package com.wenruo;

import com.wenruo.entity.User;
import io.swagger.models.auth.In;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @className: JavaLambadSorted
 * @author: muyao
 * @description: 测试stream 排序
 * @date: 2021/4/13 3:51 下午
 * @version: 1.0
 */
public class JavaLambadSorted {
    private static List<User> users = new ArrayList<>();


    @Test
    public void test1() {
        users.add(new User(1, "muyao", "11111", "1111", "111111",null, null, new BigDecimal(19)));
        users.add(new User(2, "wenruo", "2222", "2222", "2222",null, null, new BigDecimal(20)));

        System.out.println(users);
        BigDecimal price = users.stream().map(User::getPrice).min(BigDecimal::compareTo).get();
        System.out.println(users.size());
//        System.out.println(collect.size());
        System.out.println(price);

    }

    @Test
    public void test2() {
        Function<Integer, Integer> test = i -> i +1;
        System.out.println(test.apply(5));
    }
}
