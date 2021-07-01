package com.concurrency.thired;

import javax.servlet.*;
import java.io.IOException;
import java.math.BigInteger;

/**
 * @className: VolatileCacheFactorizer
 * @author: muyao
 * @description: 使用指向不可变容器对象的volayile类型饮用一缓存最新的结果
 * @date: 2021/3/3 4:05 下午
 * @version: 1.0
 */
public class VolatileCacheFactorizer implements Servlet {
    @Override
    public void init(ServletConfig config) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    private volatile OneValueCache cache = new OneValueCache(null, null);

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
