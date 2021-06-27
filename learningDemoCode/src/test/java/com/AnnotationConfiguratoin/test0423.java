package com.AnnotationConfiguratoin;

import com._2105_AnnotationConfiguration.learn2021.POJO;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test0423 {
    @Test
    public void testSpring1() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-bans.xml");
        POJO ent01 = (POJO) applicationContext.getBean(POJO.class);
        System.out.println(ent01);
        ClassPathXmlApplicationContext en = (ClassPathXmlApplicationContext) applicationContext;
        en.refresh();
        en.close();
    }
}
