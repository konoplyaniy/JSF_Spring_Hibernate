package com.dao;


import com.entity.PcEntity;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("PcDAO")
public class PcDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Boolean exists(String pcName) throws HibernateException {
        Query query = sessionFactory.getCurrentSession().createQuery("from PcEntity where name = :pcName");
        query.setString("pcName", pcName);
        boolean result = (query.uniqueResult() != null);
        return result;
    }

    public void persist(PcEntity pcDetails) throws HibernateException {
        if (!exists(pcDetails.getName())) {
            sessionFactory.getCurrentSession().save(pcDetails);
        }
    }

    public PcEntity findById(Integer id) throws HibernateException {
        PcEntity pc = (PcEntity) sessionFactory.getCurrentSession().get(PcEntity.class, id);
        return pc;
    }

    public PcEntity findByPcName(String pcName) throws HibernateException {
        Query query = sessionFactory.getCurrentSession().createQuery("from PcEntity where name =:pcName");
        query.setParameter("pcName", pcName);
        PcEntity pc = (PcEntity) query.uniqueResult();
        return pc;
    }
}
