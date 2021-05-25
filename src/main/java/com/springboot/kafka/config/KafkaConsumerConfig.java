package com.springboot.kafka.config;

import org.springframework.beans.factory.annotation.Value;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: hs
 * @Date: 2019/3/5 19:50
 * @Description:
 */
//@Configuration
//@EnableKafka
public class KafkaConsumerConfig {

    @Value("${kafka.consumer.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${kafka.consumer.enable-auto-commit}")
    private Boolean autoCommit;

    @Value("${kafka.consumer.auto-commit-interval}")
    private Integer autoCommitInterval;

    @Value("${kafka.consumer.max-poll-records}")
    private Integer maxPollRecords;

    @Value("${kafka.consumer.auto-offset-reset}")
    private String autoOffsetReset;

    @Value("#{'${kafka.listener.concurrencys}'.split(',')[0]}")
    private Integer concurrency3;

    @Value("#{'${kafka.listener.concurrencys}'.split(',')[1]}")
    private Integer concurrency6;

    @Value("${kafka.listener.poll-timeout}")
    private Long pollTimeout;

    @Value("${kafka.consumer.session-timeout}")
    private String sessionTimeout;

    @Value("${kafka.listener.batch-listener}")
    private Boolean batchListener;

    @Value("${kafka.consumer.max-poll-interval}")
    private Integer maxPollInterval;

    @Value("${kafka.consumer.max-partition-fetch-bytes}")
    private Integer maxPartitionFetchBytes;

    /**
     * 并发数6
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "kafkaBatchListener6")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaBatchListener6(
            ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory) {
        // 可省略，@KafkaListener(concurrency = "1")，包含Concurrency配置
        kafkaListenerContainerFactory.setConcurrency(concurrency6);
        return kafkaListenerContainerFactory;
    }

    /**
     * 并发数3
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "kafkaBatchListener3")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaBatchListener3(
            ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory) {
        // 可省略，@KafkaListener(concurrency = "1")，包含Concurrency配置
        kafkaListenerContainerFactory.setConcurrency(concurrency3);
        return kafkaListenerContainerFactory;
    }

    @Bean
    private ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(ConsumerFactory<String, String> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        //批量消费
        factory.setBatchListener(batchListener);
        //如果消息队列中没有消息，等待timeout毫秒后，调用poll()方法。
        // 如果队列中有消息，立即消费消息，每次消费的消息的多少可以通过max.poll.records配置。
        //手动提交无需配置
        factory.getContainerProperties().setPollTimeout(pollTimeout);
        //设置提交偏移量的方式， MANUAL_IMMEDIATE 表示消费一条提交一次；MANUAL表示批量提交一次
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return factory;
    }

    @Bean
    private ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    private Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "springboot");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        /*
        （1）实际应用中，消费到的数据处理时长不宜超过max.poll.interval.ms，否则会触发rebalance （默认300S）
        （2）如果处理消费到的数据耗时，可以尝试通过减小max.poll.records的方式减小单次拉取的记录数（默认是500条）
         */
        //max.poll.interval.ms决定了获取消息后提交偏移量的最大时间 （默认300S）
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, maxPollInterval);
        // 设置每次接收Message的数量 （默认500）
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);

        // 开启自动提交偏移量
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        // 自动提交偏移量时间间隔
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);

        // session超时时间
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
        // 心跳检查发送时间（防止session超时，导致rebalance）
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 3000);

        props.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, 180000);
        // max.poll.interval.ms决定了获取消息后提交偏移量的最大时间

        props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, maxPartitionFetchBytes);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }


}