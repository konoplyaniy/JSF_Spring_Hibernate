package com.service;

import com.dao.OvertimeDao;
import com.entity.OvertimeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by geser on 16.06.17.
 */
@Service("overtimeService")
public class OvertimeService {
    @Autowired
    OvertimeDao overtimeDao;

    public OvertimeDao getOvertimeDao() {
        return overtimeDao;
    }

    public void setOvertimeDao(OvertimeDao overtimeDao) {
        this.overtimeDao = overtimeDao;
    }

    @Transactional
    public ArrayList<OvertimeEntity> getOvertimesByDateUserId(int  userId, Date date){
        return overtimeDao.getOvertimesByDateUserId(userId, date);
    }

    @Transactional
    public ArrayList<OvertimeEntity> getAllOvertimes(Date date){
        return overtimeDao.getAllOvertimes(date);
    }

    @Transactional
    public void createOvertime(OvertimeEntity overtime){
        overtimeDao.create(overtime);
    }

    @Transactional
    public void updateOvertime(OvertimeEntity overtime){
        overtimeDao.update(overtime);
    }

    @Transactional
    public void deleteteOvertime(OvertimeEntity overtime){
        overtimeDao.delete(overtime);
    }

}
