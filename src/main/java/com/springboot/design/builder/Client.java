package com.springboot.design.builder;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @ClassName Client
 * @Author sangfor for tangbo
 * @Description //TODO
 * @Date 2020/5/6 15:20
 * @Version 1.0.0
 **/
@Slf4j
public class Client {
    @Test
    public void test(){
        IBuilder builder = new Builder();
        Director director = new Director(builder);
        Product product = director.construct();
        log.info(product.toString());
    }
}
