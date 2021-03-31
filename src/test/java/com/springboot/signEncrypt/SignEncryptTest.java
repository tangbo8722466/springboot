package com.springboot.signEncrypt;

import com.alibaba.fastjson.JSONObject;
import com.springboot.Vo.request.DataEncryptVo;
import com.springboot.Vo.request.UserCreateVo;
import com.springboot.service.biz.DataEncryptBiz;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName RestTemplateTest
 * @Author sangfor for tangbo
 * @Description //TODO
 * @Date 2020/5/9 14:06
 * @Version 1.0.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class SignEncryptTest {
    @Autowired
    @Qualifier("restTemplate")
    RestTemplate restTemplate;

    @Autowired
    @Qualifier("httpsRestTemplate")
    RestTemplate httpsRestTemplate;

    @Value("${rsa.public.key:1}")
    private String rsaPublicKey;

    @Value("${rsa.private.key:2}")
    private String rsaPrivateKey ;

    @Autowired
    DataEncryptBiz dataEncryptBiz;

    @Test
    public void testEncrypt(){
        /**
         * java.lang.RuntimeException: An instance of HttpsURLConnection is expected
         */
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        httpHeaders.setAccept(Stream.of(MediaType.APPLICATION_JSON_UTF8).collect(Collectors.toList()));
        httpHeaders.set("version", "1.0.0");

        UserCreateVo createVo = UserCreateVo.builder().userName("tangbo").account("tangbo").password("123456").perms("user:add,user:update").remark("测试账号").build();
        try {
            DataEncryptVo body = dataEncryptBiz.encryptDataByPrivateKey(JSONObject.toJSONString(createVo), rsaPublicKey);
            HttpEntity httpEntity = new HttpEntity<>(body, httpHeaders);
            ResponseEntity<DataEncryptVo> responseEntity = httpsRestTemplate.postForEntity("https://localhost:8443/springboot/v1/encrypt/user", httpEntity, DataEncryptVo.class);
            log.info(responseEntity.getStatusCode() + "，" +responseEntity.getStatusCodeValue());
            log.info(JSONObject.toJSONString(dataEncryptBiz.decodeDataByPublicKey(responseEntity.getBody(),rsaPublicKey)));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

    }
}
