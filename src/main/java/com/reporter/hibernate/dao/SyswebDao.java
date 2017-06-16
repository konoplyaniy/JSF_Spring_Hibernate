package com.reporter.hibernate.dao;


import com.reporter.hibernate.entities.SyswebEntity;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("SyswebDao")
public class SyswebDao{

    @Autowired
    private SessionFactory sessionFactory;

    public Boolean exists(String sysweb) throws HibernateException {
        Query query = sessionFactory.getCurrentSession().createQuery("from SyswebEntity where name = :sysweb");
        query.setString("sysweb", sysweb);
        boolean result = (query.uniqueResult() != null);
        return result;
    }

    public void persist(SyswebEntity sysweb) throws HibernateException {
        if (!exists(sysweb.getName())){
            sessionFactory.getCurrentSession().save(sysweb);
        }
    }

    public void update(SyswebEntity entity) throws HibernateException {
        sessionFactory.getCurrentSession().update(entity);
    }

    public SyswebEntity findById(Integer id) throws HibernateException {
        SyswebEntity sysweb = (SyswebEntity) sessionFactory.getCurrentSession().get(SyswebEntity.class, id);
        return sysweb;
    }

    public SyswebEntity findByName(String syswebUrl) throws HibernateException {
        Query query = sessionFactory.getCurrentSession().createQuery("from SyswebEntity where name =:syswebUrl");
        query.setParameter("syswebUrl", syswebUrl);
        SyswebEntity sysweb = (SyswebEntity) query.uniqueResult();
        return sysweb;
    }

}
