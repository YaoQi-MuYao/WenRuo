package com.wenruo.common;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @description:
 * @author: MuYao
 * @date: Created in 2020/5/3 17:35
 * @version: 1.0.0
 */
public class JWTToken implements AuthenticationToken {

    /** 密钥 */
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
