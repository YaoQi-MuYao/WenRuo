package com.wenruo.controller;

import com.wenruo.common.Result;
import com.wenruo.constant.MsgConstansts;
import com.wenruo.exception.BusinessException;
import com.wenruo.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 全局异常处理
 * @author: MuYao
 * @date: Created in 2020/5/4 18:33
 * @version: 1.0.0
 */
@RestControllerAdvice
@Slf4j
public class ExceptionController {

    /**
     * 捕捉Shiro抛出的异常
     * @param: [e]
     * @return: com.wenruo.common.Result
     * @author: MuYao.Zhang
     * @date: 2020/5/4 18:37
     **/
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public Result handle401(ShiroException e) {
        log.error(e.getMessage());
        return ResultUtils.fail(401, e.getMessage());
    }

    /**
     * 对于没有权限的返回信息
     * @param: [e]
     * @return: com.wenruo.common.Result
     * @author: MuYao.Zhang
     * @date: 2020/5/4 18:42
     **/
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthenticatedException.class)
    public Result handle401(UnauthenticatedException e){
        log.error(e.getMessage());
        return ResultUtils.fail(401, MsgConstansts.TOKEN_INVALID);
    }

    /**
     * 捕捉 UnauthorizedException
     * @param: []
     * @return: com.wenruo.common.Result
     * @author: MuYao.Zhang
     * @date: 2020/5/4 18:38
     **/
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public Result handle401(UnauthorizedException e) {
        log.error(e.getMessage());
        return ResultUtils.fail(401, MsgConstansts.UNAUTHORIZE);
    }

    /**
     * 捕捉其他的异常, 处理所有不可知的异常
     * @param: [request, ex]
     * @return: com.wenruo.common.Result
     * @author: MuYao.Zhang
     * @date: 2020/5/4 18:43
     **/
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result globalException(HttpServletRequest request, Throwable ex) {
        ex.printStackTrace();
        return ResultUtils.fail(getStatus(request).value(), MsgConstansts.SYSTEM_ERROR);
    }

    /**
     * 业务相关异常捕捉
     * @param: [e]
     * @return: com.wenruo.common.Result
     * @author: MuYao.Zhang
     * @date: 2020/5/4 19:01
     **/
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BusinessException.class)
    public Result businessException(BusinessException e){
        return ResultUtils.fail(500 ,e.getMessage());
    }
    
    /**
     *
     * @param: [request]
     * @return: org.springframework.http.HttpStatus
     * @author: MuYao.Zhang 
     * @date: 2020/5/4 18:45      
     **/
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
