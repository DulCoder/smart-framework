package com.web.framework.utils;

/**
 * 转型操作工具类
 *
 * Created by zhengxianyou on 2018/3/24 0024.
 */
public final class CastUtil {

    /**
     * 转为String
     *
     * @param obj
     * @return
     */
    public static String castString(Object obj){
        return castString(obj,"");
    }

    /**
     * 转为String
     *
     * @param obj
     * @param defaultValue
     * @return
     */
    public static String castString(Object obj,String defaultValue){
        return obj != null ? String.valueOf(obj):defaultValue;
    }

    /**
     * 转为Double
     *
     * @param obj
     * @return
     */
    public static double castDouble(Object obj){

        return castDouble(obj,0);
    }

    /**
     * 转为Double
     *
     * @param obj
     * @param defaultValue
     * @return
     */
    public static double castDouble(Object obj,double defaultValue){
        double doubleValue = defaultValue;
        if (null!=obj){
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)){
                try {
                    doubleValue = Double.parseDouble(strValue);
                } catch (NumberFormatException e) {
                    doubleValue = defaultValue;
                }
            }
        }

        return doubleValue;
    }

    /**
     * 转为Long
     *
     * @param obj
     * @return
     */
    public static long castLong(Object obj){

        return castLong(obj,0);
    }

    /**
     * 转为Long
     *
     * @param obj
     * @param defaultValue
     * @return
     */
    public static long castLong(Object obj,long defaultValue){
        long longValue = defaultValue;
        if (null!=obj){
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)){
                try {
                    longValue = Long.parseLong(strValue);
                } catch (NumberFormatException e) {
                    longValue = defaultValue;
                }
            }
        }

        return longValue;
    }

    /**
     * 转为int
     *
     * @param obj
     * @return
     */
    public static int castInt(Object obj){

        return castInt(obj,0);
    }

    /**
     * 转为int
     *
     * @param obj
     * @param defaultValue
     * @return
     */
    public static int castInt(Object obj,int defaultValue){
        int intValue = defaultValue;
        if (null!=obj){
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)){
                try {
                    intValue = Integer.parseInt(strValue);
                } catch (NumberFormatException e) {
                    intValue = defaultValue;
                }
            }
        }

        return intValue;
    }

    /**
     * 转为boolean
     *
     * @param obj
     * @return
     */
    public static boolean castBoolean(Object obj){

        return castBoolean(obj,false);
    }

    /**
     * 转为boolean
     *
     * @param obj
     * @param defaultValue
     * @return
     */
    public static boolean castBoolean(Object obj,boolean defaultValue){
        boolean booleanValue = defaultValue;
        if (null!=obj){
           booleanValue = Boolean.parseBoolean(castString(obj));
        }

        return booleanValue;
    }

}
