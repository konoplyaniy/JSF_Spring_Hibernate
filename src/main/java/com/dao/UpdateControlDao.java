package com.dao;

import com.entity.UpdateControlEntity;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by geser on 16.06.17.
 */
@Repository("updateControlDao")
public class UpdateControlDao {
    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public ArrayList<UpdateControlEntity> getUserUpdateControlByDate(Integer user_id, Date date) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery(
                        "from UpdateControlEntity where user_id=:user_id and month(date)=month(:date) and year(date)=year(:date)");
        query.setParameter("user_id", user_id);
        query.setParameter("date", date);
        ArrayList<UpdateControlEntity> updateList = (ArrayList<UpdateControlEntity>) query.list();
        return updateList;
    }

    public ArrayList<UpdateControlEntity> getAllUsersUpdateControlByDate(Date date) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery(
                        "from UpdateControlEntity where month(date)=month(:date) and year(date)=year(:date)");
        query.setParameter("date", date);
        ArrayList<UpdateControlEntity> updateList = (ArrayList<UpdateControlEntity>) query.list();
        return updateList;
    }

    /*public ArrayList<UpdateControlEntity> getAllUsersUpdateControlByMonth(Date date){
        Query query = sessionFactory.getCurrentSession().createQuery(
                "from UpdateControlEntity where ");
        ArrayList<UpdateControlEntity>updateList = (ArrayList<UpdateControlEntity>) sessionFactory.getCurrentSession().createCriteria(UpdateControlEntity.class).list();
        return updateList;
    }*/

    public void createUpdateControl(UpdateControlEntity updateControlEntity) {
        sessionFactory.getCurrentSession().persist(updateControlEntity);
    }

    public void deleteUpdateControl(UpdateControlEntity updateControlEntity) {
        sessionFactory.getCurrentSession().delete(updateControlEntity);
    }

    public void updateUpdateControl(UpdateControlEntity updateControlEntity) {
        sessionFactory.getCurrentSession().update(updateControlEntity);
    }
}
