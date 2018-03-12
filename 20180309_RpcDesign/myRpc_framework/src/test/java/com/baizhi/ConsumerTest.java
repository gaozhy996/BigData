package com.baizhi;

import com.baizhi.service.HelloService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author gaozhy
 * @date 2018/3/12.11:19
 */
public class ConsumerTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-consumer.xml");
        HelloService helloService = (HelloService) applicationContext.getBean("proxy");
        System.out.println(helloService.sayHello("aa"));
    }
}
