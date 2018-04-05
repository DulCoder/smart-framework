package com.web.framework.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 代理管理器，由切面类调用
 *
 * Created by zhengxianyou on 2018/3/30 0030.
 */
public class ProxyManager {

    /**
     * 传入一个目标类和一组Proxy接口实现类，返回一个代理对象
     *
     * @param targetClass
     * @param proxyList
     * @param <T>
     * @return
     */
    public static <T> T createProxy(final Class<?> targetClass, final List<Proxy> proxyList){
        return (T) Enhancer.create(targetClass, new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                return new ProxyChain(targetClass,o,method,methodProxy,objects,proxyList);
            }
        });
    }

}
