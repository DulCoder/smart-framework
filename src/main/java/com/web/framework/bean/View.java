package com.web.framework.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回视图对象
 *
 * Created by zhengxianyou on 2018/3/26 0026.
 */
public class View {

    /**
     * 视图路径
     */
    private String path;

    /**
     * 模型数据
     */
    private Map<String,Object> model;

    public View(String path) {
        this.path = path;
        model = new HashMap<String, Object>();
    }

    /**
     * 新创建视图模型
     *
     * @param key
     * @param value
     * @return
     */
    public View addModel(String key,Object value){
        model.put(key,value);
        return this;
    }


    public String getPath() {
        return path;
    }

    public Map<String, Object> getModel() {
        return model;
    }

}
