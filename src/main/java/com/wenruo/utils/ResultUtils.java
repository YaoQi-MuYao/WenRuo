package com.wenruo.utils;


import com.wenruo.common.Result;

/**
 * @ClassName ResultUtils
 * @Description 测试SpringBoot JDBC
 * @Author MuYao
 * @Date 2020/4/24 4:20
 * @Version 1.0
 **/
public class ResultUtils {

    public static <T> Result<T> success(){
        return new Result<>(true);
    }

    public static <T> Result<T> success(String msg){
        return new Result<>(msg ,true);
    }

    public static <T> Result<T> successWithData(T data){
        return new Result<>(data);
    }

    public static <T> Result<T> successWithData(String msg , T data){
        return new Result<>(msg , data);
    }

    public static <T> Result<T> successWithData(T data , Long total , Integer curPage , Integer pageSize){
        return new Result<>(data , total , curPage , pageSize);
    }

    public static <T> Result<T> successWithData(T data , Integer total , Integer curPage , Integer pageSize){
        return new Result<>(data , total.longValue() , curPage ,pageSize );
    }

    public static <T> Result<T> successWithData(String msg , T data , Long total , Integer curPage , Integer pageSize){
        return new Result<>(msg , data , total ,curPage ,pageSize);
    }

    public static <T> Result<T> fail(){
        return new Result<>(false);
    }

    public static <T> Result<T> fail(Integer code , String msg){
        return new Result<>(msg,code,false);
    }

    public static <T> Result<T> fail(Throwable e){
        return new Result<>(e);
    }

    public static <T> Result<T> fail(String msg){
        return new Result<>(msg ,false);
    }




}
