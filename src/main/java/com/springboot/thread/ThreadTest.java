package com.springboot.thread;

import com.springboot.handler.ThreadExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.aop.ThrowsAdvice;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName ThreadTest
 * @Author sangfor for tangbo
 * @Description //TODO
 * @Date 2020/4/29 13:35
 * @Version 1.0.0
 **/
@Slf4j
public class ThreadTest {

    private static Object lock = new Object();

    @Test
    public void testSingleThreadExceptionHandler() {
        Thread t = new Thread(new MyThread(1));
        /**
         * 设置异常捕获
         */
        t.setUncaughtExceptionHandler(new ThreadExceptionHandler());
        t.start();
    }

    @Test
    /**
     * /join()方法：把指定的线程加入到当前线程，可以将两个交替执行的线程合并为顺序执行。
     *  * 比如在线程B中调用了线程A的Join()方法，直到线程A执行完毕后，才会继续执行线程B。
     */
    public void testJoin() {
        Thread t1 = new Thread(new MyThread(1));
        Thread t2 = new Thread(new MyThread(2));
        t1.start();
        t2.start();
        try {
            // t1, t2 执行完成后，主线程才会往下走
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("main thread success ...");
        //等待t1,t2执行完成
        while (t1.isAlive() || t2.isAlive()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    /**
     * interrupt()只是改变中断状态而已，interrupt()不会中断（不是停止线程）一个正在运行的线程。这一方法实际上完成的是，
     * 给线程抛出一个中断信号， 这样受阻线程就得以退出阻塞的状态（不会停止线程。需要用户自己去监视线程的状态为并做处理）。
     * 更确切的说，如果线程被Object.wait, Thread.join和Thread.sleep三种方法之一阻塞，那么，它将接收到一个中断异常
     * （InterruptedException），从而提早地终结被阻塞状态。
     */
    public void testInterrupt() {
        Thread t1 = new Thread(new MyThread(1));
        Thread t2 = new Thread(new MyThread(2));
        t1.start();
        t2.start();
        //触发interrupt exception中断
        t1.interrupt();
        //手动处理中断
        t2.interrupt();

        log.info("main thread success ...");
        //等待t1,t2执行完成
        while (t1.isAlive() || t2.isAlive()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    /**
     * 1、sleep是线程中的方法，但是wait是Object中的方法。
     * 2、sleep方法不会释放lock，但是wait会释放，而且会加入到等待队列中。
     * 3、sleep方法不依赖于同步器synchronized，但是wait需要依赖synchronized关键字。
     * 4、sleep不需要被唤醒（休眠之后推出阻塞），但是wait需要（不指定时间需要被别人中断）。
     */
    public void testSleep() {
        List<Thread> threads = Stream.of("thread1", "thread2").map(name -> new Thread(name) {
            @Override
            public void run() {
                testSleepMethod(Thread.currentThread().getName());
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
    }

    public synchronized void testSleepMethod(String threadName) {
        try {
            log.info(threadName + "开始休眠...");
            Thread.currentThread().sleep(10000);
            log.info(threadName + "结束休眠...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void testWaitMethod(String threadName, long time) {
        synchronized (lock) {
            try {
                if (time > 0) {
                    //超过等待时间，线程将主动被唤起
                    log.info(threadName + "开始等待 " + time + "...");
                    lock.wait(time * 1000);
                    log.info(threadName + "结束等待...");
                } else {
                    //需调用notify方法唤起
                    log.info(threadName + "开始等待...");
                    lock.wait();
                    log.info(threadName + "结束等待...");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void testNotifyMethod(String threadName) {
        synchronized (lock) {
            lock.notify();
            log.info(threadName + "唤起...");
        }
    }

    @Test
    public void testWaitAndNotify1() {
        List<Thread> threads = Stream.of("thread1", "thread2").map(name -> new Thread(name) {
            @Override
            public void run() {
                testWaitMethod(Thread.currentThread().getName(), 1);
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
    }

    @Test
    public void testWaitAndNotify2() {
        List<Thread> threads = Stream.of("thread1", "thread2").map(name -> new Thread(name) {
            @Override
            public void run() {
                testWaitMethod(Thread.currentThread().getName(), 0);
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
    }

    @Test
    public void testWaitAndNotify3() {
        List<Thread> threads1 = Stream.of("thread1").map(name -> new Thread(name) {
            @Override
            public void run() {
                testWaitMethod(Thread.currentThread().getName(), 0);
            }
        }).collect(Collectors.toList());
        List<Thread> threads2 = Stream.of("thread2").map(name -> new Thread(name) {
            @Override
            public void run() {
                testNotifyMethod(Thread.currentThread().getName());
            }
        }).collect(Collectors.toList());

        for (Thread thread : threads1) {
            thread.start();
        }

        for (Thread thread : threads2) {
            thread.start();
        }
        log.info("main thread success ...");
        //等待t1,t2执行完成
        while (isThreadAlive(threads1) || isThreadAlive(threads2)) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
