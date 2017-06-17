package com.service;

import com.dao.UpdateControlDao;
import com.entity.UpdateControlEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by geser on 16.06.17.
 */
@Service("UpdateControlService")
public class UpdateControlService {
    @Autowired
    UpdateControlDao updateControlDao;

    public UpdateControlDao getUpdateControlDao() {
        return updateControlDao;
    }

    public void setUpdateControlDao(UpdateControlDao updateControlDao) {
        this.updateControlDao = updateControlDao;
    }

    @Transactional
    public ArrayList<UpdateControlEntity> getUserTicketsByDate(Integer userId, Date date){
        return updateControlDao.getUserTicketsByDate(userId, date);
    }

    @Transactional
    public ArrayList<UpdateControlEntity> getAllUsersTicketsByDate(Date date){
        return updateControlDao.getAllUsersTicketsByDate(date);
    }

    @Transactional
    public void createUpdateControl(UpdateControlEntity updateControlEntity){
        updateControlDao.createUpdateControl(updateControlEntity);
    }

    @Transactional
    public void deleteUpdateControl(UpdateControlEntity updateControlEntity){
        updateControlDao.deleteUpdateControl(updateControlEntity);
    }

    @Transactional
    public void updateUpdateControl(UpdateControlEntity updateControlEntity){
        updateControlDao.updateUpdateControl(updateControlEntity);
    }


}
