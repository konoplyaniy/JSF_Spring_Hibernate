package com.dao;


import com.entity.ClazzEntity;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("ClazzDAO")
public class ClazzDao{

    @Autowired
    private SessionFactory sessionFactory;

    public Boolean exists(String className) throws HibernateException {
        Query query = sessionFactory.getCurrentSession().createQuery("from ClazzEntity where name = :className");
        query.setString("className", className);
        boolean result = (query.uniqueResult() != null);
        return result;
    }

    public void persist(ClazzEntity clazz) throws HibernateException {
        if (!exists(clazz.getName())){
            sessionFactory.getCurrentSession().save(clazz);
        }
    }

    public ClazzEntity findByName(String className) {
        Query query = sessionFactory.getCurrentSession().createQuery("from ClazzEntity where name =:className");
        query.setParameter("className", className);
        ClazzEntity clazz = (ClazzEntity) query.uniqueResult();
        return clazz;
    }
}
