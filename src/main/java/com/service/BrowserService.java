package com.service;

import com.dao.BrowserDao;
import com.entity.BrowserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("browserService")
public class BrowserService {

    @Autowired
    private static BrowserDao browserDao;

    public static BrowserDao getBrowserDao() {
        return browserDao;
    }

    public static void setBrowserDao(BrowserDao browserDao) {
        BrowserService.browserDao = browserDao;
    }

    @Transactional
    public boolean exist(BrowserEntity browserEntity) {
        return browserDao.exists(browserEntity.getBrowser());
    }

    @Transactional
    public void persist(BrowserEntity entity) {
        browserDao.persist(entity);
    }

    @Transactional
    public BrowserEntity findByName(String name) {
        BrowserEntity browser = browserDao.findByName(name);
        return browser;
    }

}
