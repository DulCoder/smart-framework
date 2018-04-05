package com.web.framework.test.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by zhengxianyou on 2018/3/28 0028.
 */
public class CGLibDynamicProxy implements MethodInterceptor {
    private static CGLibDynamicProxy instance = new CGLibDynamicProxy();

    private CGLibDynamicProxy() {
    }

    public static CGLibDynamicProxy getInstance() {
        return instance;
    }

    public <T> T getProxy(Class<T> clazz){
        return (T) Enhancer.create(clazz,this);
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        Object result = methodProxy.invokeSuper(o,objects);
        after();
        return result;
    }

    private void before() {
        System.out.println("Before");
    }

    private void after() {
        System.out.println("After");
    }


    public static void main(String[] args){
        Hello hello = CGLibDynamicProxy.getInstance().getProxy(HelloImpl.class);
        hello.say("Specime");
    }

}
