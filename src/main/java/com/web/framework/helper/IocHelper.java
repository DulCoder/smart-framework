package com.web.framework.helper;

import com.web.framework.annotation.Inject;
import com.web.framework.utils.ArrayUtil;
import com.web.framework.utils.CollectionUtil;
import com.web.framework.utils.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入助手
 *
 * Created by zhengxianyou on 2018/3/25 0025.
 */
public final class IocHelper {

    static {
        //获取Bean类与其实例之间的映射关系
        Map<Class<?>,Object> beanMap = BeanHelper.getBeanMap();
        if (CollectionUtil.isNotEmpty(beanMap)){
            //遍历
            for (Map.Entry<Class<?>,Object> beanEntry:beanMap.entrySet()){
                //从BeanMap中获取Bean类与其实例
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();

                //获取Bean类定义的所有成员变量
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtil.isNotEmpty(beanFields)){
                    //遍历成员变量
                    for (Field beanField:beanFields){
                        //判断是否带有Inject注解
                        if (beanField.isAnnotationPresent(Inject.class)){
                            //获取Bean Field对应的实例
                            Class<?> beanFieldClass = beanField.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (null != beanFieldInstance){
                                //通过反射初始化BeanField的值
                                ReflectionUtil.setField(beanFieldInstance,beanField,beanInstance);
                            }
                        }
                    }
                }
            }
        }
    }

}
