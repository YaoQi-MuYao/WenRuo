package com.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

/**
 * @className: JavaPromise
 * @author: muyao
 * @description: Java 异步执行任务 JDK1.8以上
 * @date: 2021/3/19 3:01 下午
 * @version: 1.0
 */
public class JavaPromise {

    public static void main(String[] args) {

        String a = getA();
        System.out.println(a);
    }

    /**
     *  通过Java8 实现异步
     *  Java8之前的future不算真正的异步
     *  因为需要使用get来获得异步返回的信息使阻塞整个线程
     *  不能达到异步去获取任务数据
     *  而Java8 提供的异步任务是可以不要阻塞线程来获取数据
     *  这里创建线程池 不要使用Executors
     *  应该直接使用 threadPoolExecutor 创建防止生成无界队列内存逸出
     *  @author muyao
     *  @date 2021/3/19 3:10 下午
     */
    public static String getA() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CompletableFuture<String> future = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                System.out.println("task started!");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "task finished!";
            }
        }, executorService);

        future.thenAccept(e -> System.out.println(e + " ok"));

        System.out.println("main thread is running");
        return "muyao";
    }
}
