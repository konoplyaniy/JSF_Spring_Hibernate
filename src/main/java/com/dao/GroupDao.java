package com.dao;

import com.entity.GroupEntity;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("GroupDAO")
public class GroupDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Boolean exists(String groupName) throws HibernateException {
        Query query = sessionFactory.getCurrentSession().createQuery("from GroupEntity where name = :groupName");
        query.setString("groupName", groupName);
        return (query.uniqueResult() != null);
    }

    public void persist(GroupEntity group) throws HibernateException {
        if (!exists(group.getName())){
            sessionFactory.getCurrentSession().save(group);
        }
    }

    public GroupEntity findById(Integer id) throws HibernateException {
        GroupEntity group = (GroupEntity) sessionFactory.getCurrentSession().get(GroupEntity.class, id);
        return group;
    }

    public GroupEntity findByName(String groupName) throws HibernateException {
        Query query = sessionFactory.getCurrentSession().createQuery("from GroupEntity where name =:groupName");
        query.setParameter("groupName", groupName);
        GroupEntity group = (GroupEntity) query.uniqueResult();
        return group;
    }
}
