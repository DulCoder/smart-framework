package com.web.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * 属性文件工具类
 *
 * Created by zhengxianyou on 2018/3/24 0024.
 */
public final class PropsUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);

    /**
     * 加载属性文件
     *
     * @param fileName
     * @return
     */
    public static Properties loadProps(String fileName){
        Properties props = null;
        InputStream is = null;

        try {
        is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        if (null == is){
                throw new FileNotFoundException(fileName+" file is not found");
        }
        props = new Properties();
        props.load(is);

        } catch (IOException e) {
            LOGGER.error("load properties is failure ",e);
        }finally {
            if (null != is){
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.error("close InputStream failure ",e);
                }
            }
        }

        return props;
    }

    /**
     * 获取字符属性
     *
     * @param props
     * @param key
     * @return
     */
    public static String getString(Properties props,String key){
        return getString(props,key,"");
    }


    /**
     * 获取字符属性
     *
     * @param props
     * @param key
     * @param defaultValue  默认值
     * @return
     */
    public static String getString(Properties props,String key,String defaultValue){
        String value = defaultValue;
        if (props.containsKey(key)){
            value = props.getProperty(key);
        }

        return value;
    }

    /**
     * 获取数值属性
     *
     * @param props
     * @param key
     * @return
     */
    public static int getInt(Properties props,String key){
        return getInt(props,key,0);
    }

    /**
     * 获取数值属性
     *
     * @param props
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public static int getInt(Properties props, String key, int defaultValue) {
        int value = defaultValue;
        if (props.containsKey(key)){
            value = CastUtil.castInt(props.getProperty(key));
        }

        return value;
    }

    /**
     * 获取布尔属性
     *
     * @param props
     * @param key
     * @return
     */
    public boolean getBoolean(Properties props, String key) {
        return getBoolean(props,key,false);
    }

    /**
     * 获取布尔属性
     *
     * @param props
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public boolean getBoolean(Properties props, String key, Boolean defaultValue) {
        boolean value = defaultValue;
        if (props.containsKey(key)){
            value = CastUtil.castBoolean(props.getProperty(key));
        }

        return value;
    }


}
