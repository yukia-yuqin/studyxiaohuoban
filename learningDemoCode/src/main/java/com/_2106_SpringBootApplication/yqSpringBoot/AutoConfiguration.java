package com._2106_SpringBootApplication.yqSpringBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//@ComponentScan("com.SpringBootApplication.yqSpringBoot")
//@EnableAutoConfiguration
//@Configuration
//@Import({BeanOne.class})
@SpringBootApplication
public class AutoConfiguration {

    public static void main(String[] args) throws Exception {
//        SpringApplication.run(new Class[]{AutoConfiguration.class, InnerControll.class}, args);
        SpringApplication.run(AutoConfiguration.class, args);
    }

//    public static void main(String[] args) throws Exception {
//        ApplicationContext context = new AnnotationConfigApplicationContext(AutoConfiguration.class);
//        System.out.println(context.getBean("autoConfiguration"));
//    }

}
