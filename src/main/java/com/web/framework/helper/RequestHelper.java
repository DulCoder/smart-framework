package com.web.framework.helper;

import com.web.framework.bean.FormParam;
import com.web.framework.bean.Param;
import com.web.framework.utils.ArrayUtil;
import com.web.framework.utils.CodecUtil;
import com.web.framework.utils.StreamUtil;
import com.web.framework.utils.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 请求助手类
 *
 * Created by zhengxianyou on 2018/4/2 0002.
 */
public final class RequestHelper {

    /**
     * 创建请求对象
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static Param createParam(HttpServletRequest request)throws IOException{
        List<FormParam> formParamList = new ArrayList<FormParam>();
        formParamList.addAll(parseParammterNames(request));
        formParamList.addAll(parseInputStream(request));
        return new Param(formParamList);
    }

    /**
     * 解析参数名
     *
     * @param request
     * @return
     */
    public static List<FormParam> parseParammterNames(HttpServletRequest request){
        List<FormParam> formParamList = new ArrayList<FormParam>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()){
            String fieldName = paramNames.nextElement();
            String[] fieldValues = request.getParameterValues(fieldName);
            if (ArrayUtil.isNotEmpty(fieldValues)){
                Object fieldValue;
                if (fieldValues.length==1){
                    fieldValue = fieldValues[0];
                }else {
                    StringBuilder sb = new StringBuilder("");
                    for (int i=0; i<fieldValues.length;i++){
                        sb.append(fieldValues[i]);
                        if (i!=fieldValues.length-1){
                            sb.append(StringUtil.SEPARATOR);
                        }
                    }
                    fieldValue = sb.toString();
                }
                formParamList.add(new FormParam(fieldName,fieldValue));
            }
        }
        return formParamList;
    }

    /**
     * 解析输入流文件
     *
     * @param request
     * @return
     * @throws IOException
     */
    private static List<FormParam> parseInputStream(HttpServletRequest request) throws IOException {
        List<FormParam> formParamList = new ArrayList<FormParam>();
        String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
        if (StringUtil.isNotEmpty(body)){
            String[] kvs = StringUtil.splitString(body,"&");
            if (ArrayUtil.isNotEmpty(kvs)){
                for (String kv : kvs){
                    String[] array = StringUtil.splitString(kv,"=");
                    if (ArrayUtil.isNotEmpty(array)){
                        String fieldName = array[0];
                        String fieldValue = array[1];
                        formParamList.add(new FormParam(fieldName,fieldValue));
                    }
                }
            }
        }
        return formParamList;
    }

}
