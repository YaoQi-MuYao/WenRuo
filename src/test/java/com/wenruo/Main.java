package com.wenruo;

import com.wenruo.entity.User;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @projectName: WenRuo
 * @package: com.wenruo
 * @className: Test
 * @author: muyao
 * @description: 1.0.0
 * @date: 2020/12/3 5:04 下午
 * @version: 1.0
 */
public class Main {

    private static final AtomicInteger integer = new AtomicInteger(0);
    private int i = 0;

    public static void main(String[] args) {
        final Main main = new Main();
        List<Thread> ts = new ArrayList<>(600);
        long start = System.currentTimeMillis();
        for (int j = 0; j < 100; j ++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10000; i++) {
                        main.count();
                        main.safeCount();
                    }
                }
            });
            ts.add(t);
        }

        for (Thread t : ts) {
            t.start();
        }

        /* 等待所有线程执行完成 */
        for (Thread t : ts) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(main);
        System.out.println(integer.get());
        System.out.println(System.currentTimeMillis() - start);
    }

    /**
     *  使用CAS实现安全线程计数器
     *  @author muyao
     *  @date 2021/1/15 10:13 上午
     *  @param
     */
    private void safeCount() {
        for (;;) {
            int i = integer.get();
            boolean suc = integer.compareAndSet(i, ++i);
            if (suc) {
                break;
            }
        }
    }

    /**
     *  非线程安全计数器
     *  @author muyao
     *  @date 2021/1/15 10:12 上午
     *  @param
     */
    private void count() {
        i++;
    }

}
