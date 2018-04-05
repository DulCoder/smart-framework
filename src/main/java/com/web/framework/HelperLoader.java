package com.web.framework;

import com.web.framework.helper.*;
import com.web.framework.utils.ClassUtil;

/**
 * 加载相应的Helper类
 * <p>
 * Created by zhengxianyou on 2018/3/25 0025.
 */
public final class HelperLoader {

    public static void init() {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                AopHelper.class,    //先通过aop获取代理对象，才能通过IOC进行依赖注入
                IocHelper.class,
                ControllerHelper.class
        };

        for (Class<?> clazz : classList) {
            ClassUtil.loadClass(clazz.getName());
        }
    }

}
