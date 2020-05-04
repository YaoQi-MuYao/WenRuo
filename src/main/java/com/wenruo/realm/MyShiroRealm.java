package com.wenruo.realm;

import com.wenruo.biz.BaseUserBiz;
import com.wenruo.biz.UserBiz;
import com.wenruo.entity.BaseUser;
import com.wenruo.entity.User;
import com.wenruo.utils.JWTUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * @description: ShiroRealm
 * @author: MuYao
 * @date: Created in 2020/5/2 20:56
 * @version: 1.0.0
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    BaseUserBiz baseUserBiz;

    @Autowired
    UserBiz userBiz;

    /**
     * 必须重写此方法，不要问为什么，我也不知道
     * @param: [token]
     * @return: boolean
     * @author: MuYao.Zhang
     * @date: 2020/5/3 18:02
     **/
    @Override
    public boolean supports(AuthenticationToken token) {
        return super.supports(token);
    }

    /**
     * 授权 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     * @param: [principalCollection]
     * @return: org.apache.shiro.authz.AuthorizationInfo
     * @author: MuYao.Zhang
     * @date: 2020/5/3 18:03
     **/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String username = JWTUtil.getUsername(principalCollection.toString());
        User user = userBiz.findUserByUserName(username);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole(user.getRole());
        Set<String> permission = new HashSet<>(Arrays.asList(user.getPermission().split(",")));
        simpleAuthorizationInfo.addStringPermissions(permission);
        return simpleAuthorizationInfo;
    }

    /**
     * 认证
     * @param: [authenticationToken]
     * @return: org.apache.shiro.authc.AuthenticationInfo
     * @author: MuYao.Zhang
     * @date: 2020/5/3 18:03
     **/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        /* 获取用户的用户名和密码 */
        String token = (String) authenticationToken.getCredentials();
        /* 解密获得username，用于和数据库进行对比 */
        String username = JWTUtil.getUsername(token);
        if (username == null) {
            throw new AuthenticationException("token invalid");
        }

        User user = userBiz.findUserByUserName(username);
        if (user == null) {
            throw new AuthenticationException("User didn't existed!");
        }

        if (! JWTUtil.verify(token, username, user.getPassword())) {
            throw new AuthenticationException("Username or password error");
        }

        return new SimpleAuthenticationInfo(token, token, "my_realm");
    }
}
