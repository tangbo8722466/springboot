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
        String PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCw9D5cEu39diXUoSCQIf3MU5XHTHNzO2zKlHiR\n" +
                "Nl6Y5weLpaEdzX14KKF+9NnJXELQDa33Htc178etYbck2MGV/o0T8Fivb9yjBvtWprLZjV7Mqyu5\n" +
                "2fHRmGRYMQ/g4nTUwcoeob919iUk+4tqXGnj/kePoItkgCHFgxUlGIusyQIDAQAB";
        String PRIVATEKEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALD0PlwS7f12JdShIJAh/cxTlcdM\n" +
                "c3M7bMqUeJE2XpjnB4uloR3NfXgooX702clcQtANrfce1zXvx61htyTYwZX+jRPwWK9v3KMG+1am\n" +
                "stmNXsyrK7nZ8dGYZFgxD+DidNTByh6hv3X2JST7i2pcaeP+R4+gi2SAIcWDFSUYi6zJAgMBAAEC\n" +
                "gYBzAlvhdgAuY3LVDuBeLWmKOq03em3y8CWWSQdPwhmboVSbI8xi1WL+DNJh9Hv0Tr2yAaIW4nmX\n" +
                "ub965e6lySeP48hl0SbquCKnETkst4UCdMHMv9hBaLTuvK/JbBodXQFKNgCvXpmMrR+yArP44346\n" +
                "UacIW1ESmY0SScKRUPSCvQJBANkNwE8h0yArOPP9yXNswt2WarPI8/UuJpl19Om1HR4sekI6glSy\n" +
                "SvOL/XMxJ/0d74bD3+F6D2G495AvHFL1r08CQQDQtIlVKHw3MAioRImDDa2STTuYEls+pKvVYVkn\n" +
                "AtDyNr+nBh2IytInIaMJpdnGmPajIKbwgRERmQxpqjipwpxnAkBjECKgOyjXusJgoYjK9G2vopIK\n" +
                "ggHN+gVF7w8bhzzF47jc90U6kLEinNbNNzcwD/SdRlKs3v+cM0ZR+R5tQpKDAkBzQ7IxUQnBZQry\n" +
                "+99CfPRmv7bxFvpDz6iDUZ9uVzTVAP26HjuDiBimugIUWv+6mlJk56yrWWz23iMn3HYs86lzAkEA\n" +
                "m37xhtWa4a761JqrV5x3pbznBP1SxToLa1c5JEpfD2TzQ0YapO7iS9cpLm60SHmCc/9GqsaWvW01\n" +
                "CKEkOSonog==";
        try {
            //Map<String, Object> keyPair = RSAUtils.genKeyPair();
//            String PUBLICKEY = RSAUtils.getPublicKey(keyPair);
//            String  PRIVATEKEY = RSAUtils.getPrivateKey(keyPair);
            String data = "admin123";
            data = RSAUtils.encryptedDataOnJava(data, PUBLICKEY);
            System.out.println("加密数据：" + data);
            System.out.println("解密数据：" + RSAUtils.decryptDataOnJava(data, PRIVATEKEY));
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