package com.springboot.dingTalkSdk.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@Slf4j
public class RabbitConfig {


    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;

    @Value("${spring.rabbitmq.publisher-confirms}")
    private Boolean publisherConfirms;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        // ack，这意味着当你从消息队列取出一个消息时，ack自动发送，mq就会将消息删除。
        // 而为了保证消息的正确处理，我们需要将消息处理修改为手动确认的方式
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }

    /*
    如果消息没有到exchange,则confirm回调,ack=false
    如果消息到达exchange,则confirm回调,ack=true
    exchange到queue成功,则不回调return
    exchange到queue失败,则回调return(需设置mandatory=true,否则不回回调,消息就丢了)
    */
    // 配置接收端属性
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        /*
        这个参数设置，接收消息端，接收的最大消息数量（包括使用get、consume）,一旦到达这个数量，
        客户端不在接收消息。0为不限制。默认值为3.
        当rabbitmq要将队列中的一条消息投递给消费者时，会遍历该队列上的消费者列表，选一个合适的消费者，
        然后将消息投递出去。其中挑选消费者的一个依据就是看消费者对应的channel上未ack的消息数是否达到设置的prefetch_count个数，
        如果未ack的消息数达到了prefetch_count的个数，则不符合要求。当挑选到合适的消费者后，中断后续的遍历。
        */
        //factory.setPrefetchCount(5);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);// 确认模式：自动，默认
        factory.setMessageConverter(new Jackson2JsonMessageConverter());// 接收端类型转化pojo,需要序列化
        return factory;
    }


    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    //必须是prototype类型
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setMessageConverter(new Jackson2JsonMessageConverter());// 发送端类型转化pojo,需要序列化
        template.setMandatory(true);
        return template;
    }

    @Bean
    public SimpleMessageListenerContainer messageContainer() {
        //加载处理消息A的队列
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
        //设置接收多个队列里面的消息，这里设置接收队列A
        //假如想一个消费者处理多个队列里面的信息可以如下设置：
        //container.setQueues(queueA(),queueB(),queueC());
        container.setQueues(queueText(), queueLink(), queueMarkDown());
        container.setExposeListenerChannel(true);
        //设置最大的并发的消费者数量
        container.setMaxConcurrentConsumers(10);
        //最小的并发消费者的数量
        container.setConcurrentConsumers(1);
        //设置确认模式手工确认
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);

        container.setMessageListener(new ChannelAwareMessageListener() {
            public void onMessage(Message message, com.rabbitmq.client.Channel channel) throws Exception {
                try {
                    log.info(
                            "消费端接收到消息:" + message.getMessageProperties() + ":" + new String(message.getBody()));
                    log.info("topic:"+message.getMessageProperties().getReceivedRoutingKey());
                    // deliveryTag是消息传送的次数，我这里是为了让消息队列的第一个消息到达的时候抛出异常，
                    // 处理异常让消息重新回到队列，然后再次抛出异常，处理异常拒绝让消息重回队列
					/*if (message.getMessageProperties().getDeliveryTag() == 1
							|| message.getMessageProperties().getDeliveryTag() == 2) {
						throw new Exception();
					}*/

                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                    // false只确认当前一个消息收到，true确认所有consumer获得的消息
                } catch (Exception e) {
                    e.printStackTrace();

                    if (message.getMessageProperties().getRedelivered()) {
                        log.info("消息已重复处理失败,拒绝再次接收...");
                        channel.basicReject(message.getMessageProperties().getDeliveryTag(), true); // 拒绝消息
                    } else {
                        log.info("消息即将再次返回队列处理...");
                        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                        // requeue为是否重新回到队列
                    }
                }
            }
        });
        return container;
    }


    /**
     * dingTalk消息队列
     * @return
     */
    /*
    队列，消息，交换机设置持久化，才能保证重启后消息持久化
    persistant：是否持久化
    exclusive：排他队列，如果一个队列被声明为排他队列，该队列仅对首次申明它的连接可见，并在连接断开时自动删除。这里需要注意三点：1. 排他队列是基于连接可见的，同一连接的不同信道是可以同时访问同一连接创建的排他队列；2.“首次”，如果一个连接已经声明了一个排他队列，其他连接是不允许建立同名的排他队列的，这个与普通队列不同；3.即使该队列是持久化的，一旦连接关闭或者客户端退出，该排他队列都会被自动删除的，这种队列适用于一个客户端发送读取消息的应用场景。
    autoDelete：自动删除，如果该队列没有任何订阅的消费者的话，该队列会被自动删除。这种队列适用于临时队列。
    */
    @Bean
    public Queue queueText() {
        return new Queue(DingTalkDirectConfig.DING_TALK_TEXT,true, false, false);
    }

    @Bean
    public Queue queueLink() {
        return new Queue(DingTalkDirectConfig.DING_TALK_LINK,true, false, false);
    }

    @Bean
    public Queue queueMarkDown() {
        return new Queue(DingTalkDirectConfig.DING_TALK_MARKDOWN,true, false, false);
    }

    /*
    persistant：是否持久化
    autoDelete：自动删除，如果该队列没有任何订阅的消费者的话，该队列会被自动删除。这种队列适用于临时队列。
     */
    @Bean
    DirectExchange dingTalkDirectExchange() {
        return new DirectExchange(DingTalkDirectConfig.DING_TALK_EXCHANGE, true, false);
    }

    @Bean
    Binding bindingDirectText(Queue queueText, DirectExchange dingTalkDirectExchange) {
        return BindingBuilder.bind(queueText).to(dingTalkDirectExchange).with(DingTalkDirectConfig.DING_TALK_TEXT);
    }

    @Bean
    Binding bindingDirectLink(Queue queueLink, DirectExchange dingTalkDirectExchange) {
        return BindingBuilder.bind(queueLink).to(dingTalkDirectExchange).with(DingTalkDirectConfig.DING_TALK_LINK);
    }

    @Bean
    Binding bindingDirectMarkDown(Queue queueMarkDown, DirectExchange dingTalkDirectExchange) {
        return BindingBuilder.bind(queueMarkDown).to(dingTalkDirectExchange).with(DingTalkDirectConfig.DING_TALK_MARKDOWN);
    }

 
}