package com.wenruo.biz;

import com.wenruo.base.BaseBiz;
import com.wenruo.entity.BaseUser;
import com.wenruo.entity.User;
import com.wenruo.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: MuYao
 * @date: Created in 2020/5/4 17:49
 * @version: 1.0.0
 */
@Service
public class UserBiz extends BaseBiz<UserMapper, User> {

    public User findUserByUserName(String userName) {
        User user = new User();
        user.setUserName(userName);
        return selectOne(user);
    }
}
