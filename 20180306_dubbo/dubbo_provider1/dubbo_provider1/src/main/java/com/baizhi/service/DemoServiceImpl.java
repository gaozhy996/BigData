package com.baizhi.service;

/**
 * @author gaozhy
 * @date 2018/3/6.11:44
 */
public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello(String name) {
        System.out.println("sayHello method is invoke");
        return "Hello \t"+name;
    }
}
