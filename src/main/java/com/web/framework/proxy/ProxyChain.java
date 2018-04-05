package com.web.framework.proxy;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 代理链
 *
 * Created by zhengxianyou on 2018/3/30 0030.
 */
public class ProxyChain {
    private final Class<?> targetClass;     //目标类
    private final Object targetObject;      //目标对象
    private final Method targetMethod;      //目标方法
    private final MethodProxy methodProxy;  //方法代理 CGLib提供
    private final Object[] methodParams;    //方法参数

    private List<Proxy> proxyList = new ArrayList<Proxy>();  //代理列表

    private int proxyIndex = 0;   //代理索引

    public ProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod, MethodProxy methodProxy, Object[] methodParams, List<Proxy> proxyList) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.methodParams = methodParams;
        this.proxyList = proxyList;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }

    /**
     * 若未达到proxyList上限，则去除Proxy对象，并调用其doProxy方法
     * 在Proxy接口实现中会提供相应的横切逻辑，并调用doProxyChain方法，直到达到ProxyList上限，
     * 最后调用methodProxy的invokeSuper方法，执行目标对象的业务逻辑
     * @return
     * @throws Throwable
     */
    public Object doProxyChain()throws Throwable{
        Object methodResult;
        if (proxyIndex < proxyList.size()){
            methodResult = proxyList.get(proxyIndex++).doProxy(this);
        }else {
            methodResult = methodProxy.invokeSuper(targetObject,methodParams);
        }

        return methodResult;
    }

}
