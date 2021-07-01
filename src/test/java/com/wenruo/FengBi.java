package com.wenruo;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;
import java.util.HashSet;
import java.util.Set;

/**
 * 首先HasSet并不是一个线程安全的，所以被调用的时候会发生线程安全的问题
 * 但是该类的HashSet加了final 并且能够调用他的方法都是加了内置锁的
 * 所以现在是线程安全的
 * 如果不加final也是线程不安全的 因为就会变成一个变量 会存在风险
 * @className: FengBi
 * @author: muyao
 * @description: 通过封闭机制来确定线程安全
 * @date: 2021/3/4 11:21 上午
 * @version: 1.0
 */
@ThreadSafe
public class FengBi {

    @GuardedBy("this")
    private final Set<Person> mySet = new HashSet<>();

    public synchronized void addPerson(Person p) {
        mySet.add(p);
    }

    public synchronized boolean containsPerson(Person p) {
        return mySet.contains(p);
    }
}
