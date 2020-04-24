package com.wenruo.controller;

import com.alibaba.fastjson.JSON;
import com.wenruo.base.BaseController;
import com.wenruo.biz.BaseUserBiz;
import com.wenruo.entity.BaseUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ClassName JDBCController
 * @Description 测试SpringBoot JDBC
 * @Author MuYao
 * @Date 2020/4/21 10:38
 * @Version 1.0
 **/
@RestController
public class JDBCController extends BaseController<BaseUserBiz, BaseUser> {

    @Autowired
    JdbcTemplate jdbcTemplate;


    @GetMapping("/getUser")
    public String userList() {
        /* 查询数据库的所有信息 */
        /* 没有实体类，数据库中的东西，怎么获取？ Map */
//        String sql = "select * from base_user";
//        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        return JSON.toJSONString(baseBiz.selectAll());
    }
}
