package com.springboot.service.biz;

import com.springboot.Utils.encrypt.AES.AESEncryptUtils;
import com.springboot.Utils.encrypt.Base64.Base64Utils;
import com.springboot.Utils.encrypt.Rsa.RSAUtils;
import com.springboot.Vo.request.DataEncryptVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;

/**
 * @ClassName DataEncryptUtils
 * @Author sangfor for tangbo
 * @Description 数据加密工具类
 * @Date 2020/5/21 15:28
 * @Version 1.0.0
 **/
@Component
public class DataEncryptBiz {

    private String signTag = "&sign=";

    @Value("${rsa.public.key:1}")
    private String rsaPublicKey;

    @Value("${rsa.private.key:2}")
    private String rsaPrivateKey;

    public DataEncryptVo encryptData(String data) throws Exception {
        String sign = RSAUtils.sign(data.getBytes(), rsaPrivateKey);
        String signData = data + signTag + sign;
        String dataBase64 = Base64Utils.encode(signData.getBytes());
        String aesKey = AESEncryptUtils.createSecretKey();
        String body = AESEncryptUtils.AESDncode(aesKey, dataBase64);
        String skey = new String(RSAUtils.encryptByPublicKey(aesKey.getBytes(), rsaPublicKey));
        return DataEncryptVo.builder().skey(skey).body(body).build();
    }

    public String decodeData(DataEncryptVo dataEncryptVo) throws Exception {
        String aesKey = new String(RSAUtils.decryptByPrivateKey(dataEncryptVo.getSkey().getBytes(), rsaPrivateKey));
        String dataBase64 = AESEncryptUtils.AESDncode(aesKey, dataEncryptVo.getBody());
        String signData = new String(Base64Utils.decode(dataBase64));
        String[] signDataList = signData.split(signTag);
        String data = signDataList[0];
        String sign = signDataList[1];
        boolean signResult = RSAUtils.verify(data.getBytes(), rsaPublicKey, sign);
        if (!signResult){
            throw new RuntimeException("验签失败");
        }
        return data;
    }
}
