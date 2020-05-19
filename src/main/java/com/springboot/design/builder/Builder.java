package com.springboot.design.builder;

/**
 * @ClassName Builder
 * @Author sangfor for tangbo
 * @Description //TODO
 * @Date 2020/5/6 15:20
 * @Version 1.0.0
 **/
public class Builder implements IBuilder{

    //创建产品
    protected Product product = new Product();

    @Override
    public void setAttr1() {
        product.setAttr1("builder attr1");
    }

    @Override
    public void setAttr2() {
        product.setAttr2("builder attr2");
    }

    @Override
    public void setAttr3() {
        product.setAttr3("builder attr3");
    }

    @Override
    public Product getResult() {
        return product;
    }
}
