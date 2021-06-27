package com._2106_SpringBootApplication.outerPkg;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;


//@SpringBootApplication
//@RestController
@EnableAutoConfiguration
@ComponentScan
//@ComponentScan
//@Configuration
public class OuterControll {
    //    @RequestMapping("/outer")
    String home() {
        return "Hello World! outer";
    }
}


