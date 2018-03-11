package com.baizhi.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author gaozhy
 * @date 2018/3/9.11:14
 */
public class InvocationHandlerImpl implements InvocationHandler {

    /**
     * 需要代理的真实对象
     */
    private Object object;

    public InvocationHandlerImpl(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("前置通知...");

        Object invoke = method.invoke(object, args);

        System.out.println("后置通知...");

        return invoke;
    }
}
