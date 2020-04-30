package com.springboot.thread;

import lombok.extern.log4j.Log4j;

/**
 * @ClassName MyThread
 * @Author sangfor for tangbo
 * @Description 线程无结果返回
 * @Date 2020/4/29 13:33
 * @Version 1.0.0
 **/
@Log4j
public class MyThread implements Runnable{
    private int count;

    public MyThread(int count) {
        this.count = count;
    }

    @Override
    public void run(){
        String threadName = Thread.currentThread().getName();
        log.info("MyThread "+ threadName +" runing ...");
        log.info("MyThread "+ threadName +" sleep start ...");
        try {
            if (count % 2 == 0) {
                for (int i = 0; i < count; i++) {
                    if (Thread.currentThread().isInterrupted()) {
                        log.info("MyThread " + threadName + " interrupt end ...");
                        return;
                    }
                    Thread.currentThread().sleep(1000);
                }
            }
            else{
                Thread.currentThread().sleep(count * 1000);
            }
        } catch (InterruptedException e) {
            log.info("MyThread " + threadName + " interrupt exception end ...");
            e.printStackTrace();
            return;
        }
        log.info("MyThread "+ threadName +" sleep end ...");
        if (count % 2 == 0) {
            throw new RuntimeException(threadName + "Count:["+count+"]运行异常");
        }
        log.info(threadName + "Count:["+count+"]运行成功");
    }
}
