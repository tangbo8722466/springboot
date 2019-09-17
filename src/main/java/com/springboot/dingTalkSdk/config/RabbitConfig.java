package com.springboot.dingTalkSdk.config;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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