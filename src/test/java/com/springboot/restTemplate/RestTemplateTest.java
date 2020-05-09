package com.springboot.restTemplate;

import com.alibaba.fastjson.JSONObject;
import com.springboot.Utils.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

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
        ResponseEntity<String> responseEntity1 = restTemplate.getForEntity("http://www.baidu.com", String.class);
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
        ResponseEntity<String> responseEntity1 = httpsRestTemplate.getForEntity("http://www.baidu.com", String.class);
        log.info(responseEntity1.getStatusCode() + "，" +responseEntity1.getStatusCodeValue());
        log.info(responseEntity1.getBody());

        ResponseEntity<RestResult> responseEntity2 = httpsRestTemplate.getForEntity("https://localhost:8443/springboot/v1/user", RestResult.class);
        log.info(responseEntity2.getStatusCode() + "，" +responseEntity2.getStatusCodeValue());
        log.info(JSONObject.toJSONString(responseEntity2.getBody()));
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
