import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author gaozhy
 * @date 2018/3/9.17:30
 */
public class StartServer {

    public static void main(String[] args) throws IOException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-server.xml");

        System.in.read();
    }
}
