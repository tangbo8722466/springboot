package com.springboot.Utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName DataEncryptUtils
 * @Author sangfor for tangbo
 * @Description //TODO
 * @Date 2020/5/11 17:08
 * @Version 1.0.0
 **/
@Slf4j
public class DataEncryptUtils {

    /**
     * 手机号脱敏
     * @param mobile
     * @return
     */
    public static String mobileEncrypt(String mobile){
        if(StringUtils.isBlank(mobile) || (mobile.length() != 11)){
            return mobile;
        }
        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
    }

    /**
     * 身份证脱敏
     * @param idCard
     * @return
     */
    public static String idCardEncrypt(String idCard){
        if(StringUtils.isBlank(idCard) || (idCard.length() < 8)){
            return idCard;
        }
        return idCard.replaceAll("(?<=\\w{3})\\w(?=\\w{4})", "*");
    }

    /**
     * 银行卡脱敏
     * @param bankCardNo
     * @return
     */
    public static String bankCardNoEncrypt(String bankCardNo){
        if(StringUtils.isBlank(bankCardNo) || (bankCardNo.length() < 7)){
            return bankCardNo;
        }
        return bankCardNo.replaceAll("(?<=\\w{2})\\w(?=\\w{4})", "*");
    }

    /**
     * id脱敏
     * @param id
     * @return
     */
    public static String idEncrypt(String id){
        if(StringUtils.isBlank(id) || (id.length() < 7)){
            return id;
        }
        return id.replaceAll("(?<=\\w{2})\\w(?=\\w{4})", "*");
    }
}
