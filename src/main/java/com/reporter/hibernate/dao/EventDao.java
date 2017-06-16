package com.reporter.hibernate.dao;

import com.reporter.hibernate.entities.*;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Repository("EventDAO")
public class EventDao{

    @Autowired
    private SessionFactory sessionFactory;

    public void persist(EventEntity entity) {
        sessionFactory.getCurrentSession().saveOrUpdate(entity);
    }

    public void update(EventEntity entity) throws HibernateException {
        sessionFactory.getCurrentSession().update(entity);
    }

    public EventEntity findById(Integer key) {
        EventEntity event = (EventEntity) sessionFactory.getCurrentSession().get(EventEntity.class, key);
        return event;
    }

    public EventEntity findByDate(Date date) {
        Query query = sessionFactory.getCurrentSession().createQuery("from EventEntity where data =:date ");
        query.setParameter("date", date);
        return (EventEntity) query.uniqueResult();
    }

    public ArrayList<EventEntity> findByTestName(String testName) throws HibernateException {
        Query query = sessionFactory.getCurrentSession().createQuery("from EventEntity where testByTestId.name =:testName ");
        query.setParameter("testName", testName);
        ArrayList<EventEntity> results = (ArrayList<EventEntity>) query.list();
        return results;
    }

    public ArrayList<EventEntity> findByGroupName(String groupName) throws HibernateException {
        Query query = sessionFactory.getCurrentSession().createQuery("from EventEntity where testByTestId.groupByGroupId.name =:groupName");
        query.setParameter("groupName", groupName);
        ArrayList<EventEntity> results = (ArrayList<EventEntity>) query.list();
        return results;
    }

    public ArrayList<EventEntity> findByClassName(String className) throws HibernateException {
        Query query = sessionFactory.getCurrentSession().createQuery("from EventEntity where testByTestId.clazzByClassId.name =:className ");
        query.setParameter("className", className);
        ArrayList<EventEntity> results = (ArrayList<EventEntity>) query.list();
        return results;
    }

    public ArrayList<EventEntity> findBetweenDates(Date startSate, Date endDate) throws HibernateException {
        Query query = sessionFactory.getCurrentSession().createQuery("from EventEntity where data between :startDate and :endDate");
        query.setParameter("startDate", startSate);
        query.setParameter("endDate", endDate);
        ArrayList<EventEntity> results = (ArrayList<EventEntity>) query.list();
        return results;
    }

    public ArrayList<EventEntity> findByTestNameBetweenDates(String testName, Date startDate, Date endDate) throws HibernateException {
        Query query = sessionFactory.getCurrentSession().createQuery("from EventEntity " +
                "where testByTestId.name =:testName and data between :startDate and :endDate");
        query.setParameter("testName", testName);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        ArrayList<EventEntity> results = (ArrayList<EventEntity>) query.list();
        return results;
    }

    public ArrayList<EventEntity> findByMonthEvents(Date date) throws HibernateException {
        Query query = sessionFactory.getCurrentSession().createQuery("from EventEntity " +
                "where MONTH(data) = MONTH(:date) and YEAR(data) = YEAR(:date)");
        query.setParameter("date", date);
        ArrayList<EventEntity> results = (ArrayList<EventEntity>) query.list();
        return results;
    }

    public ArrayList<EventEntity> findByMonthEvents(Date date, String website) throws HibernateException {
        Query query = sessionFactory.getCurrentSession().createQuery("from EventEntity " +
                "where MONTH(data) = MONTH(:date) and YEAR(data) = YEAR(:date) and website =:website");
        query.setParameter("date", date);
        query.setParameter("website", website);
        ArrayList<EventEntity> results = (ArrayList<EventEntity>) query.list();
        return results;
    }

    public ArrayList<EventEntity> findByDayEvents(Date date) throws HibernateException {
        Query query = sessionFactory.getCurrentSession().createQuery("from EventEntity " +
                "where DAY(data) = DAY(:date) and MONTH(data) = MONTH(:date) and YEAR(data) = YEAR(:date)");
        query.setParameter("date", date);
        ArrayList<EventEntity> results = (ArrayList<EventEntity>) query.list();
        return results;
    }

    public ArrayList<EventEntity> findByDayEvents(Date date, String website) throws HibernateException {
        Query query = sessionFactory.getCurrentSession().createQuery("from EventEntity " +
                "where DAY(data) = DAY(:date) and MONTH(data) = MONTH(:date) and YEAR(data) = YEAR(:date) and website =:website");
        query.setParameter("date", date);
        query.setParameter("website", website);
        ArrayList<EventEntity> results = (ArrayList<EventEntity>) query.list();
        return results;
    }


