package com.baizhi.common;

import java.util.List;

/**
 * 注册中心接口
 * @author gaozhy
 * @date 2018/3/11.22:05
 */
public interface Registry {

    String SERVICE_PREFIX = "/rpc";
    String SERVICE_SUFFIX = "/providers";

    /**
     * 注册服务
     */
    void registerService(String targetInterfaceName,HostAndName hostAndName);

    /**
     * 订阅服务 需更新已有的服务列表
     */
    void subscribleService(String targetInterfaceName,List<HostAndName> existingHostAndNames);

    /**
     * 接受服务
     * @return
     */
    List<HostAndName> reciveService(String targetInterfaceName);

    /**
     * 关闭注册中心
     */
    void close();
}
