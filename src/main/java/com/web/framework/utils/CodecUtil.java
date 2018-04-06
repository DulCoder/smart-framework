package com.web.framework.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * URL编码与解码操作工具类
 *
 * Created by zhengxianyou on 2018/3/26 0026.
 */
public final class CodecUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CodecUtil.class);


    /**
     * 将URL编码
     *
     * @param source
     * @return
     */
    public static String encodeURL(String source){
        String target = null;

        try {
            target = URLEncoder.encode(source,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("encode url failure", e);
            throw new RuntimeException(e);
        }

        return target;
    }

    /**
     * 将URL解码
     *
     * @param source
     * @return
     */
    public static String decodeURL(String source){
        String target = null;

        try {
            target = URLDecoder.decode(source,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("decode url failure", e);
            throw new RuntimeException(e);
        }

        return target;
    }

    /**
     * MD5加密
     * @param source
     * @return
     */
    public static String md5(String source){
        return DigestUtils.md5Hex(source);
}

}
