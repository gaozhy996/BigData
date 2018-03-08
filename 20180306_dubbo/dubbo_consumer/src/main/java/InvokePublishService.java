import com.baizhi.service.DemoService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author gaozhy
 * @date 2018/3/6.12:06
 */
public class InvokePublishService {
    public static void main(String[] args) throws IOException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

//        DemoService demoService = (DemoService) applicationContext.getBean("demoService1");
//        DemoService demoService1 = (DemoService) applicationContext.getBean("demoService2");
//
//        System.out.println(demoService);
//        System.out.println(demoService1.sayHello("bb"));
        DemoService demoService = (DemoService) applicationContext.getBean("demoService");
        String sayHello = demoService.sayHello("aa");
        System.out.println(sayHello);
        //System.in.read();
    }
}
