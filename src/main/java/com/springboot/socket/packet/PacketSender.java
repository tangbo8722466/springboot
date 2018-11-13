package com.springboot.socket.packet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

public class PacketSender {
    String ip = "127.0.0.1";
    int port = 5200;
    private Logger log = LoggerFactory.getLogger(PacketSender.class);
    public String socket(SocketPacket socketPackage) throws UnsupportedEncodingException {
        SockeUtil sockeUtil = null;
        try {
            sockeUtil = new SockeUtil(ip, port);
        } catch (UnknownHostException e) {
            log.error("socket链接异常,链接信息：" + ip + port);
            e.printStackTrace();
        } catch (IOException e) {
            log.error("socket IO异常");
            e.printStackTrace();
        }
        SocketPacket s = null;
        try {
            s = sockeUtil.sentSocket(socketPackage);
        } catch (Exception e) {
            try {
                log.error("socket发送消息异常，发送信息：" + new String(socketPackage.getByteStream(), "GBK"));
            } catch (UnsupportedEncodingException e1) {
                log.error("socket将socketPackage转为字符串异常，socketPackage信息：" + socketPackage.getByteStream());
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        String result = "";
        try {
            result = new String(s.getByteStream(), "GBK");
        } catch (UnsupportedEncodingException e) {
            log.error("socket将socketPackage转为字符串异常，socketPackage信息：" + socketPackage.getByteStream());
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
//        System.out.println(new PacketSender().socket());
    }
}
