package com.web.framework.helper;

import com.web.framework.annotation.Action;
import com.web.framework.bean.Handler;
import com.web.framework.bean.Request;
import com.web.framework.utils.ArrayUtil;
import com.web.framework.utils.CollectionUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 控制器助手
 *
 * Created by zhengxianyou on 2018/3/25 0025.
 */
public final class ControllerHelper {

    /**
     * 用于存放请求与处理器之间的映射关系
     */
    private static final Map<Request,Handler> ACTION_MAP = new HashMap<Request, Handler>();


    static {
        //获取所有的Controller类
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (CollectionUtil.isNotEmpty(controllerClassSet)){
            //遍历Controller类
            for (Class<?> controllerClass : controllerClassSet){
                //获取controller类中定义的方法
                Method[] methods = controllerClass.getDeclaredMethods();
                if (ArrayUtil.isNotEmpty(methods)){
                    //遍历这些方法
                    for (Method method : methods){
                        //判断当前方法是否带有Action注解
                        if (method.isAnnotationPresent(Action.class)){
                            //从Action注解中获取URL映射规则
                            Action action = method.getAnnotation(Action.class);
                            String mapping = action.value();
                            //验证映射规则
                            if (mapping.matches("\\w+:/\\w*")){
                                String[] arrays = mapping.split(":");
                                if (ArrayUtil.isNotEmpty(arrays)&&arrays.length==2){
                                    //获取请求方法与路径
                                    String requestMethod = arrays[0];
                                    String requestPath = arrays[1];

                                    Request request = new Request(requestMethod,requestPath);
                                    Handler handler = new Handler(controllerClass,method);
                                    //初始化映射关系
                                    ACTION_MAP.put(request,handler);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取Handler
     *
     * @param requestMethod
     * @param requestPath
     * @return
     */
    public static Handler getHandler(String requestMethod,String requestPath){
        Request request = new Request(requestMethod,requestPath);
        return ACTION_MAP.get(request);
    }

}
