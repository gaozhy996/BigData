package com.baizhi.common;

import java.util.List;

/**
 * 集群容错
 *
 * @author gaozhy
 * @date 2018/3/11.22:28
 */
public interface ClusterFailover {

    /**
     * 容错调用方法
     *
     * @param existingHostAndName  可以调用的服务提供列表
     * @param transporter          调用器
     * @param loadBalancer         负载均衡器
     * @param methodInvokeDataWrap 请求参数
     * @return
     */
    ResultWrap call(List<HostAndName> existingHostAndName,Transporter transporter,LoadBalancer loadBalancer,MethodInvokeDataWrap methodInvokeDataWrap);
}
