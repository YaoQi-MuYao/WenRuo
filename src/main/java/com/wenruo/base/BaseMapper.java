package com.wenruo.base;

import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @InterfaceName BaseMapper
 * @Description Mapper通用接口
 * @Author MuYao
 * @Date 2020/4/24 13:43
 * @Version 1.0
 **/
public interface BaseMapper<T> extends tk.mybatis.mapper.common.BaseMapper, MySqlMapper<T>, IdsMapper<T>, ConditionMapper<T>, ExampleMapper<T> {

}
