package com._2105_AnnotationConfiguration.Service.Impl;

import com._2105_AnnotationConfiguration.Dao.APPDao;
import com._2105_AnnotationConfiguration.Service.APPService;

public class APPServiceImpl implements APPService {

    private APPDao appDao;

    @Override
    public void setAppDao(APPDao appDao) {
        this.appDao = appDao;
    }


}
