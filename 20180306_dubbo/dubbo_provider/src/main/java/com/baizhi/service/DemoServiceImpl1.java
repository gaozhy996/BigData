package com.baizhi.service;

/**
 * @author gaozhy
 * @date 2018/3/8.16:43
 */
public class DemoServiceImpl1 implements DemoService {

    @Override
    public String sayHello(String name) {
        System.out.println("service2 : sayHello method is invoke");
        return "Hello \t"+name;
    }
}