package com.baizhi.service;

/**
 * @author gaozhy
 * @date 2018/3/12.10:01
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String message) {
        System.out.println("提供者方法已调用！");

        return "Hi: "+message;
    }
}
