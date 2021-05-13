package learn2021;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class POJO0502 {
    @Autowired
    private POJO pojo;
    @Test
    public void testAutowiredAnnotationBeanPostProcessor () {
        ApplicationContext applicationContext =new ClassPathXmlApplicationContext("spring-bans.xml");
        POJO ent01 = (POJO) applicationContext.getBean(POJO.class);
        System.out.println(ent01);
        POJO ent2 = (POJO) applicationContext.getBean("user");

        System.out.println(pojo);
        ClassPathXmlApplicationContext en=(ClassPathXmlApplicationContext)applicationContext;
        en.refresh();
        en.close();
    }
}
