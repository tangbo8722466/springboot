package com.springboot.service.biz;

import com.alibaba.fastjson.JSONObject;
import com.springboot.Utils.encrypt.AES.AESEncryptUtils;
import com.springboot.Utils.encrypt.AES.AesCBC;
import com.springboot.Utils.encrypt.Base64.Base64Utils;
import com.springboot.Utils.encrypt.Rsa.RSAUtils;
import com.springboot.Vo.request.DataEncryptVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName DataEncryptUtils
 * @Author sangfor for tangbo
 * @Description 数据加密工具类
 * @Date 2020/5/21 15:28
 * @Version 1.0.0
 **/
@Component
@Slf4j
public class DataEncryptBiz {

    private String signTag = "&sign=";

    @Value("${sign.public.key:3}")
    private String signPublicKey;

    @Value("${sign.private.key:4}")
    private String signPrivateKey ;

    /**
     *@描述 Rsa公钥加密数据（同步Sign私钥加签）
     *@参数
     *@返回值
     */
    public DataEncryptVo encryptDataByPublicKey(String data, String rsaPublicKey) throws Exception {
        String sign = RSAUtils.sign(data.getBytes(), signPrivateKey);
        String signData = data + signTag + sign;
        String aesKey = AESEncryptUtils.createSecretKey();
        //内部会进行BASE64处理
        String body = AesCBC.encryptAES(signData.getBytes(), aesKey);
        String skey = Base64Utils.encode(RSAUtils.encryptByPublicKey(aesKey.getBytes(), rsaPublicKey));
        return DataEncryptVo.builder().skey(skey).body(body).build();
    }

    /**
     *@描述 Rsa私钥加密数据（同步Sign私钥加签）
     *@参数
     *@返回值
     */
    public DataEncryptVo encryptDataByPrivateKey(String data, String rasPrivatekey) throws Exception {
        String sign = RSAUtils.sign(data.getBytes(), signPrivateKey);
        String signData = data + signTag + sign;
        String aesKey = AESEncryptUtils.createSecretKey();
        //内部会进行BASE64处理
        String body = AesCBC.encryptAES(signData.getBytes(), aesKey);
        String skey = Base64Utils.encode(RSAUtils.encryptByPrivateKey(aesKey.getBytes(), rasPrivatekey));
        return DataEncryptVo.builder().skey(skey).body(body).build();
    }


    /**
     *@描述 Rsa私钥解密数据（同步Sign公钥验签）
     *@参数
     *@返回值
     */
    public String decodeDataByPrivateKey(DataEncryptVo dataEncryptVo, String rsaPrivateKey) throws Exception {
        String aesKey = new String(RSAUtils.decryptByPrivateKey(Base64Utils.decode(dataEncryptVo.getSkey()), rsaPrivateKey));
        String signData = AesCBC.decryptAES(dataEncryptVo.getBody(), aesKey);
        String[] signDataList = signData.split(signTag);
        String data = signDataList[0];
        String sign = signDataList[1];
        boolean signResult = RSAUtils.verify(data.getBytes(), signPublicKey, sign);
        if (!signResult){
            throw new RuntimeException("验签失败");
        }
        log.info("验签成功");
        return data;
    }

    /**
     *@描述 Rsa公钥解密数据（同步Sign公钥验签）
     *@参数
     *@返回值
     */
    public String decodeDataByPublicKey(DataEncryptVo dataEncryptVo, String rsaPublicKey) throws Exception {
        String aesKey = new String(RSAUtils.decryptByPublicKey(Base64Utils.decode(dataEncryptVo.getSkey()), rsaPublicKey));
        String signData = AesCBC.decryptAES(dataEncryptVo.getBody(), aesKey);
        String[] signDataList = signData.split(signTag);
        String data = signDataList[0];
        String sign = signDataList[1];
        boolean signResult = RSAUtils.verify(data.getBytes(), signPublicKey, sign);
        if (!signResult){
            throw new RuntimeException("验签失败");
        }
        log.info("验签成功");
        return data;
    }

}
