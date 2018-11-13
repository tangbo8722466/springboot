package com.springboot.socket.packet.xml;

import com.springboot.socket.packet.xml.JaxbUtil;
import com.springboot.socket.packet.xml.MySocket;

public class Test {
    public static void main(String[] args) {
        objectToXml();
        xmlToObjetct();
    }    
    public static  void objectToXml(){
        MySocket mySocket = new MySocket();
        mySocket.setName("张三");
        mySocket.setCode("00012");
        mySocket.setAge("25");
        String xml = JaxbUtil.convertToXml(mySocket);
        System.out.println(xml);
    }

    public static void xmlToObjetct(){
        String xml = "<?xml version=\"1.0\" encoding=\"GBK\"?><Service><Code>00011</Code><Name>李四</Name><Age>26</Age></Service>";
        MySocket mySocket= JaxbUtil.converyToJavaBean(xml, MySocket.class);
        System.out.println(mySocket.toString());
    }
}