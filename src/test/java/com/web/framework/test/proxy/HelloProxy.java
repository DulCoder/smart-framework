package com.web.framework.test.proxy;

/**
 * Created by zhengxianyou on 2018/3/27 0027.
 */
public class HelloProxy implements Hello {

    private Hello hello;

    public HelloProxy() {
        hello = new HelloImpl();
    }

    @Override
    public void say(String name) {
        before();
        System.out.println(name);
        after();

    }

    private void before() {
        System.out.println("Before");
    }

    private void after() {
        System.out.println("After");
    }


    public static void main(String[] args){
        Hello proxy = new HelloProxy();
        proxy.say("Jack");
    }

}
