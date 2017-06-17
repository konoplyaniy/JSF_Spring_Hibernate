package com.reporter.hibernate.dao;

import com.reporter.hibernate.entities.TestEntity;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository("TestDAO")
public class TestDao{

    @Autowired
    private SessionFactory sessionFactory;

    public void persist(TestEntity entity) {
        sessionFactory.getCurrentSession().saveOrUpdate(entity);
    }

    public Boolean exists(String clazzName, String testName) throws HibernateException {
        ClazzDao dao = new ClazzDao();
        if (dao.exists(clazzName)) {
            int clazzId = dao.findByName(clazzName).getClass_Id();
            Query query = sessionFactory.getCurrentSession().createQuery("from TestEntity where clazzByClassId.classId =:clazzId and name =:testName");
            query.setParameter("testName", testName);
            query.setParameter("clazzId", clazzId);
            boolean result = (query.uniqueResult() != null);
            return result;
        } else return false;
    }

    public TestEntity findById(Integer id) throws HibernateException {
        TestEntity testEntity = (TestEntity) sessionFactory.getCurrentSession().get(TestEntity.class, id);
        return testEntity;
    }

    public TestEntity findByIdValue(String IdValue) throws HibernateException {
        Query query = sessionFactory.getCurrentSession().createQuery("from TestEntity where id_value =:IdValue");
        query.setParameter("IdValue", IdValue);
        TestEntity test = (TestEntity) query.uniqueResult();
        return test;
    }

    public ArrayList<TestEntity> findByTestName(String testName) throws HibernateException {
        Query query = sessionFactory.getCurrentSession().createQuery("from TestEntity where name =:testName");
        query.setParameter("testName", testName);
        ArrayList<TestEntity> tests = (ArrayList<TestEntity>) query.list();
        return tests;
    }

    public TestEntity findByTestNameClassName(String testName, String clazzName) throws HibernateException {
        Query query = sessionFactory.getCurrentSession().createQuery("from TestEntity where name =:test_name and clazzByClassId.name =:clazzName");
        query.setParameter("testName", testName);
        query.setParameter("clazzName", clazzName);
        TestEntity tests = (TestEntity) query.uniqueResult();
        return tests;
    }

    public ArrayList<TestEntity> findByTestGroupId(int groupId) throws HibernateException {
        Query query = sessionFactory.getCurrentSession().createQuery("from TestEntity where groupByGroupId.group_id =:groupId");
        query.setParameter("groupId", groupId);
        ArrayList<TestEntity> tests = (ArrayList<TestEntity>) query.list();
        return tests;
    }

    public ArrayList<TestEntity> findByTestClassId(int classId) throws HibernateException {
        Query query = sessionFactory.getCurrentSession().createQuery("from TestEntity where clazzByClassId.class_id =:classId");
        query.setParameter("classId", classId);
        ArrayList<TestEntity> tests = (ArrayList<TestEntity>) query.list();
        return tests;
    }


}
