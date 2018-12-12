package com.springboot.RSA;

import java.util.Map;

import com.springboot.Utils.Base64Utils;
import com.springboot.Utils.RSAUtils;
import org.junit.Test;

public class RSATester {

    static String publicKey;
    static String privateKey;

    static {
        try {
            Map<String, Object> keyMap = RSAUtils.genKeyPair();
            publicKey = RSAUtils.getPublicKey(keyMap);
            privateKey = RSAUtils.getPrivateKey(keyMap);
            System.err.println("公钥: \n\r" + publicKey);
            System.err.println("私钥： \n\r" + privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        testJavaRsa();
    }

    @Test
    public void tests() throws Exception {
        String data = "hNYdT0/8wxljGvLQVPYUDeoWS217rmB5msKOljb6OBsOhdL1Domxt6Sy3BqdGqS3StGJuZuQ9wEFzGdyoTQ7IRiC0gRrkLuxvCUq8FANt1JpJCBqvzLdQD/ygCjkJMWWkMU4EFUW3xYkAsidqM8Zynhpk+mdja3789Ng4s68xJI=";

        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIn2zWqU7K/2qm5pOpq5bp9R+3MTnStWTfJU9nC/Vo7UKH9dITPvrELCTK+qlqpx5Fes+l0GY7n6u4n4jyiw4ejsvkZYQ5ww477yLOn2FcoEGuZEwPgSCmfTST0OFUgQqn+/J11k9L92jEHyieE3qmhMkMt0UsVUSJwx/nZxo30ZAgMBAAECgYBD3YHigeuEC4R+14iaf8jo2j0kuGtB3Cxvnlez0otTqw1YyYkBsU49cLKkXvfKVEgM0Ow/QltgKvSBxCE31PrrDka5TygVMqqA/IM7NrDvjUcGLjyoeNmLA8660fWcDxUTlAGN5kxIvUATayVwKVflpWPWu0FPKsWrZustnEo+4QJBAMCmYsWqAKWYMVRXFP3/XGRfio8DV793TOckyBSN9eh8UhgoZyT3u7oeHmDJEwm4aNMHlg1Pcdc6tNsvi1FRCiUCQQC3VNzfF4xOtUgX7vWPL8YVljLuXmy12iVYmg6ofu9l31nwM9FLQ1TRFglvF5LWrIXTQb07PgGd5DJMAQWGsqLlAkAPE7Z9M73TN+L8b8hDzJ1leZi1cpSGdoa9PEKwYR/SrxAZtefEm+LEQSEtf+8OfrEtetWCeyo0pvKKiOEFXytFAkEAgynL/DC0yXsZYUYtmYvshHU5ayFTVagFICbYZeSrEo+BoUDxdI9vl0fU6A5NmBlGhaZ65G+waG5jLc1tTrlvoQJAXBEoPcBNAosiZHQfYBwHqU6mJ9/ZacJh3MtJzGGebfEwJgtln5b154iANqNWXpySBLvkK+Boq7FYRiD83pqmUg==";
        byte[] rs = Base64Utils.decode(data);

        String r1 = new String(RSAUtils.decryptByPrivateKey(rs, privateKey));

        System.out.println("\r\n\r\n" + r1 + "okb");

    }

    static void testSign() throws Exception {
        System.err.println("私钥加密——公钥解密");
        String source = "32232";
        System.out.println("原文字：\r\n" + source);
        byte[] data = source.getBytes();
        byte[] encodedData = RSAUtils.encryptByPrivateKey(data, privateKey);
        System.out.println("加密后：\r\n" + new String(encodedData));
        byte[] decodedData = RSAUtils.decryptByPublicKey(encodedData, publicKey);
        String target = new String(decodedData);
        System.out.println("解密后: \r\n" + target);
        System.err.println("私钥签名——公钥验证签名");
        String sign = RSAUtils.sign(encodedData, privateKey);
        System.err.println("签名:\r" + sign);
        boolean status = RSAUtils.verify(encodedData, publicKey, sign);
        System.err.println("验证结果:\r" + status);
    }

    static void test() throws Exception {
        System.err.println("公钥加密——私钥解密");
        String source = "这是一行没有任何意义的文字，你看完了等于没看，不是吗？";
        System.out.println("\r加密前文字：\r\n" + source);
        byte[] data = source.getBytes();
        byte[] encodedData = RSAUtils.encryptByPublicKey(data, publicKey);
        System.out.println("加密后文字：\r\n" + new String(encodedData));
        System.out.println("----------------:base64处理：" + Base64Utils.encode(encodedData));
        byte[] decodedData = RSAUtils.decryptByPrivateKey(encodedData, privateKey);
        String target = new String(decodedData);
        System.out.println("解密后文字: \r\n" + target);
    }

    /*
     * 测试自己封装java端加密和解密的方法
     */

    static void testJavaRsa() {
        String PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCv2Jrtljrs6YdNk0CwmxmdKRVTSKQKmMeBXdaZ1VtnnTwrqLm+e0lGSwe2Q5ZLY6uBiL9etQlmKEEIU01LJWa39J5vQePkKxS5Ka89wIQ44JjCx1IblmbBX9HAEywLVugdC/yV0MyhRQpteOBLHfkM+TKjn9KWfN3F+oUOQkcDKwIDAQAB";
        String PRIVATEKEY = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAK/Ymu2WOuzph02TQLCbGZ0pFVNIpAqYx4Fd1pnVW2edPCuoub57SUZLB7ZDlktjq4GIv161CWYoQQhTTUslZrf0nm9B4+QrFLkprz3AhDjgmMLHUhuWZsFf0cATLAtW6B0L/JXQzKFFCm144Esd+Qz5MqOf0pZ83cX6hQ5CRwMrAgMBAAECgYEAnCr3NMd1349qbXe4POAR+GRTHxrlLNAQZpDLFOXJ/pRCqtd21ncxVS8vUyxlHuEOoMSsiFSpW0OmtdkdTP6TYJEgkYsad6XgQeX8yaWWGsaCQH5+fmoTqMR9/Yf5Ve4O7lmD5PApRDgu5TJkQgAMb82GwJTej/WGulwhoiFL4aECQQD1HdrXtgqRQgWFolVK/TA5c57FWRsU2y85FwcaD8hOlBoOcNDXeAgIZKpdwo/4r5iJS0Zu4Gzh2YulPnul05UxAkEAt6dhJOHdRsOcn3f62N7s57WEoOHpKUX+SiaX2nICUTFR2OtgHBBPz/0smsp1deGa2ley2yeQcbDHDTv9L0T3GwJAZ9COOKBsV1tcFV5Mu6cCfsK+cx0rQCkZFhfvtRUMJGPLqWQUv72hwVRJZ86YxZ4xf6y3FwzMcNd/sqwFGJXFcQJBALDCm6Ao95VVTli53KiBHxkAvw8tgAEmidIKhRtNoedNhc429QDtvjOcJCMze4wiwK0nvVBrKv4Ru/OD6nJleUMCQQCi5SXDs34Xb406fWWypTNPFyqAm4h7RIl73BX2oSgFlMqDNRmlcqSDUxQ3OkAFioI8nkwGREBSDzKBhU4LGF4+";

        try {
            Map<String, Object> keyPair = RSAUtils.genKeyPair();
//             PUBLICKEY = RSAUtils.getPublicKey(keyPair);
//             PRIVATEKEY = RSAUtils.getPrivateKey(keyPair);
            String data = "admin123";
            data = RSAUtils.encryptedDataOnJava(data, PUBLICKEY);
            System.out.println("加密数据：" + data);
            String test = "pa60wSjq/WKMEvTrTMiB6BeoviWFiujbZwpfLcx2CHm5q4Tol8hE6kcJaxBlXNVuwZk3wIHHeNM0";
            System.out.println("解密数据：" + RSAUtils.decryptDataOnJava(data, PRIVATEKEY));
            System.out.println("解密数据test：" + RSAUtils.decryptDataOnJava(data, PRIVATEKEY));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void testFrontEncrptAndAfterDecrypt() {
        String PRIVATEKEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIn2zWqU7K/2qm5pOpq5bp9R+3MTnStWTfJU9nC/Vo7UKH9dITPvrELCTK+qlqpx5Fes+l0GY7n6u4n4jyiw4ejsvkZYQ5ww477yLOn2FcoEGuZEwPgSCmfTST0OFUgQqn+/J11k9L92jEHyieE3qmhMkMt0UsVUSJwx/nZxo30ZAgMBAAECgYBD3YHigeuEC4R+14iaf8jo2j0kuGtB3Cxvnlez0otTqw1YyYkBsU49cLKkXvfKVEgM0Ow/QltgKvSBxCE31PrrDka5TygVMqqA/IM7NrDvjUcGLjyoeNmLA8660fWcDxUTlAGN5kxIvUATayVwKVflpWPWu0FPKsWrZustnEo+4QJBAMCmYsWqAKWYMVRXFP3/XGRfio8DV793TOckyBSN9eh8UhgoZyT3u7oeHmDJEwm4aNMHlg1Pcdc6tNsvi1FRCiUCQQC3VNzfF4xOtUgX7vWPL8YVljLuXmy12iVYmg6ofu9l31nwM9FLQ1TRFglvF5LWrIXTQb07PgGd5DJMAQWGsqLlAkAPE7Z9M73TN+L8b8hDzJ1leZi1cpSGdoa9PEKwYR/SrxAZtefEm+LEQSEtf+8OfrEtetWCeyo0pvKKiOEFXytFAkEAgynL/DC0yXsZYUYtmYvshHU5ayFTVagFICbYZeSrEo+BoUDxdI9vl0fU6A5NmBlGhaZ65G+waG5jLc1tTrlvoQJAXBEoPcBNAosiZHQfYBwHqU6mJ9/ZacJh3MtJzGGebfEwJgtln5b154iANqNWXpySBLvkK+Boq7FYRiD83pqmUg==";
        String data = "FBGU7sQfpSfCgB2hqFuIqkivEUHVRHD8JFdyxYeWqQHsTj9UEuVmvi28n1fOHRwW+3aZD3ttdzfUHWiXD2NErcX/CYs5BtSXT7RcJfWWcXvegq5BBDEAJCADWCRdYnblN+SLUC+ctDXcLw4xmjwAajowSzhCfY/lU3TdnJjO488=";
        System.out.println("解密数据：" + RSAUtils.decryptDataOnJava(data, PRIVATEKEY));
    }

}