    public ArrayList<EventEntity> findBySyswebBetweenDates(String sysweb, Date startDate, Date endDate) throws HibernateException {
        Query query = sessionFactory.getCurrentSession().createQuery("from EventEntity " +
                "where syswebBySyswebId.name =:sysweb and data between :startDate and :endDate");
        query.setParameter("sysweb", sysweb);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        ArrayList<EventEntity> results = (ArrayList<EventEntity>) query.list();
        return results;
    }

    public ArrayList<EventEntity> findBySelectedDay(String website, String clazzName, String testName, String sysweb, String locale, Date date) throws HibernateException {
        String sqlQuery = "from EventEntity " +
                "where DAY(data) = DAY(:date) and MONTH(data) = MONTH(:date) and YEAR(data) = YEAR(:date) ";
        if (!clazzName.equals("")) {
            sqlQuery = sqlQuery + "and testByTestId.clazzByClassId.name =:clazzName ";
        }
        if (!testName.equals("")) {
            sqlQuery = sqlQuery + "and testByTestId.name =:testName ";
        }
        if (!sysweb.equals("")) {
            sqlQuery = sqlQuery + "and syswebBySyswebId.name =:sysweb ";
        }
        if (!locale.equals("")) {
            sqlQuery = sqlQuery + "and localeByLocaleId.locale =:locale ";
        }
        if (!website.equals("")) {
            sqlQuery = sqlQuery + "and website =:website";
        }

        Query query = sessionFactory.getCurrentSession().createQuery(sqlQuery);
        if (!clazzName.equals("")) {
            query.setParameter("clazzName", clazzName);
        }
        if (!testName.equals("")) {
            query.setParameter("testName", testName);
        }
        if (!sysweb.equals("")) {
            query.setParameter("sysweb", sysweb);
        }
        if (!locale.equals("")) {
            query.setParameter("locale", locale);
        }
        if (!website.equals("")) {
            query.setParameter("website", website);
        }
        query.setParameter("date", date);
        ArrayList<EventEntity> results = (ArrayList<EventEntity>) query.list();
        return results;
    }

    public ArrayList<EventEntity> findBySelected(
            String website, String clazzName, String testName, String sysweb, String locale, Date startDate, Date endDate)
            throws HibernateException {

        String sqlQuery = "from EventEntity " +
                "where data between :startDate and :endDate ";
        if (!clazzName.equals("")) {
            sqlQuery = sqlQuery + "and testByTestId.clazzByClassId.name =:clazzName ";
        }
        if (!testName.equals("")) {
            sqlQuery = sqlQuery + "and testByTestId.name =:testName ";
        }
        if (!sysweb.equals("")) {
            sqlQuery = sqlQuery + "and syswebBySyswebId.name =:sysweb ";
        }
        if (!locale.equals("")) {
            sqlQuery = sqlQuery + "and localeByLocaleId.locale =:locale ";
        }
        if (!website.equals("")) {
            sqlQuery = sqlQuery + "and website =:webSite";
        }

        Query query = sessionFactory.getCurrentSession().createQuery(sqlQuery);
        if (!clazzName.equals("")) {
            query.setParameter("clazzName", clazzName);
        }
        if (!testName.equals("")) {
            query.setParameter("testName", testName);
        }
        if (!sysweb.equals("")) {
            query.setParameter("sysweb", sysweb);
        }
        if (!locale.equals("")) {
            query.setParameter("locale", locale);
        }
        if (!website.equals("")) {
            query.setParameter("webSite", website);
        }
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        ArrayList<EventEntity> results = (ArrayList<EventEntity>) query.list();
        return results;
    }

    public ArrayList<EventEntity> findByLocale(String localeName) throws HibernateException {
        Query query = sessionFactory.getCurrentSession().createQuery("from EventEntity where localeByLocaleId.locale =:localeName ");
        query.setParameter("localeName", localeName);
        ArrayList<EventEntity> results = (ArrayList<EventEntity>) query.list();
        return results;
    }

    public ArrayList<EventEntity> findBySysweb(String sysweb) throws HibernateException {
        Query query = sessionFactory.getCurrentSession().createQuery("from EventEntity where syswebBySyswebId.name =:sysweb");
        query.setParameter("sysweb", sysweb);
        ArrayList<EventEntity> results = (ArrayList<EventEntity>) query.list();
        return results;
    }

    public ArrayList<EventEntity> findByPcName(String pcName) throws HibernateException {
        Query query = sessionFactory.getCurrentSession().createQuery("from EventEntity where pcByPcId.name =:pcName");
        query.setParameter("pcName", pcName);
        Transaction transaction = sessionFactory.getCurrentSession().beginTransaction();
        ArrayList<EventEntity> results = (ArrayList<EventEntity>) query.list();
        transaction.commit();
        return results;
    }

