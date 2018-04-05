package com.web.framework.test.proxy;

/**
 * Created by zhengxianyou on 2018/3/27 0027.
 */
public class HelloImpl implements Hello {
    @Override
    public void say(String name) {
        System.out.println("Hello "+name);
    }
}
