package com.baizhi.common;

import java.util.List;

/**
 * 负载均衡器接口
 *
 * @author gaozhy
 * @date 2018/3/11.22:26
 */
public interface LoadBalancer {

    /**
     * 负载均衡方法
     *
     * @param existingHostAndNames
     * @return
     */
    HostAndName select(List<HostAndName> existingHostAndNames);
}
