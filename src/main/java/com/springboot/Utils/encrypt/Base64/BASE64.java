package com.springboot.Utils.encrypt.Base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.nio.charset.Charset;

public class BASE64 {
    public static String encode(byte[] buf) {
        if (buf == null)
            return null;
        return new BASE64Encoder().encode(buf);
    }

    public static String decode(String decodeStr) {
        return getFromBase64(decodeStr);
    }

    public static String getBase64(String encodeStr) {
        byte[] encodeStrBytes = null;
        try {
            encodeStrBytes = encodeStr.getBytes(Charset.forName("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encode(encodeStrBytes);
    }

    public static String getFromBase64(String s) {
        String decodeStr = null;
        if (s != null) {
            try {
                byte[] decodeStrBytes = new BASE64Decoder().decodeBuffer(s);
                decodeStr = new String(decodeStrBytes, Charset.forName("UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return decodeStr;
    }

    public static void main(String[] args) {
        String encode = BASE64.getBase64("this is a test msg");
        System.out.println(encode);
        String decode = BASE64.decode(encode);
        System.out.println(decode);
    }
}
