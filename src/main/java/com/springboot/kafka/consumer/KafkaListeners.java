package com.springboot.kafka.consumer;

import com.alibaba.fastjson.JSON;
import com.springboot.kafka.cont.Contants;
import com.springboot.kafka.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: hs
 * @Date: 2019/3/7 00:12
 * @Description:
 */
@Slf4j
@Component
public class KafkaListeners {

    @KafkaListener(containerFactory = "kafkaBatchListener6",topics = {"#{'${kafka.listener.topics}'.split(',')[0]}"}, group = "${kafka.listener.group}")
    public void batchListener(List<ConsumerRecord<?,?>> records, Acknowledgment ack){

        List<User> userList = new ArrayList<>();
        try {
            records.forEach(record -> {
                User user = JSON.parseObject(record.value().toString(),User.class);
                user.getCreateTime().format(DateTimeFormatter.ofPattern(Contants.DateTimeFormat.DATE_TIME_PATTERN));
                userList.add(user);
                log.info("消息：{}"+record.value().toString());
            });
        } catch (Exception e) {
            log.error("Kafka监听异常"+e.getMessage(),e);
        } finally {
            ack.acknowledge();//手动提交偏移量
        }

    }

}
