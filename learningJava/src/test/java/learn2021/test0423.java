package learn2021;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.Test;
import org.springframework.context.ApplicationContext;

public class test0423 {
    @Test
    public void testSpring1(){
        ApplicationContext applicationContext =new ClassPathXmlApplicationContext("spring-bans.xml");
        POJO ent01 = (POJO) applicationContext.getBean(POJO.class);
        System.out.println(ent01);
        ClassPathXmlApplicationContext en=(ClassPathXmlApplicationContext)applicationContext;
        en.refresh();
        en.close();
    }
}
