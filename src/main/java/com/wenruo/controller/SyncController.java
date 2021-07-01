package com.wenruo.controller;

import com.wenruo.biz.OrderBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @className: SyncController
 * @author: muyao
 * @description: 测试controller线程问题
 * @date: 2021/3/25 9:18 上午
 * @version: 1.0
 */
@RestController
@RequestMapping("/sync")
public class SyncController {
    @Autowired
    OrderBiz orderBiz;
    private static final Lock lock = new ReentrantLock();

    @GetMapping("/test")
    public String getTest(@RequestParam Integer integer) {
        synchronized (this) {
            try {
                if (integer == 1) {

                    wait();
                    System.out.println("11111");
                } else {
                    notifyAll();
                    System.out.println("22222");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "33333";

    }
}
