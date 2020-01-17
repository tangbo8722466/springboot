package com.springboot.dingTalkSdk.config;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.springboot.dingTalkSdk.biz.DingTalkBiz;
import com.springboot.dingTalkSdk.vo.request.TextRequestVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class RabbitConfig {

    /**
     * ★Config:
     * # base
     * spring.rabbitmq.host: 服务Host
     * spring.rabbitmq.port: 服务端口
     * spring.rabbitmq.username: 登陆用户名
     * spring.rabbitmq.password: 登陆密码
     * spring.rabbitmq.virtual-host: 连接到rabbitMQ的vhost
     * spring.rabbitmq.addresses: 指定client连接到的server的地址，多个以逗号分隔(优先取addresses，然后再取host)
     * spring.rabbitmq.requested-heartbeat: 指定心跳超时，单位秒，0为不指定；默认60s
     * spring.rabbitmq.publisher-confirms: 是否启用【发布确认】
     * spring.rabbitmq.publisher-returns: 是否启用【发布返回】
     * spring.rabbitmq.connection-timeout: 连接超时，单位毫秒，0表示无穷大，不超时
     * spring.rabbitmq.parsed-addresses:
     *
     *
     * # ssl
     * spring.rabbitmq.ssl.enabled: 是否支持ssl
     * spring.rabbitmq.ssl.key-store: 指定持有SSL certificate的key store的路径
     * spring.rabbitmq.ssl.key-store-password: 指定访问key store的密码
     * spring.rabbitmq.ssl.trust-store: 指定持有SSL certificates的Trust store
     * spring.rabbitmq.ssl.trust-store-password: 指定访问trust store的密码
     * spring.rabbitmq.ssl.algorithm: ssl使用的算法，例如，TLSv1.1
     *
     *
     * # cache
     * spring.rabbitmq.cache.channel.size: 缓存中保持的channel数量
     * spring.rabbitmq.cache.channel.checkout-timeout: 当缓存数量被设置时，从缓存中获取一个channel的超时时间，单位毫秒；如果为0，则总是创建一个新channel
     * spring.rabbitmq.cache.connection.size: 缓存的连接数，只有是CONNECTION模式时生效
     * spring.rabbitmq.cache.connection.mode: 连接工厂缓存模式：CHANNEL 和 CONNECTION
     *
     *
     * # listener
     * spring.rabbitmq.listener.simple.auto-startup: 是否启动时自动启动容器
     * spring.rabbitmq.listener.simple.acknowledge-mode: 表示消息确认方式，其有三种配置方式，分别是none、manual和auto；默认auto
     * spring.rabbitmq.listener.simple.concurrency: 最小的消费者数量
     * spring.rabbitmq.listener.simple.max-concurrency: 最大的消费者数量
     * spring.rabbitmq.listener.simple.prefetch: 指定一个请求能处理多少个消息，如果有事务的话，必须大于等于transaction数量.
     * spring.rabbitmq.listener.simple.transaction-size: 指定一个事务处理的消息数量，最好是小于等于prefetch的数量.
     * spring.rabbitmq.listener.simple.default-requeue-rejected: 决定被拒绝的消息是否重新入队；默认是true（与参数acknowledge-mode有关系）
     * spring.rabbitmq.listener.simple.idle-event-interval: 多少长时间发布空闲容器时间，单位毫秒
     *
     * spring.rabbitmq.listener.simple.retry.enabled: 监听重试是否可用
     * spring.rabbitmq.listener.simple.retry.max-attempts: 最大重试次数
     * spring.rabbitmq.listener.simple.retry.initial-interval: 第一次和第二次尝试发布或传递消息之间的间隔
     * spring.rabbitmq.listener.simple.retry.multiplier: 应用于上一重试间隔的乘数
     * spring.rabbitmq.listener.simple.retry.max-interval: 最大重试时间间隔
     * spring.rabbitmq.listener.simple.retry.stateless: 重试是有状态or无状态
     *
     *
     * # template
     * spring.rabbitmq.template.mandatory: 启用强制信息；默认false
     * spring.rabbitmq.template.receive-timeout: receive() 操作的超时时间
     * spring.rabbitmq.template.reply-timeout: sendAndReceive() 操作的超时时间
     * spring.rabbitmq.template.retry.enabled: 发送重试是否可用
     * spring.rabbitmq.template.retry.max-attempts: 最大重试次数
     * spring.rabbitmq.template.retry.initial-interval: 第一次和第二次尝试发布或传递消息之间的间隔
     * spring.rabbitmq.template.retry.multiplier: 应用于上一重试间隔的乘数
     * spring.rabbitmq.template.retry.max-interval: 最大重试时间间隔
     */


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
    
    @Autowired
    DingTalkBiz dingTalkBiz;

    @Value("${spring.rabbitmq.dingTalk.link.url}")
    private String dingTalkLinkUrl;

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
        factory.setPrefetchCount(5);
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
        //container.setQueues(queueText(), queueLink(), queueMarkDown());
        container.setQueues(queueText(), queueMarkDown());
        container.setExposeListenerChannel(true);
        //设置最大的并发的消费者数量
        container.setMaxConcurrentConsumers(10);
        //最小的并发消费者的数量
        container.setConcurrentConsumers(2);
        //设置确认模式手工确认
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setMessageListener(new ChannelAwareMessageListener() {
            public void onMessage(Message message, com.rabbitmq.client.Channel channel) throws Exception {
                try {
                    log.info("消费端接收到消息:" + message.getMessageProperties());
                    log.info("请求参数:" + new String(message.getBody()));
                    log.info("队列topic:"+message.getMessageProperties().getReceivedRoutingKey());
                    log.info("消息DeliveryTag:"+message.getMessageProperties().getDeliveryTag());
                    // deliveryTag（唯一标识 ID）：当一个消费者向 RabbitMQ 注册后，会建立起一个 Channel ，
                    // RabbitMQ 会用 basic.deliver 方法向消费者推送消息，这个方法携带了一个 delivery tag，
                    // 它代表了 RabbitMQ 向该 Channel 投递的这条消息的唯一标识 ID，是一个单调递增的正整数，
                    // delivery tag 的范围仅限于 Channel
                    // 处理异常让消息重新回到队列，然后再次抛出异常，处理异常拒绝让消息重回队列
//					if (message.getMessageProperties().getDeliveryTag() == 1
//							|| message.getMessageProperties().getDeliveryTag() == 2) {
//						throw new Exception();
//					}
//                    if (true) throw new Exception();
                    //deliveryTag:该消息的index
                    //multiple：是否批量.true:将一次性ack所有小于deliveryTag的消息。
                    // false只确认当前一个消息收到，true确认所有consumer获得的消息

                    String json = new String(message.getBody(), StandardCharsets.UTF_8);
                    TextRequestVo textRequestVo = JSONObject.parseObject(json, TextRequestVo.class);
                    OapiRobotSendResponse response = dingTalkBiz.sendText(dingTalkLinkUrl, textRequestVo);
                    log.info("send ding link:" + net.sf.json.JSONObject.fromObject(response).toString());
                    if (response.isSuccess()) {
                        dingTalkBiz.sendTextIntoDelayQueue(textRequestVo);
                    }
                    else {
                        dingTalkBiz.sendTextIntoDelayQueue(textRequestVo);
                    }
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                } catch (Exception e) {
                    e.printStackTrace();

                    if (message.getMessageProperties().getRedelivered()) {
                        log.info("消息已重复处理失败,拒绝再次接收...");
                        //deliveryTag:该消息的index
                        //requeue：被拒绝的是否重新入队列
                        channel.basicReject(message.getMessageProperties().getDeliveryTag(), false); // 拒绝消息
                    } else {
                        log.info("消息即将再次返回队列处理...");
                        //deliveryTag:该消息的index
                        //multiple：是否批量.true:将一次性拒绝所有小于deliveryTag的消息。
                        //requeue：被拒绝的是否重新入队列
                        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
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
    Queue delayQueueText() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", DingTalkDirectConfig.DING_TALK_EXCHANGE); // DLX，dead letter发送到的exchange
        arguments.put("x-dead-letter-routing-key", DingTalkDirectConfig.DING_TALK_TEXT); // dead letter携带的routing key
        //arguments.put("x-message-ttl", QUEUE_EXPIRATION); // 设置队列的过期时间，如果消费发送也设置了过期时间，将采用最小的过期时间
        return new Queue(DingTalkDirectConfig.DING_TALK_DELAY_TEXT,true, false, false, arguments);
    }


    @Bean
    public Queue queueLink() {
        return new Queue(DingTalkDirectConfig.DING_TALK_LINK,true, false, false);
    }

    @Bean
    public Queue delayQueueLink() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", DingTalkDirectConfig.DING_TALK_EXCHANGE); // DLX，dead letter发送到的exchange
        arguments.put("x-dead-letter-routing-key", DingTalkDirectConfig.DING_TALK_LINK); // dead letter携带的routing key
        //arguments.put("x-message-ttl", QUEUE_EXPIRATION); // 设置队列的过期时间，如果消费发送也设置了过期时间，将采用最小的过期时间
        return new Queue(DingTalkDirectConfig.DING_TALK_DELAY_LINK,true, false, false, arguments);
    }

    @Bean
    public Queue queueMarkDown() {
        return new Queue(DingTalkDirectConfig.DING_TALK_MARKDOWN,true, false, false);
    }

    @Bean
    public Queue delayQueueMarkDown() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", DingTalkDirectConfig.DING_TALK_EXCHANGE); // DLX，dead letter发送到的exchange
        arguments.put("x-dead-letter-routing-key", DingTalkDirectConfig.DING_TALK_MARKDOWN); // dead letter携带的routing key
        //arguments.put("x-message-ttl", QUEUE_EXPIRATION); // 设置队列的过期时间，如果消费发送也设置了过期时间，将采用最小的过期时间
        return new Queue(DingTalkDirectConfig.DING_TALK_DELAY_MARKDOWN,true, false, false, arguments);
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

    @Bean
    Binding bindingDirectDelayText(Queue delayQueueText, DirectExchange dingTalkDirectExchange) {
        return BindingBuilder.bind(delayQueueText).to(dingTalkDirectExchange).with(DingTalkDirectConfig.DING_TALK_DELAY_TEXT);
    }

    @Bean
    Binding bindingDirectDelayLink(Queue delayQueueLink, DirectExchange dingTalkDirectExchange) {
        return BindingBuilder.bind(delayQueueLink).to(dingTalkDirectExchange).with(DingTalkDirectConfig.DING_TALK_DELAY_LINK);
    }

    @Bean
    Binding bindingDirectDelayMarkDown(Queue delayQueueMarkDown, DirectExchange dingTalkDirectExchange) {
        return BindingBuilder.bind(delayQueueMarkDown).to(dingTalkDirectExchange).with(DingTalkDirectConfig.DING_TALK_DELAY_MARKDOWN);
    }
 
}