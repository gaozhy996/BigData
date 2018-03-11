package com.baizhi.common;

import java.util.List;

/**
 * 负载均衡器
 * @author gaozhy
 * @date 2018/3/9.15:23
 */
public interface LoadBalancer {

    /**
     * 选择方法
     *
     * @param hostAndNames
     * @return
     */
    HostAndName select(List<HostAndName> hostAndNames);
}
