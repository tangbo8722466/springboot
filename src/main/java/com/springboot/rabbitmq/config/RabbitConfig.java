package com.springboot.rabbitmq.config;

import com.springboot.dingTalkSdk.config.DingTalkDirectConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.rabbitmq.client.Channel;
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
public class RabbitConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    public static final String QUEUE_A = "QUEUE_A";
    public static final String QUEUE_B = "QUEUE_B";
    public static final String QUEUE_C = "QUEUE_C";

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host,port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        // factory.setPort(15672);
        connectionFactory.setPublisherConfirms(true);// 保证消息的事务性处理rabbitmq默认的处理方式为auto
        // ack，这意味着当你从消息队列取出一个消息时，ack自动发送，mq就会将消息删除。而为了保证消息的正确处理，我们需要将消息处理修改为手动确认的方式

        Channel channel = connectionFactory.createConnection().createChannel(false);

        // 声明queue,exchange,以及绑定
        try {
            channel.exchangeDeclare("queueExchange" /* exchange名称 */, "topic"/* 类型 */);
            // durable,exclusive,autodelete
            channel.queueDeclare("queueName", true, false, false, null); // (如果没有就)创建Queue
            channel.queueBind("queueName", "queueExchange", "routingkey");
        } catch (Exception e) {
            logger.error("mq declare queue exchange fail ", e);
        } finally {
            try {
                channel.exchangeDelete("queueExchange" );
                channel.close();
            } catch (Exception e) {
                logger.error("mq channel close fail", e);
            }

        }
        return connectionFactory;
    }

    /*如果消息没有到exchange,则confirm回调,ack=false
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
        //这个参数设置，接收消息端，接收的最大消息数量（包括使用get、consume）,一旦到达这个数量，客户端不在接收消息。0为不限制。默认值为3.
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
		/*Queue[] q = new Queue[queues.split(",").length];
		for (int i = 0; i < queues.split(",").length; i++) {
			q[i] = new Queue(queues.split(",")[i]);
		}*/
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
        container.setQueues(QueueA(),QueueB(), queueApple(), queuePear(), AMessage(), BMessage(), CMessage(), queueMessage(), queueMessages());
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(1);
        container.setConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setMessageListener(new ChannelAwareMessageListener() {

            public void onMessage(Message message, com.rabbitmq.client.Channel channel) throws Exception {
                try {
                    System.out.println(
                            "消费端接收到消息:" + message.getMessageProperties() + ":" + new String(message.getBody()));
                    System.out.println("topic:"+message.getMessageProperties().getReceivedRoutingKey());
                    // deliveryTag是消息传送的次数，我这里是为了让消息队列的第一个消息到达的时候抛出异常，处理异常让消息重新回到队列，然后再次抛出异常，处理异常拒绝让消息重回队列
					/*if (message.getMessageProperties().getDeliveryTag() == 1
							|| message.getMessageProperties().getDeliveryTag() == 2) {
						throw new Exception();
					}*/

                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); // false只确认当前一个消息收到，true确认所有consumer获得的消息
                } catch (Exception e) {
                    e.printStackTrace();

                    if (message.getMessageProperties().getRedelivered()) {
                        System.out.println("消息已重复处理失败,拒绝再次接收...");
                        channel.basicReject(message.getMessageProperties().getDeliveryTag(), true); // 拒绝消息
                    } else {
                        System.out.println("消息即将再次返回队列处理...");
                        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true); // requeue为是否重新回到队列
                    }
                }
            }
        });
        return container;
    }

    @Bean
    public Queue QueueA() {
        return new Queue(RabbitConfig.QUEUE_A);
    }

    @Bean
    public Queue QueueB() {
        return new Queue(RabbitConfig.QUEUE_B);
    }


    /*
    direct
     */

    @Bean
    public Queue queueApple() {
        return new Queue(com.springboot.rabbitmq.config.DirectRabbitConfig.apple);
    }

    @Bean
    public Queue queuePear() {
        return new Queue(com.springboot.rabbitmq.config.DirectRabbitConfig.pear);
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange("directExchange");
    }

    @Bean
    Binding bindingDirectApple(Queue queueApple, DirectExchange directExchange) {
        return BindingBuilder.bind(queueApple).to(directExchange).with(com.springboot.rabbitmq.config.DirectRabbitConfig.apple);
    }

    @Bean
    Binding bindingDirectPear(Queue queuePear, DirectExchange directExchange) {
        return BindingBuilder.bind(queuePear).to(directExchange).with(com.springboot.rabbitmq.config.DirectRabbitConfig.pear);
    }

    /*
   fanout
    */
    @Bean
    public Queue AMessage() {
        return new Queue("fanout.A");
    }

    @Bean
    public Queue BMessage() {
        return new Queue("fanout.B");
    }

    @Bean
    public Queue CMessage() {
        return new Queue("fanout.C");
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    Binding bindingExchangeA(Queue AMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(AMessage).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeB(Queue BMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(BMessage).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeC(Queue CMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(CMessage).to(fanoutExchange);
    }

    /*
    topic
     */
    final static String message = "topic.message";
    final static String messages = "topic.messages";

    @Bean
    public Queue queueMessage() {
        return new Queue(message);
    }

    @Bean
    public Queue queueMessages() {
        return new Queue(messages);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("topicExchange");
    }

    @Bean
    Binding bindingExchangeMessage(Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with("topic.message");
    }

    @Bean
    Binding bindingExchangeMessages(Queue queueMessages, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessages).to(exchange).with("topic.#");
    }


    /**
     * dingTalk消息队列
     * @return
     */
    @Bean
    public Queue queueText() {
        return new Queue(DingTalkDirectConfig.DING_TALK_TEXT);
    }

    @Bean
    public Queue queueLink() {
        return new Queue(DingTalkDirectConfig.DING_TALK_LINK);
    }

    @Bean
    public Queue queueMarkDown() {
        return new Queue(DingTalkDirectConfig.DING_TALK_MARKDOWN);
    }

    @Bean
    DirectExchange dingTalkDirectExchange() {
        return new DirectExchange(DingTalkDirectConfig.DING_TALK_EXCHANGE);
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