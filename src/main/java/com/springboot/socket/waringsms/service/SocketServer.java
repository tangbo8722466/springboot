package com.springboot.socket.waringsms.service;

import com.nsfocus.sms.request.ResqBody;
import com.nsfocus.sms.request.SMSRequestPacket;
import com.nsfocus.sms.request.TestRequest;
import com.nsfocus.sms.response.TestResponse;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
                //socket.setSoTimeout(3000);
                //接受客户端的响应
                InputStream is = socket.getInputStream();
                OutputStream os = socket.getOutputStream();
                while(!socket.isClosed()) {
                    //获取报文长度
                    int length = is.available();
                    if (length > 0) {
                        byte[] packetHeader = new byte[7];
                        is.read(packetHeader, 0, 7);
                        int packetSize = Integer.parseInt(new String(packetHeader));
                        //获取报文内容
                        byte[] b = new byte[length];
                        is.read(b);
                        System.out.println(new String(b));
                        String requestXml = new String(b).trim();
                        System.out.println(requestXml);
                        SMSRequestPacket requestPacket = TestRequest.xmlToObject(requestXml);
                        ResqBody body = requestPacket.getBody();
                        try {
                            Thread.sleep(1000);
                            System.out.println("server prepare to send a sms: \n" +
                                    "{ \n txnNo = " + body.getMblNo() + "\n mblNo = " + body.getTxnNo() + "\n msgCntntInf = " + body.getMsgCntntInf() +
                                    "\n}");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //向客户端发送消息
                        String packetHeaderSize = StringUtils.leftPad(String.valueOf(msg.length()), 8, "0");
                        String response = packetHeaderSize + msg;
                        System.out.println("response :  " + response);
                        os.write(response.getBytes());
                        os.flush();
                    }
                    if (is.read() == -1){
                        try {
                            socket.sendUrgentData(0);
                        } catch (IOException e) {
                            e.printStackTrace();
                            is.close();
                            os.close();
                            socket.close();
                        }
                    }
                }
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