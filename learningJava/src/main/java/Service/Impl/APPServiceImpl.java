package Service.Impl;

import Dao.APPDao;
import Service.APPService;

public class APPServiceImpl implements APPService {

    private APPDao appDao;

    @Override
    public void setAppDao(APPDao appDao) {
        this.appDao = appDao;
    }


}
