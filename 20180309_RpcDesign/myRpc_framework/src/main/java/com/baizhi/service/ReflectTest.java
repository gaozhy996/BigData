package com.baizhi.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author gaozhy
 * @date 2018/3/12.10:02
 */
public class ReflectTest {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        HelloServiceImpl helloService = new HelloServiceImpl();

        Class<HelloService> c = HelloService.class;

        Method method = c.getMethod("sayHello", String.class);

        Object invoke = method.invoke(helloService, "zhangsan");

        System.out.println(invoke);
    }
}
