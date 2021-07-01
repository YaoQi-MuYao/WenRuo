package com.wenruo.biz;

import com.wenruo.base.BaseBiz;
import com.wenruo.entity.dto.Order;
import com.wenruo.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @projectName: WenRuo
 * @package: com.wenruo.biz
 * @className: OrderBiz
 * @author: muyao
 * @description: 1.0.0
 * @date: 2020/12/28 12:36 下午
 * @version: 1.0
 */
@Service
public class OrderBiz extends BaseBiz<OrderMapper, Order> {

    public Order selectOrder() {
        Order order = mapper.selectOrder();
        System.out.println(order);
        Order order1 = Optional.ofNullable(mapper.selectOrder()).orElse(new Order());
        System.out.println(order1);
        return order1;
    }
}
