package com.springboot.socket.waringsms.service;

import com.springboot.socket.SocketServerTherd;
import com.springboot.socket.waringsms.request.Body;
import com.springboot.socket.waringsms.request.SMSRequestPacket;
import com.springboot.socket.waringsms.request.TestRequest;
import com.springboot.socket.waringsms.response.TestResponse;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        String msg = TestResponse.objectToXml().toString();
        try {
            //构造ServerSocket实例，指定端口监听客户端的连接请求
            serverSocket = new ServerSocket(5200);
            while(true) {
                //建立跟客户端的连接
                socket = serverSocket.accept();

                //接受客户端的响应
                InputStream is = socket.getInputStream();
                byte[] b = new byte[5120];
                is.read(b);
                String requestXml = new String(b).trim();
                System.out.println(requestXml);
                SMSRequestPacket requestPacket = TestRequest.xmlToObject(requestXml);
                Body body = requestPacket.getBody();
                System.out.println("server prepare to send a sms: \n" +
                        "{ \n txnNo = " + body.getMblNo() + "\n mblNo = " + body.getTxnNo() + "\n msgCntntInf = " + body.getMsgCntntInf() +
                        "\n}");

                //向客户端发送消息
                OutputStream os = socket.getOutputStream();
                os.write(msg.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //操作结束，关闭socket
            try {
                serverSocket.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}