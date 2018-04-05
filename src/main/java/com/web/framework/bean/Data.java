package com.web.framework.bean;

/**
 * 返回数据对象
 *
 * Created by zhengxianyou on 2018/3/26 0026.
 */
public class Data {

    /**
     * 模型数据
     */
    private Object model;

    public Data(Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }

}
