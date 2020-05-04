package com.wenruo.biz;

import com.wenruo.base.BaseBiz;
import com.wenruo.entity.BaseUser;
import com.wenruo.mapper.BaseUserMapper;
import org.springframework.stereotype.Service;

/**
 * @ClassName BaseUserBiz
 * @Description TODO
 * @Author MuYao
 * @Date 2020/4/24 14:13
 * @Version 1.0
 **/
@Service
public class BaseUserBiz extends BaseBiz<BaseUserMapper, BaseUser> {

    public BaseUser findUserByUserName(String userName) {
        BaseUser baseUser = new BaseUser();
        baseUser.setUserName(userName);
        return selectOne(baseUser);
    }
}
