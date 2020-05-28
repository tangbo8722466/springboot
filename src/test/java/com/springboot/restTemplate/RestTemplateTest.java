package com.springboot.restTemplate;

import com.alibaba.fastjson.JSONObject;
import com.springboot.Utils.RestResult;
import com.springboot.Vo.request.DataEncryptVo;
import com.springboot.Vo.request.UserCreateVo;
import com.springboot.service.biz.DataEncryptBiz;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
public class RestTemplateTest {
    @Autowired
    @Qualifier("restTemplate")
    RestTemplate restTemplate;

    @Autowired
    @Qualifier("httpsRestTemplate")
    RestTemplate httpsRestTemplate;

    @Test
    public void testHttp(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        httpHeaders.setAccept(Stream.of(MediaType.APPLICATION_JSON_UTF8).collect(Collectors.toList()));
        httpHeaders.set("version", "1.0.0");

        JSONObject body = new JSONObject();
        body.put("orderSn","1202047258163078526");

        HttpEntity httpEntity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<String> responseEntity1 = restTemplate.postForEntity("https://test.cdzghome.com:8400/commonPayConfig/queryOrderPayStateNoToken", httpEntity, String.class);
        log.info(responseEntity1.getStatusCode() + "，" +responseEntity1.getStatusCodeValue());
        log.info(responseEntity1.getBody());
        /**
         * org.springframework.web.client.ResourceAccessException: I/O error on GET request for "https://localhost:8443/springboot/v1/user":
         * java.security.cert.CertificateException: No name matching localhost found; nested exception is javax.net.ssl.SSLHandshakeException:
         * java.security.cert.CertificateException: No name matching localhost found

        ResponseEntity<RestResult> responseEntity2 = restTemplate.getForEntity("https://localhost:8443/springboot/v1/user", RestResult.class);
        log.info(responseEntity2.getStatusCode() + "，" +responseEntity2.getStatusCodeValue());
        log.info(JSONObject.toJSONString(responseEntity2.getBody()));
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
         */
    }

    @Test
    public void testHttps(){
        /**
         * java.lang.RuntimeException: An instance of HttpsURLConnection is expected
         */
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        httpHeaders.setAccept(Stream.of(MediaType.APPLICATION_JSON_UTF8).collect(Collectors.toList()));
        httpHeaders.set("version", "1.0.0");

        JSONObject body = new JSONObject();
        body.put("orderSn","1202047258163078526");

        HttpEntity httpEntity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<String> responseEntity1 = httpsRestTemplate.postForEntity("https://test.cdzghome.com:8400/commonPayConfig/queryOrderPayStateNoToken", httpEntity, String.class);
        log.info(responseEntity1.getStatusCode() + "，" +responseEntity1.getStatusCodeValue());
        log.info(responseEntity1.getBody());

//        ResponseEntity<RestResult> responseEntity2 = httpsRestTemplate.getForEntity("https://localhost:8443/springboot/v1/user", RestResult.class);
//        log.info(responseEntity2.getStatusCode() + "，" +responseEntity2.getStatusCodeValue());
//        log.info(JSONObject.toJSONString(responseEntity2.getBody()));
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
