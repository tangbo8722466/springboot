package com.springboot.handler;

import lombok.extern.log4j.Log4j;

/**
 * @ClassName ThreadExceptionHandler
 * @Author sangfor for tangbo
 * @Description //TODO
 * @Date 2020/4/29 13:44
 * @Version 1.0.0
 **/
@Log4j
public class ThreadExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error("线程信息："+ t.toString());
        log.error("异常信息 {}", e);
    }
}
