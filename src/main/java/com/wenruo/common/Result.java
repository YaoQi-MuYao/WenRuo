package com.wenruo.common;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ClassName Result
 * @Description 响应数据
 * @Author MuYao
 * @Date 2020/4/24 15:22
 * @Version 1.0
 **/
@Builder
@ToString
@Accessors(chain = true)
@AllArgsConstructor
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /* 成功或失败 */
    @Getter
    @Setter
    private boolean success = true;

    /* 响应码 */
    @Getter
    @Setter
    private Integer code = 200;

    /* 响应消息 */
    @Getter
    @Setter
    private String msg;

    /* 消息类型 */
    @Getter
    @Setter
    private String msgType = TypeEnums.MsgType.message.name();

    /* 返回单条数据 */
    @Getter
    @Setter
    private T data;

    /* 总数 */
    @Getter
    @Setter
    private Long total;

    @Getter
    @Setter
    private Integer curPage;

    @Getter
    @Setter
    private Integer pageSize;

    public Result() {
        super();
    }

    public Result(boolean success) {
        super();
        this.success = success;
    }

    public Result(String msg ,Integer code , boolean success) {
        super();
        this.msg = msg;
        this.code = code;
        this.success = success;
    }

    public Result(String msg , boolean success) {
        super();
        this.msg = msg;
        this.success = success;
    }

    public Result(T data , Long total , Integer curPage , Integer pageSize) {
        super();
        this.curPage = curPage;
        this.pageSize = pageSize;
        this.total = total;
        this.data = data;
    }

    public Result(String msg, T data) {
        super();
        this.msg = msg;
        this.data = data;
    }

    public Result(String msg, T data , Long total ,  Integer curPage , Integer pageSize) {
        super();
        this.curPage = curPage;
        this.pageSize = pageSize;
        this.msg = msg;
        this.total = total;
        this.data = data;
    }

    public Result(T data) {
        super();
        this.data = data;
    }

    public Result(Throwable e) {
        super();
        success = false;
        this.msg = e.getMessage();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


}
