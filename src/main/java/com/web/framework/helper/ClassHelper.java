package com.web.framework.helper;

import com.web.framework.annotation.Controller;
import com.web.framework.annotation.Service;
import com.web.framework.utils.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * 类操作助手
 *
 * Created by zhengxianyou on 2018/3/25 0025.
 */
public class ClassHelper {

    /**
     * 定义类集合 用于存放所加载的类
     */
    private static final Set<Class<?>> CLASS_SET;


    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }


    /**
     * 获取应用包名下的所有类
     *
     * @return
     */
    public static Set<Class<?>> getClassSet(){
        return CLASS_SET;
    }


    /**
     * 获取应用包名下的所有Service类
     *
     * @return
     */
    public static Set<Class<?>> getServiceClassSet(){
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> clazz:CLASS_SET){
            if (clazz.isAnnotationPresent(Service.class)){
                classSet.add(clazz);
            }
        }

        return classSet;
    }


    /**
     * 获取应用包名下的所有Controller类
     *
     * @return
     */
    public static Set<Class<?>> getControllerClassSet(){
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> clazz:CLASS_SET){
            if (clazz.isAnnotationPresent(Controller.class)){
                classSet.add(clazz);
            }
        }

        return classSet;
    }


    /**
     * 获取应用包名下的所有Bean类 包括Controller 和 Service
     *
     * @return
     */
    public static Set<Class<?>> getBeanClassSet(){
        Set<Class<?>> beanClassSet = new HashSet<Class<?>>();
       beanClassSet.addAll(getServiceClassSet());
       beanClassSet.addAll(getControllerClassSet());

       return beanClassSet;
    }

    /**
     * 获取应用包名下某父类(接口)的所有子类(实现类)
     *
     * @param superClass
     * @return
     */
    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass){
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> clazz : CLASS_SET){
            if (superClass.isAssignableFrom(clazz)&&!superClass.equals(clazz)){
                classSet.add(clazz);
            }
        }

        return classSet;
    }

    /**
     * 获取应用包名下带有某注解的所有类
     *
     * @param annotationClass
     * @return
     */
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass){
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> clazz : CLASS_SET){
            if (clazz.isAnnotationPresent(annotationClass)){
                classSet.add(clazz);
            }
        }

        return classSet;
    }

}
