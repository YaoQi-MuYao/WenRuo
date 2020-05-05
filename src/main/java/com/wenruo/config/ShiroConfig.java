package com.wenruo.config;

import com.wenruo.filter.JWTFilter;
import com.wenruo.realm.MyShiroRealm;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: shiro配置类
 * @author: MuYao
 * @date: Created in 2020/5/2 20:49
 * @version: 1.0.0
 */
@Configuration
public class ShiroConfig {

    /* ShiroFilterFactoryBean */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        /* 使用自定义的过滤器，这里使用JWT自定义的过滤器，支持完全前后端分离 */
        Map<String, Filter> customFilterMap = new HashMap<>(16);
        customFilterMap.put("jwt", new JWTFilter());
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        /* 设置安全管理器 */
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setUnauthorizedUrl("/wenruo/401");
        /* 添加shiro的内置过滤器 */
        /**
         * anon：无需认证就可以访问
         * authc：必须认证才能访问
         * user：必须拥有 记住我才能访问
         * perms：拥有对某个资源的权限才能访问
         * role：拥有某个角色权限才能访问
         */
        Map<String, String> filterMap  = new HashMap<>();
        /* 访问401和404页面不需要通过我们的过滤器 */
        filterMap.put("/wenruo/401", "anon");

        /* swagger不需要拦截 */
        filterMap.put("/swagger/**", "anon");
        filterMap.put("/v2/api-docs", "anon");
        filterMap.put("/swagger-ui.html#", "anon");
        filterMap.put("/swagger-ui.html", "anon");
        filterMap.put("/webjars/**", "anon");
        filterMap.put("/swagger-resources/**", "anon");

        /* 其他正常的请求需要通过我们的过滤器，进行过滤 */
        filterMap.put("/**", "jwt");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        shiroFilterFactoryBean.setFilters(customFilterMap);

        return shiroFilterFactoryBean;
    }

    /* DefaultWebSecurityManager */
    /* 第二步 */
    @Bean
    @Qualifier("securityManager")
    public DefaultWebSecurityManager securityManager(@Qualifier("realm") MyShiroRealm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        /* 关联realm */
        securityManager.setRealm(realm);

        /* 关闭shiro自带的session */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);

        return securityManager;
    }

    /* 创建realm对象，需要自定义类 */
    /* 第一步 */
    @Bean
    public MyShiroRealm realm() {
        return new MyShiroRealm();
    }

    /**
     * 下面代码是添加注解支持
     * @param: []
     * @return: org.apache.shiro.spring.LifecycleBeanPostProcessor
     * @author: MuYao.Zhang
     * @date: 2020/5/3 18:04
     **/
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
