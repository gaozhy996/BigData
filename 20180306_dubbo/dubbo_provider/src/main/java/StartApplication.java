import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author gaozhy
 * @date 2018/3/6.11:53
 */
public class StartApplication {

    public static void main(String[] args) throws IOException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        System.out.println("提供者开始注册服务");
        System.in.read();
    }
}
