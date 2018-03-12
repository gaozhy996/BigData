package com.baizhi.common.impl;

import com.baizhi.common.HostAndName;
import com.baizhi.common.LoadBalancer;

import java.util.List;
import java.util.Random;

/**
 * 随机的负载均衡实现
 *
 * @author gaozhy
 * @date 2018/3/12.10:30
 */
public class RandomLoadBalancer implements LoadBalancer {

    @Override
    public HostAndName select(List<HostAndName> existingHostAndNames) {

        if(existingHostAndNames != null && existingHostAndNames.size() != 0){
            // 获取随机数 0 ~ length-1 取对应下标的元素 作为远程调用主机
            Random random = new Random();
            int index = random.nextInt(existingHostAndNames.size());
            return existingHostAndNames.get(index);
        }
        return null;
    }
}
