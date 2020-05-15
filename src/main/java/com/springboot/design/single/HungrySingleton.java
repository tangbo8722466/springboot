package com.springboot.design.single;

/**
 * @ClassName HungrySingleton
 * @Author sangfor for tangbo
 * @Description 饿汉
 * @Date 2020/4/29 10:55
 * @Version 1.0.0
 **/
public class HungrySingleton{
    //添加volatile 会导致第一次重内存中获取实例为null
    //private static volatile HungrySingleton instance = new HungrySingleton();
    private static HungrySingleton instance = new HungrySingleton();

    private HungrySingleton(){

    }

    public static HungrySingleton getInstance(){
        return instance;
    }
}
