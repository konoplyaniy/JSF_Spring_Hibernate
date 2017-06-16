package com.reporter.hibernate.service;

import com.reporter.hibernate.dao.TestDao;
import com.reporter.hibernate.entities.TestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service("TestService")
public class TestService {

    @Autowired
    private static TestDao testDao;

    public static TestDao getTestDao() {
        return testDao;
    }

    public static void setTestDao(TestDao testDao) {
        TestService.testDao = testDao;
    }

    @Transactional
    public void persist(TestEntity entity) {
        testDao.persist(entity);
    }

    @Transactional
    public boolean exist(String clazzName, String testName) {
        return testDao.exists(clazzName, testName);
    }

    @Transactional
    public TestEntity findById(Integer id) {
        return testDao.findById(id);
    }

    @Transactional
    public TestEntity findByIdValue(String idValue) {
        return testDao.findByIdValue(idValue);
    }

    @Transactional
    public ArrayList<TestEntity> findByTestName(String testName) {
        return testDao.findByTestName(testName);
    }

    @Transactional
    public TestEntity findByTestNameClazzName(String testName, String clazzName) {
        return testDao.findByTestNameClassName(testName, clazzName);
    }

    @Transactional
    public ArrayList<TestEntity> findByTestGroupId(int id) {
        return testDao.findByTestGroupId(id);
    }

    @Transactional
    public ArrayList<TestEntity> findByTestClassId(int id) {
        return testDao.findByTestClassId(id);
    }

}
