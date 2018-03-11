package com.baizhi.client;

import com.baizhi.common.*;
import com.baizhi.common.impl.RpcClientTransport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;

/**
 * @author gaozhy
 * @date 2018/3/11.11:21
 */
public class PpcObjectProxy {

    /**
     * 需要生成代理的目标接口
     */
    private Class<?> targetInterface;

    private Registry registry;

    private LoadBalancer loadBalancer;

    public PpcObjectProxy(Class<?> targetInterface,Registry registry,LoadBalancer loadBalancer) {
        this.targetInterface = targetInterface;
        this.registry = registry;
        this.loadBalancer = loadBalancer;
    }

    /**
     * 创建代理对象方法
     * @return
     */
    public Object createProxy(){
        return Proxy.newProxyInstance(PpcObjectProxy.class.getClassLoader(), new Class<?>[]{targetInterface}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 封装请求参数 targetInterface methodName args parameters
                MethodInvokeData methodInvokeData = new MethodInvokeData(targetInterface, method.getName(), method.getParameterTypes(), args);
                HashMap<String, Object> attachment = new HashMap<>();
                attachment.put("name", "zs");
                MethodInvokeDataWrap methodInvokeDataWrap = new MethodInvokeDataWrap(methodInvokeData, attachment);

                // 由客户端发起网络请求，进行远程调用
                RpcClientTransport rpcClientTransport = new RpcClientTransport();

                // 通过注册中心获取服务提供列表
                System.out.println("接口名："+targetInterface.getName());
                List<HostAndName> hostAndNames = registry.receive(targetInterface.getName());

                registry.subscribe(targetInterface.getName(),hostAndNames);
                if(hostAndNames.size() == 0){
                    throw new RuntimeException("没有可用的服务");
                }

                // 调用负载均衡算法 获取服务提供者
                HostAndName hostAndName = loadBalancer.select(hostAndNames);
                System.out.println(hostAndName.getHostname() + "\t" +hostAndName.getPort());

                // 待修改
                rpcClientTransport.invoke(hostAndName, methodInvokeDataWrap);

                ResultWrap resultWrap = rpcClientTransport.getResultWrap();
                //完整呈现 服务器端的异常信息
                if (resultWrap.getResult().getE() != null) {
                    resultWrap.getResult().getE().printStackTrace();
                }
                return resultWrap.getResult().getReturnValue();
            }
        });

    }
}
