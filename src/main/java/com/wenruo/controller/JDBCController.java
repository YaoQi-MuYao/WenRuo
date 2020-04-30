package com.wenruo.controller;

import com.alibaba.fastjson.JSON;
import com.wenruo.base.BaseController;
import com.wenruo.biz.BaseUserBiz;
import com.wenruo.common.Result;
import com.wenruo.entity.BaseUser;
import com.wenruo.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @ClassName JDBCController
 * @Description 测试SpringBoot JDBC
 * @Author MuYao
 * @Date 2020/4/21 10:38
 * @Version 1.0
 **/
@RestController
@Api(value = "用户管理" ,tags = "用户管理")
@RequestMapping("/baseUser")
public class JDBCController extends BaseController<BaseUserBiz, BaseUser> {

    @Autowired
    JdbcTemplate jdbcTemplate;


    @GetMapping("/getUser")
    public Result<BaseUser> userList() {
        /* 查询数据库的所有信息 */
        /* 没有实体类，数据库中的东西，怎么获取？ Map */
//        String sql = "select * from base_user";
//        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        return ResultUtils.successWithData(baseBiz.selectById(112));
    }

    @GetMapping("/getOneUser")
    @ApiOperation(value = "获取一个用户", tags = "获取一个用户")
    public Result<BaseUser> getOne() {
        Example example = new Example(BaseUser.class);
        example.createCriteria().andEqualTo("id", 112);
        List<BaseUser> baseUsers = baseBiz.selectByExample(example);
        return ResultUtils.successWithData(baseUsers.get(0));
    }
}
