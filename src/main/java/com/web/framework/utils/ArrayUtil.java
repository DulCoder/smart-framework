package com.web.framework.utils;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 数组工具类
 *
 * Created by zhengxianyou on 2018/3/25 0025.
 */
public final class ArrayUtil {

    /**
     * 判断数组是否非空
     *
     * @param array
     * @return
     */
    public static boolean isNotEmpty(Object[] array){
        return !ArrayUtils.isEmpty(array);
    }


    /**
     * 判断数组是否为空
     *
     * @param array
     * @return
     */
    public static boolean isEmpty(Object[] array){
        return ArrayUtils.isEmpty(array);
    }


}
