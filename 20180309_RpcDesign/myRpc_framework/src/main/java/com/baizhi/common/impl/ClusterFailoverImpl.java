package com.baizhi.common.impl;

import com.baizhi.common.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 集群容错
 * @author gaozhy
 * @date 2018/3/12.10:49
 */
public class ClusterFailoverImpl implements ClusterFailover {
    /**
     * 大概思路
     *    首先从订阅的服务列表中 随机挑选一个服务进行PRC调用，如果调用过程中出现异常，则使用其它的服务进行调用
     * @param existingHostAndName  可以调用的服务提供列表
     * @param transporter          调用器
     * @param loadBalancer         负载均衡器
     * @param methodInvokeDataWrap 请求参数
     * @return
     */
    @Override
    public ResultWrap call(List<HostAndName> existingHostAndName, Transporter transporter, LoadBalancer loadBalancer, MethodInvokeDataWrap methodInvokeDataWrap) {

        List<HostAndName> backupHostAndName = new ArrayList<HostAndName>();

        backupHostAndName.addAll(existingHostAndName);

        HostAndName hostAndName = loadBalancer.select(backupHostAndName);

        ResultWrap resultWrap = null;
        try {
            while(backupHostAndName.size() >=1 ){
                hostAndName = loadBalancer.select(backupHostAndName);
                try {
                    resultWrap = transporter.invoke(methodInvokeDataWrap,hostAndName);
                    break;
                } catch (Exception e1) {
                    e1.printStackTrace();
                    backupHostAndName.remove(hostAndName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            backupHostAndName.remove(hostAndName);
        }
        return resultWrap;
    }
}
