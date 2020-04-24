package com.wenruo.base;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName BaseController
 * @Description TODO
 * @Author MuYao
 * @Date 2020/4/24 13:56
 * @Version 1.0
 **/
public class BaseController<Biz extends BaseBiz, Entity> {

    @Autowired
    protected Biz baseBiz;
}
