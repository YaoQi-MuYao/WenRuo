package com.wenruo.constant;

public interface MsgConstansts {
    String NOT_NULL = "不能为空";

    String NOT_EMPTY = "不能为空";

    String SIZE_LIMIT = "长度不能超过%s";

    String ELEMENT = "页面资源";

    String URI = "后端请求";

    String OPERATE_SUCCESS = "操作成功";

    String VALIDATE_SUCCESS= "校验成功";

    String TOKEN_INVALID = "无效的访问令牌";

    String USERNAME_NULL = "用户名不能为空";

    String PASSWORD_NULL = "密码不能为空";

    String USER_INEXISTENCE = "用户名不存在";

    String PASSWORD_FAULT = "密码错误";

    String LOGIN_SUCCESS = "登录成功";

    String ORG_DISABLED = "用户所属机构已经停用";

    String UNAUTHORIZE = "无操作权限";

    String SYSTEM_ERROR = "系统错误";

    String ORG_ADMIN = "机构管理员";

    String DELETE_OWN_DATA = "只能删除自己创建的数据";

    String UPDATE_OWN_DATA = "只能修改自己创建的数据";

    String OPERATE_OWN_DATA = "只能操作自己创建的数据";

    String MAX_LENGTH = "长度不能大于{max}";

    String MAX_MIN_LENGTH = "长度不能大于{max}，不能小于{min}";

    String INCOMPLETE_BATCHNUMBER_DATE = "产品【%s】的批号效期不完整";

    String LEND_STORE_ORDER_ALREADY_COMPLETE = "借入单【%s】早已全部还出";

    String LEND_EXPORT_ORDER_ALREADY_COMPLETE = "借出单【%s】早已全部还入";

    String IS_NEARLY_EXPIRE_DATE = "产品【%s】已近效期，是否继续该操作";

    String HAS_MORE_NEARLY_EXPIRE_DATE = "产品【%s】存在更近效期的产品，是否继续该操作";

    String EXIST_OVERDUE_PRODUCT = "产品【%s】已过期，不得进行该操作！";

    String RETURN_STORE_NOT_GREATER_THAN_LEND_EXPORT = "产品【%s】还入总量不能大于借出总量！";

    String RETURN_EXPORT_NOT_GREATER_THAN_LEND_STORE = "产品【%s】还出总量不能大于借入总量！";

    String PRODUCTIONDATE_AFTER_OF_VALIDITYDATE = "产品【%s】的生产日期晚于失效日期";

    String PRODUCTION_DATE_AFTER_TODAY = "产品【%s】生产日期大于当前日期";

    String STATUS_VALIDATE_MESSAGE = "只能【%s】【%s】状态的数据";

    String DEPT_DISABLE_ERROR = "部门下存在【启用】的人员，不能进行停用操作";
}
