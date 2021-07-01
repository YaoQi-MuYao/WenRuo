package com.concurrency.thired;

import org.bouncycastle.util.Arrays;

import java.math.BigInteger;

/**
 * @className: OneValueCache
 * @author: muyao
 * @description: 对数值及其因数分解结果进行缓存的不可变容器 不可变类
 * @date: 2021/3/3 3:58 下午
 * @version: 1.0
 */
public class OneValueCache {
    private final BigInteger lastNumber;
    private final BigInteger[] lastFactors;

    public OneValueCache(BigInteger i, BigInteger[] factors) {
        lastNumber = i;
        lastFactors = Arrays.copyOf(factors, factors.length);
    }

    public BigInteger[] getFactors(BigInteger i) {
        if (lastNumber == null || !lastNumber.equals(i)) {
            return null;
        } else {
            return Arrays.copyOf(lastFactors, lastFactors.length);
        }
    }
}
