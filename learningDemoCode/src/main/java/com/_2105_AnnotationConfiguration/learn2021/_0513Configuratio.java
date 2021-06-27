package com._2105_AnnotationConfiguration.learn2021;

import com._2105_AnnotationConfiguration.Dao.APPDao;
import com._2105_AnnotationConfiguration.Dao.Impl.APPDaoImpl;
import com._2105_AnnotationConfiguration.Service.APPService;
import com._2105_AnnotationConfiguration.Service.Impl.APPServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class _0513Configuratio {
    @Bean
    public APPService app1() {
        APPServiceImpl appService = new APPServiceImpl();
        appService.setAppDao(appDao());
        return appService;
    }

    @Bean
    public APPService app2() {
        APPServiceImpl appService = new APPServiceImpl();
        appService.setAppDao(appDao());
        return appService;
    }

    @Bean
    public APPDao appDao() {
        return new APPDaoImpl();
    }
}
