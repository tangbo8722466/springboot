package com.springboot.socket.waringsms.service;

import com.springboot.socket.waringsms.request.TestRequest;
import com.springboot.socket.waringsms.response.Result;
import com.springboot.socket.waringsms.response.SMSResponsePacket;
import com.springboot.socket.waringsms.response.TestResponse;

import java.io.*;
import java.net.Socket;

public class SocketClient {
    public static void main(String[] args) {
        Socket socket = null;
        try {
            //对服务端发起连接请求
            socket = new Socket("localhost", 5200);
            //给服务端发送响应信息
            OutputStream os = socket.getOutputStream();
            os.write(TestRequest.objectToXml().toString().getBytes());

            //接受服务端消息并打印
            InputStream is = socket.getInputStream();
            byte b[] = new byte[5120];
            is.read(b);
            String responseXml = new String(b).trim();
            SMSResponsePacket responsePacket = TestResponse.xmlToObject(responseXml);
            Result result = responsePacket.getSysHead().getResult().get(0);
            System.out.println("send sms result:\n" +
                    " {\n ReturnCode: "+result.getReturnCode()+" \n ReturnMsg: "+result.getReturnMsg()+" \n }");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}