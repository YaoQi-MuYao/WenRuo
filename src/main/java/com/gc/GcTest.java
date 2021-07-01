package com.gc;

import com.wenruo.entity.User;

import java.util.ArrayList;

/**
 * @className: GcTest
 * @author: muyao
 * @description: 堆的运行机制
 * @date: 2021/3/24 9:33 上午
 * @version: 1.0
 */
public class GcTest {
    public static void main(String[] args) throws InterruptedException {
        ArrayList<User> userArrayList = new ArrayList<>();
        while (true) {
            userArrayList.add(new User());
            Thread.sleep(10);
        }
    }
}
