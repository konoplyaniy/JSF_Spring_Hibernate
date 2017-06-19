package com.dao;


import com.entity.LocaleEntity;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("LocaleDAO")
public class LocaleDao{
    @Autowired
    private SessionFactory sessionFactory;

    public Boolean exists(String locale) throws HibernateException {
        Query query = sessionFactory.getCurrentSession().createQuery("from LocaleEntity where locale = :locale");
        query.setString("locale", locale);
        boolean result = (query.uniqueResult() != null);
        return result;
    }

    public void persist(LocaleEntity locale) throws HibernateException {
        if (!exists(locale.getLocale())){
            sessionFactory.getCurrentSession().save(locale);
        }
    }

    public LocaleEntity findById(Integer id) throws HibernateException {
        LocaleEntity locale = (LocaleEntity) sessionFactory.getCurrentSession().get(LocaleEntity.class, id);
        return locale;
    }

    public LocaleEntity findByLocale(String locale) throws HibernateException {
        Query query = sessionFactory.getCurrentSession().createQuery("from LocaleEntity where locale =:locale");
        query.setParameter("locale", locale);
        LocaleEntity local = (LocaleEntity) query.uniqueResult();
        return local;
    }
}
