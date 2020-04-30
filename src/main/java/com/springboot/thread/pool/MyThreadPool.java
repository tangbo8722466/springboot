package com.springboot.thread.pool;

import com.springboot.thread.MyThread;
import com.springboot.thread.MyThreadForResult;
import lombok.extern.log4j.Log4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @ClassName MyThreadPool
 * @Author sangfor for tangbo
 * @Description //TODO
 * @Date 2020/4/29 13:53
 * @Version 1.0.0
 **/
@Log4j
public class MyThreadPool {
    public static void testThreadPool(ExecutorService executorService, int size) {
        List<Integer> data = new ArrayList<>();
        for (int i=1; i <= size; i++) {
            data.add(i);
        }
        List<Future<String>> results = new ArrayList<>();
        data.stream().forEach(v -> results.add(executorService.submit(new MyThreadForResult(v))));

        //shutdown方法：平滑的关闭ExecutorService，当此方法被调用时，ExecutorService停止接收新的任务并且等待已经提交的任务（包含提交正在执行和提交未执行）执行完成。
        //当所有提交任务执行完毕，线程池即被关闭。
        executorService.shutdown();
        //当使用awaitTermination时，主线程会处于一种等待的状态，等待线程池中所有的线程都运行完毕后才继续运行。
        //awaitTermination方法：接收人timeout和TimeUnit两个参数，用于设定超时时间及单位。当等待超过设定时间时，会监测ExecutorService是否已经关闭
        try {
            //等待5S，判断线程池是否关闭
            if (executorService.awaitTermination(6, TimeUnit.SECONDS)) {
                log.info("testThreadPool run success ...");
            } else {
                //关闭超时线程
                log.info("testThreadPool run timeout ...");
                results.stream().forEach(v -> {
                    //关闭超时线程
                    if (!v.isDone()) {
                        log.info(v.toString() + " time out");
                        v.cancel(true);
                    } else {
                        try {
                            log.info(v.toString() + " run success, " + v.get());
                        } catch (InterruptedException e) {
                            log.info(v.toString() + " run InterruptedException, " + e.getMessage());
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            log.info(v.toString() + " run ExecutionException, " + e.getMessage());
                            e.printStackTrace();
                        } catch (Exception e){
                            log.info(v.toString() + " run exception, " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (InterruptedException e) {
            log.info("testThreadPool interrupted ...");
            e.printStackTrace();
        } finally {
            //用shutdown + awaitTermination关闭线程池是最标准的方式。但是这样不能确保子线程按照预想的那样退出。
            //因此还需要 executorService.shutdownNow();来主动中断所有子线程。
            executorService.shutdownNow();
        }
    }

    @Test
    public void testScheduledThreadPool() {
        int size = 10;
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
        for (int i=1; i <= size; i++) {
            if (i % 2 == 0) {
                //延时执行
                executorService.scheduleWithFixedDelay(new MyThread(i), 0, 1000, TimeUnit.MILLISECONDS);
            }
            else {
                //周期执行
                executorService.scheduleAtFixedRate(new MyThread(i), 0, 1000, TimeUnit.MILLISECONDS);
            }
        }
        //shutdown方法：平滑的关闭ExecutorService，当此方法被调用时，ExecutorService停止接收新的任务并且等待已经提交的任务（包含提交正在执行和提交未执行）执行完成。
        //当所有提交任务执行完毕，线程池即被关闭。
        //executorService.shutdown();
        //当使用awaitTermination时，主线程会处于一种等待的状态，等待线程池中所有的线程都运行完毕后才继续运行。
        //awaitTermination方法：接收人timeout和TimeUnit两个参数，用于设定超时时间及单位。当等待超过设定时间时，会监测ExecutorService是否已经关闭
        try {
            //等待5S，判断线程池是否关闭
           while(!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                log.info("waiting testScheduledThreadPool to stop ...");
           }
           log.info("waiting testScheduledThreadPool run ...");
        } catch (InterruptedException e) {
            log.info("testScheduledThreadPool interrupted ...");
            e.printStackTrace();
        } finally {
            //用shutdown + awaitTermination关闭线程池是最标准的方式。但是这样不能确保子线程按照预想的那样退出。
            //因此还需要 executorService.shutdownNow();来主动中断所有子线程。
            executorService.shutdownNow();
        }
    }

    @Test
    public void testCached() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        testThreadPool(executorService, 10);
    }

    @Test
    public void testFixed() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        testThreadPool(executorService, 10);
    }

    @Test
    public void testSingleton() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        testThreadPool(executorService, 10);
    }

}
