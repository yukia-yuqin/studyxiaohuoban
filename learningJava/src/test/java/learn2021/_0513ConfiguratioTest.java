package learn2021;

import Service.APPService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class _0513ConfiguratioTest {
    @Test
    public void app1_APPDaoImpl() {
        ApplicationContext applicationContext =new AnnotationConfigApplicationContext(_0513Configuratio.class);
        APPService app1 = (APPService) applicationContext.getBean("app1");
        System.out.println(app1);
        APPService app2 = (APPService) applicationContext.getBean("app2");
        System.out.println(app2);
    }
}
