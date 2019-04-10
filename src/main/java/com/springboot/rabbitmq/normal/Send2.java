package com.springboot.rabbitmq.normal;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send2 {

    private static final String EXCHANGE_NAME = "qq";

    public static void main(String[] argv) throws java.io.IOException, Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("hostname");
        factory.setUsername("username");
        factory.setPassword("password");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout", true);

        String message = "hello world";

        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
        System.out.println("[x] Sent'" + message + "'");

        channel.close();
        connection.close();
    }
}