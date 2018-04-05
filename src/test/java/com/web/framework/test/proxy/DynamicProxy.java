package com.web.framework.test.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by zhengxianyou on 2018/3/27 0027.
 */
public class DynamicProxy implements InvocationHandler {

    private Object target;

    public DynamicProxy(Object target) {
        this.target = target;
    }

    public <T> T getProxy(){
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                this);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result = method.invoke(target,args);
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
//        Hello hello = new HelloImpl();
//
//        DynamicProxy dynamicProxy = new DynamicProxy(hello);
//
//        Hello helloProxy = (Hello) Proxy.newProxyInstance(hello.getClass().getClassLoader(),
//                hello.getClass().getInterfaces(),
//                dynamicProxy);

        DynamicProxy dynamicProxy = new DynamicProxy(new HelloImpl());
        Hello helloProxy = dynamicProxy.getProxy();

        helloProxy.say("Jackson");

    }

}
