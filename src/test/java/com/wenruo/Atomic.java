package com.wenruo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @projectName: WenRuo
 * @package: com.wenruo
 * @className: Atomic
 * @author: muyao
 * @description: 1.0.0
 * @date: 2021/2/24 11:22 上午
 * @version: 1.0
 */
public class Atomic {
    static AtomicInteger ai = new AtomicInteger(1);

    public static void main(String[] args) {
        System.out.println(ai.getAndIncrement());
        System.out.println(ai.get());
    }
}
