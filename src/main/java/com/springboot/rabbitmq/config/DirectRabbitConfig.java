package com.springboot.rabbitmq.config;

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

/**
 * Created by tangbo on 2019/4/10 0010.
 */
//@Configuration
public class DirectRabbitConfig {
    final static String apple = "apple";
    final static String pear = "pear";

    @Bean
    public Queue queueApple() {
        return new Queue(DirectRabbitConfig.apple);
    }

    @Bean
    public Queue queuePear() {
        return new Queue(DirectRabbitConfig.pear);
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange("directExchange");
    }

    @Bean
    Binding bindingDirectApple(Queue queueApple, DirectExchange directExchange) {
        return BindingBuilder.bind(queueApple).to(directExchange).with(DirectRabbitConfig.apple);
    }

    @Bean
    Binding bindingDirectPear(Queue queuePear, DirectExchange directExchange) {
        return BindingBuilder.bind(queuePear).to(directExchange).with(DirectRabbitConfig.pear);
    }
}
