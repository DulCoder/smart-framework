package com.web.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Created by zhengxianyou on 2018/3/30 0030.
 */
public class AspectProxy implements Proxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectProxy.class);

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;

        Class<?> clazz = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();

        begin();


        try {
            if (intercept(clazz, method, params)) {
                before(clazz, method, params);
                result = proxyChain.doProxyChain();
                after(clazz, method, params);
            } else {
                result = proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            LOGGER.error("proxy failure", e);
            error();
            throw e;
        } finally {
            end();
        }

        return result;
    }

    private boolean intercept(Class<?> clazz, Method method, Object[] params) throws Throwable {
        return true;
    }

    private void begin() {
    }

    private void before(Class<?> clazz, Method method, Object[] params) throws Throwable {
    }

    private void after(Class<?> clazz, Method method, Object[] params) throws Throwable {
    }

    private void error() {
    }

    private void end() {
    }

}
