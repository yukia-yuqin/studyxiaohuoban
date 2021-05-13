package learn2021;

import Dao.APPDao;
import Dao.Impl.APPDaoImpl;
import Service.APPService;
import Service.Impl.APPServiceImpl;
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
