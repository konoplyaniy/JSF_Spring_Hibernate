package com.service;


import com.dao.LocaleDao;
import com.entity.LocaleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("localeService")
public class LocaleService {

    @Autowired
    private static LocaleDao localeDao;

    public static LocaleDao getLocaleDao() {
        return localeDao;
    }

    public static void setLocaleDao(LocaleDao localeDao) {
        LocaleService.localeDao = localeDao;
    }

    @Transactional
    public boolean exist(String locale) {
        return localeDao.exists(locale);
    }

    @Transactional
    public void persist(LocaleEntity entity) {
        localeDao.persist(entity);
    }

    @Transactional
    public LocaleEntity findById(Integer id) {
        return localeDao.findById(id);
    }

    @Transactional
    public LocaleEntity findByName(String name) {
        return localeDao.findByLocale(name);
    }

}
