package com.wenruo.controller;

import com.wenruo.biz.UserBiz;
import com.wenruo.common.Result;
import com.wenruo.constant.MsgConstansts;
import com.wenruo.entity.User;
import com.wenruo.entity.dto.LoginRequest;
import com.wenruo.entity.dto.LoginResponse;
import com.wenruo.exception.BusinessException;
import com.wenruo.utils.JWTUtil;
import com.wenruo.utils.ProString;
import com.wenruo.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 登陆类
 * @author: MuYao
 * @date: Created in 2020/5/4 19:18
 * @version: 1.0.0
 */
@RestController
//@RequestMapping("/login")
@Api(value = "登录接口", tags = "登录接口")
public class LoginController {

    @Autowired
    UserBiz userBiz;

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * 登录接口
     * @param: [loginRequest]
     * @return: com.wenruo.common.Result<com.wenruo.entity.dto.LoginResponse>
     * @author: MuYao.Zhang
     * @date: 2020/5/5 17:37
     **/
    @ApiOperation(value = "用户登录", notes = "用户登录")
    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        logger.info("当前登录名==============================>" + loginRequest.getUsername());
        String userName = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        if (ProString.isBlank(userName)){
            throw new BusinessException(MsgConstansts.USERNAME_NULL);
        }

        if (ProString.isBlank(password)) {
            throw new BusinessException(MsgConstansts.PASSWORD_NULL);
        }

        User user = userBiz.findUserByUserName(userName);
        if (null == user) {
            throw new BusinessException(MsgConstansts.USER_INEXISTENCE);
        }

        if (password.equals(user.getPassword())) {

            /* 使用JWT获取令牌 */
            String token = JWTUtil.sign(userName, password);

            /* 将令牌和登录信息返回给前端 */
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setUser(user);
            loginResponse.setToken(token);
            return ResultUtils.successWithData(MsgConstansts.LOGIN_SUCCESS, loginResponse);
        } else {
            throw new BusinessException(MsgConstansts.PASSWORD_FAULT);
        }
    }

    /**
     * 后面的方法都是测试方法
     * @param: []
     * @return: com.wenruo.common.Result
     * @author: MuYao.Zhang
     * @date: 2020/5/5 17:36
     **/
    @GetMapping("/article")
    public Result article() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return ResultUtils.success("You are already logged in");
        } else {
            return ResultUtils.success("You are guest");
        }
    }

    @GetMapping("/require_auth")
    @RequiresAuthentication
    public Result requireAuth() {
        return ResultUtils.success("You are authenticated");
    }

    @GetMapping("/require_role")
    @RequiresRoles("admin")
    public Result requireRole() {
        return ResultUtils.success("You are visiting require_role");
    }

    @GetMapping("/require_permission")
    @RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
    public Result requirePermission() {
        return ResultUtils.success("You are visiting permission require edit,view");
    }

    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result unauthorized() {
        return ResultUtils.success("Unauthorized");
    }

}
