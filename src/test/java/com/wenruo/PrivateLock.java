//package com.wenruo;
//
//import javax.annotation.concurrent.GuardedBy;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.ReentrantLock;
//
///**
// * @className: PrivateLock
// * @author: muyao
// * @description: 私有锁
// * @date: 2021/3/4 3:32 下午
// * @version: 1.0
// */
//public class PrivateLock {
//    private final Object myLock = new Object();
//    @GuardedBy("myLock") Person person;
//
//    void someMethod(Person person) {
//        synchronized (myLock) {
//            this.person = person;
//            System.out.println(person.getAge());
//        }
//    }
//    static volatile AtomicInteger j = new AtomicInteger(1);
//    public static void main(String[] args) {
//        System.out.println("主线程");
//        Lock lock = new ReentrantLock();
//        for (int i = 12; i > 0; i--) {
//            Integer finalI = i;
//            new Thread(() -> {
//                lock.lock();
//                System.out.println("子线程" + finalI);
//                PrivateLock privateLock = new PrivateLock();
//                Person person = new Person();
//                person.setAge(finalI);
//                privateLock.someMethod(person);
//                System.out.println("子线程结束" + finalI);
//                lock.unlock();
//            }).start();
//        }
//        System.out.println("主线程结束");
//    }
//}
