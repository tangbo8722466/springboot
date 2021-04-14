package com.springboot.restTemplate;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author tangbo
 * @create 2020/02/17 15:32
 * @description RestTemplate配置类:
 * 1.将 HttpClient 作为 RestTemplate 的实现,添加 httpclient 依赖即可
 * 2.设置响应类型和内容类型
 */
@Configuration
public class RestTemplateConfiguration {

    @Bean("restTemplateBuilder")
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder();
    }

    /**
     * 设置超时时间 适用1.5 作废
     * @return
     */
//    @Bean("httpFactory")
//    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
//        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
//        factory.setConnectTimeout(15000);
//        factory.setReadTimeout(5000);
//        return factory;
//    }

    @Bean("httpFactorySupplier")
    Supplier<ClientHttpRequestFactory> httpFactorySupplier() {
        return () -> {
            HttpClientBuilder clientBuilder = HttpClientBuilder.create();
            HttpClient httpClient = clientBuilder.build();
            HttpComponentsClientHttpRequestFactory requestFactory =
                    new HttpComponentsClientHttpRequestFactory(httpClient);
            requestFactory.setConnectTimeout(15000);
            requestFactory.setReadTimeout(5000);
            requestFactory.setBufferRequestBody(false);
            return requestFactory;
        };
    }

    @Bean(name = "restTemplate")
    @Primary
    public RestTemplate restTemplate(Supplier<ClientHttpRequestFactory> httpFactorySupplier, RestTemplateBuilder restTemplateBuilder) {
        RestTemplate restTemplate = restTemplateBuilder
                .additionalMessageConverters(new WxMappingJackson2HttpMessageConverter())
                .requestFactory(httpFactorySupplier)
                .build();
        //设置请求字符为utf-8
        restTemplate.getMessageConverters().forEach(httpMessageConverter -> {
            if (httpMessageConverter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) httpMessageConverter).setDefaultCharset(StandardCharsets.UTF_8);
            }
        });
        return restTemplate;
    }

    /**
     * 适配响应为字符串
     */
    class WxMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
        //设置响应适配
        WxMappingJackson2HttpMessageConverter() {
            List<MediaType> mediaTypes = Arrays.asList(
                    MediaType.TEXT_PLAIN,
                    MediaType.TEXT_HTML,
                    MediaType.APPLICATION_JSON_UTF8
            );
            setSupportedMediaTypes(mediaTypes);// tag6
        }
    }

    /**
     适用1.5 作废
     */
//    @Bean(name = "httpsFactory")
//    public ClientHttpRequestFactory simpleClientHttpsRequestFactory() {
//        SimpleClientHttpRequestFactory factory = new SSLClientHttpRequestFactory();
//        factory.setConnectTimeout(15000);
//        factory.setReadTimeout(300000);
//        return factory;
//    }

    @Bean("httpsFactorySupplier")
    Supplier<ClientHttpRequestFactory> httpsFactorySupplier() {
        return () -> {
            HttpClientBuilder clientBuilder = HttpClientBuilder.create();
            HttpClient httpClient = clientBuilder.build();
            HttpComponentsClientHttpRequestFactory requestFactory =
                    new HttpComponentsClientHttpRequestFactory(httpClient);
            requestFactory.setConnectTimeout(15000);
            requestFactory.setReadTimeout(5000);
            requestFactory.setBufferRequestBody(false);
            return requestFactory;
        };
    }


    @Bean(name = "httpsRestTemplate")
    public RestTemplate httpsRestTemplate(Supplier<ClientHttpRequestFactory> httpsFactorySupplier, RestTemplateBuilder restTemplateBuilder){
        RestTemplate restTemplate = restTemplateBuilder
                .additionalMessageConverters(new WxMappingJackson2HttpMessageConverter())
                .requestFactory(httpsFactorySupplier)
                .build();
        //设置请求字符为utf-8
        restTemplate.getMessageConverters().forEach(httpMessageConverter -> {
            if (httpMessageConverter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) httpMessageConverter).setDefaultCharset(StandardCharsets.UTF_8);
            }
        });
        return restTemplate;
    }

}