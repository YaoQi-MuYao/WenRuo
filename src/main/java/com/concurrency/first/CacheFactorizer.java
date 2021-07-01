package com.concurrency.first;

import javax.annotation.concurrent.GuardedBy;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.math.BigInteger;

/**
 * @className: CacheFactorizer
 * @author: muyao
 * @description: 缓存最近执行因数分解的数值及其计算结果的servlet
 * @date: 2021/3/2 10:26 上午
 * @version: 1.0
 */
public class CacheFactorizer {
    @GuardedBy("this") private BigInteger lastNumber;
    @GuardedBy("this") private BigInteger[] lastFactors;
    @GuardedBy("this") private long hits;
    @GuardedBy("this") private long cacheHits;

    public synchronized long getHits() {
        return hits;
    }

    public synchronized double getCacheHiRatio() {
        return (double)cacheHits / (double)hits;
    }

    public void service(ServletRequest request, ServletResponse response) {

    }

    public static void main(String[] args) {
        ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>(){
            @Override
            protected Integer initialValue() {
                return 111;
            }
        };

        System.out.println(threadLocal.get());
    }
}
