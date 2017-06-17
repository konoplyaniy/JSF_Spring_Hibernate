package com.reporter.hibernate.service;

import com.reporter.hibernate.dao.TestCaseDao;
import com.reporter.hibernate.entities.TestcaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service("TestCaseService")
public class TestCaseService {

    @Autowired
    private static TestCaseDao testCaseDao;

    public static TestCaseDao getTestCaseDao() {
        return testCaseDao;
    }

    public static void setTestCaseDao(TestCaseDao testCaseDao) {
        TestCaseService.testCaseDao = testCaseDao;
    }

    @Transactional
    public boolean exist(String clazzName, String testName) {
        return testCaseDao.exists(clazzName, testName);
    }

    @Transactional
    public TestcaseEntity findByClassNameTestName(String className, String testName) {
        return testCaseDao.findByClassNameTestName(className, testName);
    }

    @Transactional
    public ArrayList<TestcaseEntity> findByClassName(String className) {
        return testCaseDao.findByClassName(className);
    }

    @Transactional
    public ArrayList<TestcaseEntity> findAll() {
        return testCaseDao.findAll();
    }
}