    public ArrayList<EventEntity> findByPcOS(String pcOs) throws HibernateException {
        Query query = sessionFactory.getCurrentSession().createQuery("from EventEntity where pcByPcId.os =:pcOs");
        query.setParameter("pcOs", pcOs);
        ArrayList<EventEntity> results = (ArrayList<EventEntity>) query.list();
        return results;
    }

    public ArrayList<EventEntity> findByBrowser(String browser) throws HibernateException {
        Query query = sessionFactory.getCurrentSession().createQuery("from EventEntity where browserByBrowserId.browser =:browser");
        query.setParameter("browser", browser);
        ArrayList<EventEntity> results = (ArrayList<EventEntity>) query.list();
        return results;
    }

    public ArrayList<EventEntity> findOnlyChecked() throws HibernateException {
        int checkStatus = 1;
        Query query = sessionFactory.getCurrentSession().createQuery("from EventEntity where checked =:checkStatus");
        query.setParameter("checkStatus", checkStatus);
        ArrayList<EventEntity> results = (ArrayList<EventEntity>) query.list();
        return results;
    }

    public ArrayList<EventEntity> findOnlyUnChecked() throws HibernateException {
        int checkStatus = 0;
        Query query = sessionFactory.getCurrentSession().createQuery("from EventEntity where checked =:checkStatus");
        query.setParameter("checkStatus", checkStatus);
        ArrayList<EventEntity> results = (ArrayList<EventEntity>) query.list();
        return results;
    }

    public ClazzEntity findClazzByName(String className) {
        Query query = sessionFactory.getCurrentSession().createQuery("from ClazzEntity where name =:className");
        query.setParameter("className", className);
        ClazzEntity clazz = (ClazzEntity) query.uniqueResult();
        return clazz;
    }

    public HashSet<String> getTestNames() {
        HashSet<String> testNamesList = new HashSet<>();
        Query query = sessionFactory.getCurrentSession().createQuery("from TestEntity ");
        List<TestEntity> testEntities = query.list();
        testEntities.forEach(testEntity -> testNamesList.add(testEntity.getName()));
        return testNamesList;
    }

    public ClazzEntity getClassByClassName(String className) {
        Query query = sessionFactory.getCurrentSession().createQuery("from ClazzEntity where name =:className");
        query.setParameter("className", className);
        ClazzEntity clazz = (ClazzEntity) query.uniqueResult();
        System.out.println(clazz.getName());
        return clazz;
    }

    public HashSet<String> getTestNamesByClazzName(ClazzEntity clazzEntity) {
        /*Query query = getCurrentSession().createQuery("from ClazzEntity where name =:className");
        query.setParameter("className", className);
        ClazzEntity clazzEntity = (ClazzEntity) query.uniqueResult();
        System.out.println(clazzEntity);*/
        Query query1 = sessionFactory.getCurrentSession().createQuery("from TestEntity where clazzByClassId =:clazzEntity");
        query1.setParameter("clazzEntity", clazzEntity);
        ArrayList<TestEntity> testEntities = (ArrayList<TestEntity>) query1.list();
        System.out.println("result size " + testEntities.size());
        HashSet<String> result = new HashSet<>();
        testEntities.forEach(testEntity -> result.add(testEntity.getName()));
        return result;
    }

    public HashSet<String> getClazzNames() {
        HashSet<String> clazzNamesList = new HashSet<>();
        Query query = sessionFactory.getCurrentSession().createQuery("from ClazzEntity ");
        List<ClazzEntity> classEntities = query.list();
        classEntities.forEach(clazzEntity -> clazzNamesList.add(clazzEntity.getName()));
        return clazzNamesList;
    }

    public HashSet<String> getLocaleNames() {
        HashSet<String> localeNamesList = new HashSet<>();
        Query query = sessionFactory.getCurrentSession().createQuery("from LocaleEntity ");
        List<LocaleEntity> localeEntities = query.list();
        localeEntities.forEach(clazzEntity -> localeNamesList.add(clazzEntity.getLocale()));
        return localeNamesList;
    }

    public HashSet<String> getSyswebNames() {
        HashSet<String> syswebNamesList = new HashSet<>();
        Query query = sessionFactory.getCurrentSession().createQuery("from SyswebEntity ");
        List<SyswebEntity> syswebEntities = query.list();
        syswebEntities.forEach(syswebEntity -> syswebNamesList.add(syswebEntity.getName()));
        return syswebNamesList;
    }

}
