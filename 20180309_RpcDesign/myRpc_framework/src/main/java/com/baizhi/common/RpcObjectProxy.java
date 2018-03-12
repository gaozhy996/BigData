package com.baizhi.common;

import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author gaozhy
 * @date 2018/3/12.11:04
 */
public class RpcObjectProxy implements FactoryBean {

    private Registry registry;

    private Class<?> targetInterface;

    private LoadBalancer loadBalancer;

    private ClusterFailover clusterFailover;

    private Transporter transporter;

    public RpcObjectProxy(Registry registry, Class<?> targetInterface, LoadBalancer loadBalancer,ClusterFailover clusterFailover,Transporter transporter) {
        this.registry = registry;
        this.targetInterface = targetInterface;
        this.loadBalancer = loadBalancer;
        this.clusterFailover = clusterFailover;
        this.transporter = transporter;
    }

    @Override
    public Object getObject() throws Exception {
        return Proxy.newProxyInstance(RpcObjectProxy.class.getClassLoader(), new Class[]{targetInterface}, new InvocationHandler() {
            /**
             * @param proxy 代理对象
             * @param method 目标方法
             * @param args  参数列表
             * @return
             * @throws Throwable
             */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                MethodInvokeData methodInvokeData = new MethodInvokeData(targetInterface, method.getName(), args, method.getParameterTypes());

                MethodInvokeDataWrap methodInvokeDataWrap = new MethodInvokeDataWrap();

                methodInvokeDataWrap.setMethodInvokeData(methodInvokeData);

                ResultWrap resultWrap = clusterFailover.call(registry.reciveService(targetInterface.getName()), transporter, loadBalancer, methodInvokeDataWrap);

                return resultWrap.getResult().getReturnValue();
            }
        });
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
