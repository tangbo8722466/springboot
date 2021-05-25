package com.springboot.kafka.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Value;
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

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: hs
 * @Date: 2019/3/5 19:50
 * @Description:
 */
//@Configuration
//@EnableKafka
@Slf4j
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

    /**
     * 使用Consumer进行消费
     * 偏移量提交的三种方式
     * 1.指定偏移量提交
     * 2.同步提交
     * 3.异步提交
     */
    public void consumerHandler() {
        String topic = "springboot";
        ConsumerFactory factory = new DefaultKafkaConsumerFactory<>(consumerConfigs());
        Consumer<String, String> consumer = factory.createConsumer();

        // 消息订阅
        // consumer.subscribe(Collections.singletonList(topic));
        // 可以订阅多个主题
        // consumer.subscribe(Arrays.asList(topic, topic2));
        // 可以使用正则表达式进行订阅
        // consumer.subscribe(Pattern.compile("topic-demo*"));

        // 指定订阅的分区
        TopicPartition topicPartition = new TopicPartition(topic, 0);
        consumer.assign(Arrays.asList(topicPartition));
        // 初始化offset位移为-1
        long lastConsumeOffset = -1;

        Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.of(5, ChronoUnit.MINUTES));
            int count = 0;
            for (ConsumerRecord<String, String> record : records) {
                //模拟对消息的处理
                log.info("topic = %s, partition = %s, offset = %d, customer = %s, country = %s\n", record.topic(),
                        record.partition(), record.offset(), record.key(),
                        record.value());
                //* 1.指定偏移量提交
                //在读取每条消息后，使用期望处理的下一个消息的偏移量更新map里的偏移量。下一次就从这里开始读取消息
                currentOffsets.put(new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset() + 1, "no matadata"));
                if (count++ % 1000 == 0) {
                    //每处理1000条消息就提交一次偏移量，在实际应用中，可以根据时间或者消息的内容进行提交
                    consumer.commitAsync(currentOffsets, null);
                }
            }
            // * 2.同步提交
            try {
                consumer.commitSync();//处理完当前批次的消息，在轮询更多的消息之前，调用commitSync方法提交当前批次最新的消息
            } catch (CommitFailedException e) {
                log.error("commit failed", e);//只要没有发生不可恢复的错误，commitSync方法会一直尝试直至提交成功。如果提交失败，我们也只能把异常记录到错误日志里
            }

            // * 3.异步提交
            try {
                consumer.commitAsync();//如果一切正常，我们使用commitAsync来提交，这样速度更快，而且即使这次提交失败，下次提交很可能会成功
            } catch (CommitFailedException e) {
                log.error("commit failed", e);
            } finally{
                consumer.commitSync();//使用commitSync，直到提交成成功或者发生无法恢复的错误
            }
        }
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

        // 是否开启自动提交偏移量，使用手动提交偏移量
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        // 自动提交偏移量时间间隔
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);

        // session超时时间
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
        // 心跳检查发送时间（防止session超时，导致rebalance）
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 3000);

        props.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, 180000);

        props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, maxPartitionFetchBytes);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }


}