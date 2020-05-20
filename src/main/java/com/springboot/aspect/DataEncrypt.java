package com.springboot.aspect;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName DataEncrypt
 * @Author sangfor for tangbo
 * @Description //TODO
 * @Date 2020/5/19 17:27
 * @Version 1.0.0
 **/
@Component
public class DataEncrypt {

    @DataEncryptAnno
    public Object encryptData(Object obj){
        return obj;
    }

    @DataEncryptAnno
    public <T> List<T> encryptDataList(List<T> obj){
        return obj;
    }

}
