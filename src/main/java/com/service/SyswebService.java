package com.service;


import com.dao.SyswebDao;
import com.entity.SyswebEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("syswebService")
public class SyswebService {

    @Autowired
    private static SyswebDao syswebDao;

    public static SyswebDao getSyswebDao() {
        return syswebDao;
    }

    public static void setSyswebDao(SyswebDao syswebDao) {
        SyswebService.syswebDao = syswebDao;
    }

    @Transactional
    public boolean exist(String sysweb) {
        return syswebDao.exists(sysweb);
    }

    @Transactional
    public void persist(SyswebEntity entity) {
        syswebDao.persist(entity);
    }

    @Transactional
    public SyswebEntity findById(Integer id) {
        return syswebDao.findById(id);
    }

    @Transactional
    public SyswebEntity findByName(String name) {
        return syswebDao.findByName(name);
    }

}
