package com.service;

import com.dao.ClazzDao;
import com.entity.ClazzEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("clazzService")
public class ClazzService {

    @Autowired
    private static ClazzDao clazzDao;

    public static ClazzDao getClazzDao() {
        return clazzDao;
    }

    public static void setClazzDao(ClazzDao clazzDao) {
        ClazzService.clazzDao = clazzDao;
    }

    @Transactional
    public boolean exist(String clazzName) {
        return clazzDao.exists(clazzName);
    }

    @Transactional
    public void persist(ClazzEntity entity) {
        clazzDao.persist(entity);
    }

    @Transactional
    public ClazzEntity findByName(String name) {
        return clazzDao.findByName(name);
    }
}
