package com.dao;


import org.hibernate.Query;
import com.entity.OvertimeEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by geser on 16.06.17.
 */
@Repository("OvertimeDAO")
public class OvertimeDao {
    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /*Return month overtimes*/
    public ArrayList<OvertimeEntity> getUserOvertimesByDate(Integer user_id, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Query query = sessionFactory.getCurrentSession()
                .createQuery(
                        "from OvertimeEntity where user_id=:user_id and MONTH(date)=MONTH(:date) and YEAR(date)=YEAR(:date)");
        query.setParameter("user_id", user_id);
        query.setParameter("date", date);
        ArrayList<OvertimeEntity> overtimeList = (ArrayList<OvertimeEntity>) query.list();
        return overtimeList;
    }

    /*Return year overtimes*/
    public ArrayList<OvertimeEntity> getOvertimesByMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Query query = sessionFactory.getCurrentSession()
                .createQuery("from OvertimeEntity where MONTH(date)=MONTH(:date) and YEAR(date)=YEAR(:date)");
        query.setParameter("date", date);
        ArrayList<OvertimeEntity> overtimeList = (ArrayList<OvertimeEntity>) query.list();
        return overtimeList;
    }

    public void create(OvertimeEntity overtimeEntity) {
        sessionFactory.getCurrentSession().persist(overtimeEntity);
    }

    public void delete(OvertimeEntity overtimeEntity) {
        sessionFactory.getCurrentSession().delete(overtimeEntity);
    }

    public void update(OvertimeEntity overtimeEntity) {
        sessionFactory.getCurrentSession().update(overtimeEntity);
    }
}
