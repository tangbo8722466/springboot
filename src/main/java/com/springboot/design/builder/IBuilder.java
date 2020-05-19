package com.springboot.design.builder;

public interface IBuilder {
    void setAttr1();

    void setAttr2();

    void setAttr3();

    Product getResult();

    default String info() {
        return "IBuilder";
    }
}
