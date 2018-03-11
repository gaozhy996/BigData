package com.baizhi.service;

/**
 * @author gaozhy
 * @date 2018/3/9.11:13
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String message) {
        return "Hello:\t"+message;
    }
}
