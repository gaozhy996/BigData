package com.baizhi.common;

import java.util.List;

/**
 * 注册中心接口
 *
 * @author gaozhy
 * @date 2018/3/9.15:14
 */
public interface Registry {

    String SERVICE_PREFIX="/rpc/";
    String SERVICE_SUFFIX="/providers";

    /**
     * 注册服务
     * @param c
     * @param hostAndName
     */
    void register(String interfaceName, HostAndName hostAndName);

    /**
     * 订阅服务
     * @param c
     * @param hostAndNames 服务提供者列表
     */
    void subscribe(String interfaceName, List<HostAndName> hostAndNames);

    /**
     * 获取服务
     * @param c
     * @return
     */
    List<HostAndName> receive(String interfaceName);

    /**
     * 关闭注册中心
     */
    void close();
}
