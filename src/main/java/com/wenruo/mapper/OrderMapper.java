package com.wenruo.mapper;

import com.wenruo.base.BaseMapper;
import com.wenruo.entity.dto.Order;

import java.util.List;

/**
 * @description: 数据mapper
 * @author: MuYao
 * @date: Created in 2020/6/19 17:49
 * @version: 1.0.0
 */
public interface OrderMapper extends BaseMapper<Order> {

    Order selectOrder();
}
