package com.springboot.design.builder;

/**
 * @ClassName Director
 * @Author sangfor for tangbo
 * @Description //TODO
 * @Date 2020/5/6 15:18
 * @Version 1.0.0
 **/
public class Director {
    private IBuilder builder;
    public Director(IBuilder builder){
        this.builder = builder;
    }

    public Product construct(){
        builder.setAttr1();
        builder.setAttr2();
        builder.setAttr3();
        return builder.getResult();
    }
}
