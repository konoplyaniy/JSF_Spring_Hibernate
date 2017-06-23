package com.dao;

import com.entity.TestcaseEntity;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository("testCaseDAO")
public class TestCaseDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Boolean exists(String clazzName, String testName) throws HibernateException {
        Query query = sessionFactory.getCurrentSession().createQuery("from TestcaseEntity where class_name =:clazzName and test_name =:testName");
        query.setParameter("testName", testName);
        query.setParameter("clazzName", clazzName);
        boolean result = (query.uniqueResult() != null);
        return result;
    }

    public boolean persist(TestcaseEntity entity) throws HibernateException {
        if (!exists(entity.getClass_name(), entity.getTest_name())) {
            sessionFactory.getCurrentSession().save(entity);
            return true;
        }
        return false;
    }

    public boolean update(TestcaseEntity entity) {
        try {
            sessionFactory.getCurrentSession().update(entity);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public TestcaseEntity findByClassNameTestName(String clazzName, String testName) {
        Query query = sessionFactory.getCurrentSession().createQuery("from TestcaseEntity where class_name =:clazzName and test_name =:testName");
        query.setParameter("testName", testName);
        query.setParameter("clazzName", clazzName);
        return (TestcaseEntity) query.uniqueResult();
    }

    public ArrayList<TestcaseEntity> findByClassName(String clazzName) {
        Query query = sessionFactory.getCurrentSession().createQuery("from TestcaseEntity where class_name =:clazzName");
        query.setParameter("clazzName", clazzName);
        return (ArrayList<TestcaseEntity>) query.list();
    }

    @SuppressWarnings("unchecked")
    public ArrayList<TestcaseEntity> findAll() {
        ArrayList<TestcaseEntity> locales = (ArrayList<TestcaseEntity>) sessionFactory.getCurrentSession().createQuery("from TestcaseEntity ").list();
        return locales;
    }
}
