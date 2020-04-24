package com.wenruo.controller;

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
public class JDBCController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /* 查询数据库的所有信息 */
    /* 没有实体类，数据库中的东西，怎么获取？ Map */
    @GetMapping("/getUser")
    public List<Map<String, Object>> userList() {
        String sql = "select * from base_user";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        return maps;
    }
}
