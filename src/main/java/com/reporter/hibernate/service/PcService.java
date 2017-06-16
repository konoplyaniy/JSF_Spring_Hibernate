package com.reporter.hibernate.service;


import com.reporter.hibernate.dao.PcDao;
import com.reporter.hibernate.entities.PcEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("PcService")
public class PcService {

    @Autowired
    private static PcDao pcDao;

    public static PcDao getPcDao() {
        return pcDao;
    }

    public static void setPcDao(PcDao pcDao) {
        PcService.pcDao = pcDao;
    }

    @Transactional
    public boolean exist(PcEntity pcEntity) {
        return pcDao.exists(pcEntity.getName());
    }

    @Transactional
    public void persist(PcEntity entity) {
        pcDao.persist(entity);
    }

    @Transactional
    public PcEntity findById(Integer id) {
        return pcDao.findById(id);
    }

    @Transactional
    public PcEntity findByName(String name) {
        return pcDao.findByPcName(name);
    }

}
