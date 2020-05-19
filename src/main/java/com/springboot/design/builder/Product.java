package com.springboot.design.builder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName Product
 * @Author sangfor for tangbo
 * @Description //TODO
 * @Date 2020/5/6 15:13
 * @Version 1.0.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private String attr1;
    private String attr2;
    private String attr3;

    @Override
    public String toString() {
        return "Client{" +
                "attr1='" + attr1 + '\'' +
                ", attr2='" + attr2 + '\'' +
                ", attr3='" + attr3 + '\'' +
                '}';
    }

}
