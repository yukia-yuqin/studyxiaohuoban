package com.AnnotationConfiguratoin;

import com._2105_AnnotationConfiguration.Service.APPService;
import com._2105_AnnotationConfiguration.learn2021._0513Configuratio;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class _0513ConfiguratioTest {
    @Test
    public void app1_APPDaoImpl() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(_0513Configuratio.class);
        APPService app1 = (APPService) applicationContext.getBean("app1");
        System.out.println(app1);
        APPService app2 = (APPService) applicationContext.getBean("app2");
        System.out.println(app2);
    }
}
