package com.wenruo.exception;

/**
 * @description: 关于业务的统一异常处理，可以作为父类，让具体的业务异常继承
 * @author: MuYao
 * @date: Created in 2020/5/4 18:46
 * @version: 1.0.0
 */
public class BusinessException extends RuntimeException {

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }
}
