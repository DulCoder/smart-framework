package com.web.framework.helper;

import com.web.framework.utils.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhengxianyou on 2018/3/25 0025.
 */
public final class BeanHelper {
    /**
     * 定义Bean映射 用于存放Bean类与Bean实例的映射关系
     */
    private static final Map<Class<?>,Object> BEAN_MAP = new HashMap<Class<?>, Object>();

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for (Class<?> beanClass : beanClassSet){
            Object obj = ReflectionUtil.newInstance(beanClass);
            BEAN_MAP.put(beanClass,obj);
        }
    }


    /**
     * 获取Bean映射
     *
     * @return
     */
    public static Map<Class<?>,Object> getBeanMap(){
        return BEAN_MAP;
    }

    /**
     * 设置Bean实例
     *
     * @param clazz
     * @param obj
     */
    public static void setBean(Class<?> clazz,Object obj){
        BEAN_MAP.put(clazz,obj);
    }

    /**
     * 获取Bean实例
     *
     * @param clazz
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> clazz){
        if (!BEAN_MAP.containsKey(clazz)){
            throw new RuntimeException("can not get bean by class: "+clazz);
        }

        return (T)BEAN_MAP.get(clazz);
    }


}
