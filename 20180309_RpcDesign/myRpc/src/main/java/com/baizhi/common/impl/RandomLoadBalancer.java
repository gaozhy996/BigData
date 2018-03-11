package com.baizhi.common.impl;

import com.baizhi.common.HostAndName;
import com.baizhi.common.LoadBalancer;

import java.util.List;
import java.util.Random;

/**
 * @author gaozhy
 * @date 2018/3/9.16:12
 */
public class RandomLoadBalancer implements LoadBalancer {

    /**
     * 随机的负载均衡器
     * @param hostAndNames
     * @return
     */
    @Override
    public HostAndName select(List<HostAndName> hostAndNames) {
        Random random = new Random();
        int index = random.nextInt(hostAndNames.size());
        return hostAndNames.get(index);
    }
}
