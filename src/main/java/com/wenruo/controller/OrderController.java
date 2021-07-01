package com.wenruo.controller;

import com.sun.deploy.util.OrderedHashSet;
import com.wenruo.biz.OrderBiz;
import com.wenruo.common.Result;
import com.wenruo.entity.dto.Order;
import com.wenruo.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @projectName: WenRuo
 * @package: com.wenruo.controller
 * @className: OrderController
 * @author: muyao
 * @description: 1.0.0
 * @date: 2020/12/28 11:27 上午
 * @version: 1.0
 */
@RestController
@RequestMapping("/order")
public class OrderController {


    @GetMapping("/get")
    public Order getOrder() {
        OrderBiz orderBiz = new OrderBiz();
        return orderBiz.selectOrder();
    }
}
