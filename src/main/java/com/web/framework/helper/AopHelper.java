package com.web.framework.helper;

import com.web.framework.annotation.Aspect;
import com.web.framework.annotation.Service;
import com.web.framework.proxy.AspectProxy;
import com.web.framework.proxy.Proxy;
import com.web.framework.proxy.ProxyManager;
import com.web.framework.proxy.TransactionProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * 方法拦截助手类
 *
 * Created by zhengxianyou on 2018/4/1 0001.
 */
public final class AopHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(AopHelper.class);

    static {
        try {
            Map<Class<?>,Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>,List<Proxy>> targetMap = createTargetMap(proxyMap);
            for (Map.Entry<Class<?>,List<Proxy>> targetEntry : targetMap.entrySet()){
                Class<?> targetClass = targetEntry.getKey();
                List<Proxy> proxyList = targetEntry.getValue();
                Object proxy = ProxyManager.createProxy(targetClass,proxyList);
                BeanHelper.setBean(targetClass,proxy);
            }
        } catch (Exception e) {
            LOGGER.error("aop failure",e);
        }
    }


    /**
     * 通过Aspect的值，获取切面注解拦截的所有目标类集合
     *
     * @param aspect
     * @return
     * @throws Exception
     */
    private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception{
        Set<Class<?>> targetClassSet = new HashSet<Class<?>>();
        Class<? extends Annotation> annotation = aspect.value();  //获取切面注解代理的值(注解类)
        if (annotation!=null&&!annotation.equals(Aspect.class)){
            targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }

        return targetClassSet;
    }

    /**
     * 获取代理类（扩展自AspectProxy且带有Aspect注解的切面类）与目标类集合之间的映射关系
     *
     * @return
     * @throws Exception
     */
    private static Map<Class<?>,Set<Class<?>>> createProxyMap() throws Exception{
        Map<Class<?>,Set<Class<?>>> proxyMap = new HashMap<Class<?>, Set<Class<?>>>();
        addAspectProxy(proxyMap);
        addTransactionProxy(proxyMap);
        return proxyMap;
    }

    /**
     * 添加普通切面代理
     *
     * @param proxyMap
     * @throws Exception
     */
    private static void addAspectProxy(Map<Class<?>,Set<Class<?>>> proxyMap) throws Exception{
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);

        for (Class<?> proxyClass : proxyClassSet) {
            if (proxyClass.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(proxyClass, targetClassSet);
            }
        }
    }

    /**
     * 添加事务代理
     *
     * @param proxyMap
     */
    private static void addTransactionProxy(Map<Class<?>,Set<Class<?>>> proxyMap){
        Set<Class<?>> serviceClassSet = ClassHelper.getClassSetByAnnotation(Service.class);
        proxyMap.put(TransactionProxy.class,serviceClassSet);
    }


    /**
     * 获取目标类与代理对象列表之间的映射关系
     *
     * @param proxyMap
     * @return
     * @throws Exception
     */
    private static Map<Class<?>,List<Proxy>> createTargetMap(Map<Class<?>,Set<Class<?>>> proxyMap) throws Exception{
        Map<Class<?>,List<Proxy>> targetMap = new HashMap<Class<?>, List<Proxy>>();

        for (Map.Entry<Class<?>,Set<Class<?>>> proxyEntry : proxyMap.entrySet()){
            Class<?> proxyClass = proxyEntry.getKey();      //代理类
            Set<Class<?>> targetClassSet = proxyEntry.getValue(); //目标类集合

            for (Class<?> targetClass : targetClassSet){
                if (targetMap.containsKey(targetClass)){
                    Proxy proxy = (Proxy) proxyClass.newInstance();
                    targetMap.get(targetClass).add(proxy);
                }else {
                    List<Proxy> proxyList = new ArrayList<Proxy>();
                    targetMap.put(targetClass,proxyList);
                }
            }

        }

        return targetMap;
    }

}
