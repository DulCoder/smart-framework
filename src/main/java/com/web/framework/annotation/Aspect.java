package com.web.framework.annotation;

import java.lang.annotation.*;

/**
 * 切面注解
 *
 * Created by zhengxianyou on 2018/3/30 0030.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    /**
     * 注解
     * @return
     */
    Class<? extends Annotation> value();
}
