import com.baizhi.client.PpcObjectProxy;
import com.baizhi.service.HelloService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author gaozhy
 * @date 2018/3/11.12:26
 */
public class RpcClientTest {

    public static void main(String[] args) {

        ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext-client.xml");
        PpcObjectProxy ppcObjectProxy= (PpcObjectProxy) ctx.getBean("ppcObjectProxy");
        HelloService helloService = (HelloService) ppcObjectProxy.createProxy();
        System.out.println(helloService);
        String test = helloService.sayHello("测试");

        System.out.println(test);
    }
}
