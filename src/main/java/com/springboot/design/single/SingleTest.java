package com.springboot.design.single;

import lombok.extern.log4j.Log4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName SingleTest
 * @Author sangfor for tangbo
 * @Description //TODO
 * @Date 2020/5/6 10:08
 * @Version 1.0.0
 **/
@Log4j
public class SingleTest {
    @Test
    public void lazySingleton(){
        List<LazySingleton> list = new ArrayList<>();
        List<Thread> threads = Stream.of("thread1", "thread2","thread3", "thread4").map(name -> new Thread(name) {
            @Override
            public void run() {
                list.add(LazySingleton.getInstance());
            }
        }).collect(Collectors.toList());
        for (Thread thread : threads) {
            thread.start();
        }
        log.info("main thread success ...");
        //等待t1,t2执行完成
        while (isThreadAlive(threads)) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

       for (LazySingleton singleton : list){
           log.info(singleton);
       }
    }

    @Test
    public void hungrySingleton(){
        List<HungrySingleton> list = new ArrayList<>();
        List<Thread> threads = Stream.of("thread1", "thread2","thread3", "thread4").map(name -> new Thread(name) {
            @Override
            public void run() {
                list.add(HungrySingleton.getInstance());
            }
        }).collect(Collectors.toList());
        for (Thread thread : threads) {
            thread.start();
        }
        log.info("main thread success ...");
        //等待t1,t2执行完成
        while (isThreadAlive(threads)) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (HungrySingleton singleton : list){
            log.info(singleton);
        }
    }

    public boolean isThreadAlive(List<Thread> threads) {
        for (Thread thread : threads) {
            if (thread.isAlive()) {
                return true;
            }
        }
        return false;
    }

}
