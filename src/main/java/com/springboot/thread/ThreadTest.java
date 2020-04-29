package com.springboot.thread;

import com.springboot.handler.ThreadExceptionHandler;

/**
 * @ClassName ThreadTest
 * @Author sangfor for tangbo
 * @Description //TODO
 * @Date 2020/4/29 13:35
 * @Version 1.0.0
 **/
public class ThreadTest {
    public static void main(String[] args) {

    }

    public void testSingleThreadExceptionHandler(){
        Thread t = new Thread(new MyThread(1));
        /**
         * 设置异常捕获
         */
        t.setUncaughtExceptionHandler(new ThreadExceptionHandler());
        t.start();
    }
}
