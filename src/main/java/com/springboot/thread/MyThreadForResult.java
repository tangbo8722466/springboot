package com.springboot.thread;

import lombok.extern.log4j.Log4j;

import java.util.concurrent.Callable;

/**
 * @ClassName MyThreadForResult
 * @Author sangfor for tangbo
 * @Description 线程有结果返回
 * @Date 2020/4/29 13:53
 * @Version 1.0.0
 **/
@Log4j
public class MyThreadForResult implements Callable<String> {
    private int count;

    public MyThreadForResult(int count) {
        this.count = count;
    }

    @Override
    public String call() throws Exception {
        String threadName = Thread.currentThread().getName();
        log.info("MyThreadForResult "+ threadName +" runing ...");
        log.info("MyThreadForResult "+ threadName +" sleep start ...");
        Thread.currentThread().sleep(count * 1000);
        log.info("MyThreadForResult "+ threadName +" sleep end ...");
        if (count % 2 == 0) {
            throw new Exception(threadName + "Count:["+count+"]运行异常");
        }
        return threadName + "Count:["+count+"]运行成功";
    }
}
