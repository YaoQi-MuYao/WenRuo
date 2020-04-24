//package com.wenruo.config;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import com.alibaba.druid.support.http.StatViewServlet;
//import com.alibaba.druid.support.http.WebStatFilter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.servlet.Filter;
//import javax.sql.DataSource;
//import java.sql.SQLException;
//
//
///**
// * @ClassName DruidConfig
// * @Description TODO
// * @Author Lenovo
// * @Date 2020/4/22 9:48
// * @Version 1.0
// **/
//@Configuration
//@EnableConfigurationProperties({DruidDataSourceProperties.class})
//public class DruidConfig {
//
//    @Autowired
//    private DruidDataSourceProperties properties;
//
//
//    @Bean
//    @ConditionalOnMissingBean
//    public DataSource druidDataSource() {
//        DruidDataSource druidDataSource = new DruidDataSource();
//        druidDataSource.setDriverClassName(properties.getDriverClassName());
//        druidDataSource.setUrl(properties.getUrl());
//        druidDataSource.setUsername(properties.getUsername());
//        druidDataSource.setPassword(properties.getPassword());
//        druidDataSource.setInitialSize(properties.getInitialSize());
//        druidDataSource.setMinIdle(properties.getMinIdle());
//        druidDataSource.setMaxActive(properties.getMaxActive());
//        druidDataSource.setMaxWait(properties.getMaxWait());
//        druidDataSource.setTimeBetweenEvictionRunsMillis(properties.getTimeBetweenEvictionRunsMillis());
//        druidDataSource.setMinEvictableIdleTimeMillis(properties.getMinEvictableIdleTimeMillis());
//        druidDataSource.setValidationQuery(properties.getValidationQuery());
//        druidDataSource.setTestWhileIdle(properties.isTestWhileIdle());
//        druidDataSource.setTestOnBorrow(properties.isTestOnBorrow());
//        druidDataSource.setTestOnReturn(properties.isTestOnReturn());
//        druidDataSource.setPoolPreparedStatements(properties.isPoolPreparedStatements());
//        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(properties.getMaxPoolPreparedStatementPerConnectionSize());
//
//        try {
//            druidDataSource.setFilters(properties.getFilters());
//            druidDataSource.init();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return druidDataSource;
//    }
//
//    /* 后台监控 */
//    /**
//     * @Description:  注册Servlet信息， 配置监控视图
//     * @param
//     * @return org.springframework.boot.web.servlet.ServletRegistrationBean<com.alibaba.druid.support.http.StatViewServlet>
//     * @author MuYao.Zhang
//     * @date 2020/4/22 11:07
//     */
//    @Bean
//    @ConditionalOnMissingBean
//    public ServletRegistrationBean<StatViewServlet> statViewServlet() {
//        ServletRegistrationBean<StatViewServlet> statViewServletServletRegistrationBean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/**");
//        /* 后台需要有人登录，账号密码配置 */
//        /* 增加配置 */
//        /* 允许谁能访问 */
//        statViewServletServletRegistrationBean.addInitParameter("allow","");
//        /* 登录的key 是固定的loginUsername loginPassword */
//        /* 登录查看信息的账号密码, 用于登录Druid监控后台 */
//        statViewServletServletRegistrationBean.addInitParameter("loginUsername","admin");
//        statViewServletServletRegistrationBean.addInitParameter("loginPassword","123456");
//        /* 禁止谁能访问 */
////        statViewServletServletRegistrationBean.addInitParameter("wenruo","127.0.0.1");
//        //是否能够重置数据.
//        statViewServletServletRegistrationBean.addInitParameter("resetEnable", "true");
//
//        return statViewServletServletRegistrationBean;
//    }
//
//    /**
//     * @Description: 注册Filter信息, 监控拦截器
//     * @param
//     * @return org.springframework.boot.web.servlet.FilterRegistrationBean<javax.servlet.Filter>
//     * @author MuYao.Zhang
//     * @date 2020/4/22 11:06
//     */
//    @Bean
//    @ConditionalOnMissingBean
//    public FilterRegistrationBean<Filter> filterRegistrationBean() {
//        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<Filter>();
//        filterRegistrationBean.setFilter(new WebStatFilter());
//        filterRegistrationBean.addUrlPatterns("/*");
//        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
//        return filterRegistrationBean;
//    }
//}
