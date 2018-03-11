package com.baizhi.common;

/**
 * 传输方法
 * @author gaozhy
 * @date 2018/3/9.15:26
 */
public interface Transport {

    /**
     * 传输方法
     * @param hostAndName
     * @param methodInvokeDataWrap
     */
    void invoke(HostAndName hostAndName,MethodInvokeDataWrap methodInvokeDataWrap);

    /**
     * 关闭方法
     */
    void close();
}
