package com.baizhi.service;

import java.lang.reflect.Proxy;

/**
 * @author gaozhy
 * @date 2018/3/9.11:17
 */
public class DynamicProxyDemonstration {

    public static void main(String[] args) {

        HelloService helloService = new HelloServiceImpl();

        HelloService hs = (HelloService) Proxy.newProxyInstance(helloService.getClass().getClassLoader(), helloService.getClass().getInterfaces(), new InvocationHandlerImpl(helloService));

        String sayHello = hs.sayHello("test");

        System.out.println(sayHello);
    }
}
