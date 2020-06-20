package com.wenruo;

import com.wenruo.entity.dto.Order;
import com.wenruo.mapper.OrderMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description: 数据测试类
 * @author: MuYao
 * @date: Created in 2020/6/19 17:48
 * @version: 1.0.0
 */
public class OrderTest {

    @Autowired
    private static OrderMapper orderMapper;

    private static final String YEAR = "2019";

    @Test
    public void orderTest() {
        List<Order> outOrderList = new LinkedList<>();

        List<Order> orderMapperList = orderMapper.selectOrder();
        /* 获取2019年的单据 */
        orderMapperList = orderMapperList.stream()
                .peek(order -> {
                    order.getLocalDateTime().substring(0, 4).equals(YEAR);
                }).collect(Collectors.toList());
        /* 按支行号分组 */
        Map<String, List<Order>> collect = orderMapperList.stream()
                .collect(Collectors.groupingBy(Order::getAreaNo));

        /* 分组 */
        collect.keySet().forEach(areaNo -> {
            /* 按小时分组 */
            Map<String, List<Order>> hourMap = collect.get(areaNo).stream()
                    .collect(Collectors.groupingBy(order -> order.getLocalDateTime().substring(0, 10)));
            /* 按天分组 */
            Map<String, List<Order>> dayMap = collect.get(areaNo).stream()
                    .collect(Collectors.groupingBy(order -> order.getLocalDateTime().substring(0, 8)));

            /* 按月分组 */
            Map<String, List<Order>> mouthMap = collect.get(areaNo).stream()
                    .collect(Collectors.groupingBy(order -> order.getLocalDateTime().substring(0, 6)));

            outOrderList.addAll(getDate(hourMap));

            outOrderList.addAll(getDate(dayMap));

            outOrderList.addAll(getDate(mouthMap));

            System.out.println(outOrderList);
        });
    }

    public List<Order> getDate(Map<String, List<Order>> map) {
        List<Order> orderList = new LinkedList<>();

        map.keySet().forEach(localDateTime -> {
            Order orderHour = new Order();
            /* 总交易量 */
            BigDecimal countDeal = map.get(localDateTime).stream()
                    .map(Order::getCountDeal)
                    .reduce(BigDecimal.ZERO,BigDecimal::add);
            /* 最大交易量 */
            BigDecimal maxDeal = map.get(localDateTime).stream()
                    .map(Order::getCountDeal).max(BigDecimal::compareTo)
                    .get();
            /* 总平均交易金额 */
            BigDecimal avDelMoney = map.get(localDateTime).stream()
                    .map(order -> order.getAvgDelMoney().multiply(order.getCountDeal()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add).divide(countDeal);
            orderHour.setLocalDateTime(localDateTime.substring(0,10))
                    .setLogo(1)
                    .setCountDeal(countDeal)
                    .setAvgDelMoney(avDelMoney)
                    .setMaxDeal(maxDeal);
            orderList.add(orderHour);
        });

        return orderList;
    }

}
