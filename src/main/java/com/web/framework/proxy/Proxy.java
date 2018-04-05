package com.web.framework.proxy;

/**
 * 代理接口
 *
 * Created by zhengxianyou on 2018/3/30 0030.
 */
public interface Proxy {

    /**
     * 执行链式代理
     *
     * @param proxyChain
     * @return
     * @throws Throwable
     */
    Object doProxy(ProxyChain proxyChain)throws Throwable;
}
