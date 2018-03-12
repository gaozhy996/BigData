package com.baizhi.common;

/**
 *
 * @author gaozhy
 * @date 2018/3/11.22:19
 */
public interface Transporter {

    /**
     * 远程调用方法
     *
     * @param methodInvokeDataWrap
     * @param hostAndName
     * @return
     */
    ResultWrap invoke(MethodInvokeDataWrap methodInvokeDataWrap,HostAndName hostAndName);

    /**
     * 关闭资源
     */
    void close();
}
