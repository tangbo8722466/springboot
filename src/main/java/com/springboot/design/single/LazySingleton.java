package com.springboot.design.single;

import java.util.Optional;

/**
 * @ClassName LazySingleton
 * @Author sangfor for tangbo
 * @Description //懒汉
 * @Date 2020/4/29 10:54
 * @Version 1.0.0
 **/
public class LazySingleton {
    //多线程触发，内存同步
    private static volatile LazySingleton instance = null;
    private LazySingleton() {

    }

    /**
     * 获取实例，防止多线程3+
     * @return
     */
    public static synchronized LazySingleton getInstance(){
      instance = Optional.ofNullable(instance).orElseGet(() -> new LazySingleton());
      return instance;
    }
}
