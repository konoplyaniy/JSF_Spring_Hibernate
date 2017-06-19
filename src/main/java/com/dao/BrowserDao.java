package com.dao;


import com.entity.BrowserEntity;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("BrowserDAO")
public class BrowserDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Boolean exists(String browser) throws HibernateException {
        Query query = sessionFactory.getCurrentSession().createQuery("from BrowserEntity where browser = :browser");
        query.setString("browser", browser);
        boolean result = (query.uniqueResult() != null);
        return result;
    }

    public void persist(BrowserEntity entity) throws HibernateException {
        if (!exists(entity.getBrowser())){
            sessionFactory.getCurrentSession().save(entity);
        }
    }

    public BrowserEntity findByName(String browser) throws HibernateException {
        Query query = sessionFactory.getCurrentSession().createQuery("from BrowserEntity where browser =:browser");
        query.setParameter("browser", browser);
        BrowserEntity brows = (BrowserEntity) query.uniqueResult();
        return brows;
    }
}
