package com.springboot.socket.packet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

/**

 * 报文发送

 */

public class SockeUtil {
    Socket socket = null;
    public SockeUtil(String ip,int port) throws UnknownHostException, IOException {
        socket = new Socket(ip, port);
    }

    //
    public SocketPacket sentSocket(SocketPacket socketPacket) throws UnsupportedEncodingException, IOException{
        SocketPacket sPacket = new SocketPacket();
        OutputStream output=null;
        InputStream input =null;
        // 同步字符串(3byte)
        byte[] sync = null; //
        byte[] bodyLen = null; // 8位长度
        byte[] body = null; // 内容
        byte[] md5 = null;  // MD5
        output = socket.getOutputStream();
        //写数据发送报文
        output.write(socketPacket.getByteStream());
        //获得服务端返回的数据
        input = socket.getInputStream();
        sync = this.streamToBytes(input,3);
        bodyLen = this.streamToBytes(input, 8);
        String lenString = new String(bodyLen);
        int len = Integer.valueOf(lenString);
        body = this.streamToBytes(input, len);
        md5 = this.streamToBytes(input, 32);
        sPacket.setSyncStr(new String(sync, Charset.forName("gbk")));
        socketPacket.setBodyLen(new String(bodyLen,Charset.forName("gbk")));
        socketPacket.setBody(new String(body,Charset.forName("gbk")));
        socketPacket.setMd5(new String(md5,Charset.forName("gbk")));
        return sPacket;
    }

    public byte[] streamToBytes(InputStream inputStream,int len){
        /**
         * inputStream.read(要复制到得字节数组,起始位置下标,要复制的长度)
         * 该方法读取后input的下标会自动的后移，下次读取的时候还是从上次读取后移动到的下标开始读取
         * 所以每次读取后就不需要在制定起始的下标了
         */
        byte [] bytes= new byte[len];
        try {
            inputStream.read(bytes, 0, len);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        return bytes;
    }

}