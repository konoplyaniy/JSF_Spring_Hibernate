package com.service;


import com.dao.EventDao;
import com.entity.ClazzEntity;
import com.entity.EventEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service("eventService")
public class EventService {
    @Autowired
    private static EventDao eventDao;

    public static EventDao getEventDao() {
        return eventDao;
    }

    public static void setEventDao(EventDao eventDao) {
        EventService.eventDao = eventDao;
    }

    @Transactional
    public void persist(EventEntity entity) {
        System.out.println("try to add event");
        eventDao.persist(entity);
        System.out.println("success");
    }

    @Transactional
    public void update(EventEntity eventEntity) {
        eventDao.update(eventEntity);
    }

    @Transactional
    public EventEntity findbyId(int id) {
        return eventDao.findById(id);
    }

    @Transactional
    public ArrayList<EventEntity> findByTestName(String testName) {
        return eventDao.findByTestName(testName);
    }

    @Transactional
    public ArrayList<EventEntity> findByGroupName(String groupName) {
        return eventDao.findByGroupName(groupName);
    }

    @Transactional
    public ArrayList<EventEntity> findByClassName(String className) {
        return eventDao.findByClassName(className);
    }

    @Transactional
    public ArrayList<EventEntity> findByBrowser(String browser) {
        return eventDao.findByBrowser(browser);
    }

    @Transactional
    public ArrayList<EventEntity> findByPcOS(String pcOs) {
        return eventDao.findByPcOS(pcOs);
    }

    @Transactional
    public ArrayList<EventEntity> findByPcName(String pcName) {
        return eventDao.findByPcName(pcName);
    }

    @Transactional
    public ArrayList<EventEntity> findBySysweb(String sysweb) {
        return eventDao.findBySysweb(sysweb);
    }

    @Transactional
    public ArrayList<EventEntity> findByLocale(String locale) {
        return eventDao.findByLocale(locale);
    }

    @Transactional
    public ArrayList<EventEntity> findBetweenDate(Date startDate, Date endDate) {
        return eventDao.findBetweenDates(startDate, endDate);
    }

    @Transactional
    public ArrayList<EventEntity> findByCurrentMonthEvents() {
        Date date = new Date();
        return eventDao.findByMonthEvents(date);
    }

    @Transactional
    public ArrayList<EventEntity> findByMonthEvents(Date date) {
        return eventDao.findByMonthEvents(date);
    }

    @Transactional
    public ArrayList<EventEntity> findByMonthEvents(Date date, String website) {
        return eventDao.findByMonthEvents(date, website);
    }

    @Transactional
    public ArrayList<EventEntity> findByDayEvents(Date date) {
        return eventDao.findByDayEvents(date);
    }

    @Transactional
    public ArrayList<EventEntity> findByDayEvents(Date date, String website) {
        return eventDao.findByDayEvents(date, website);
    }

    @Transactional
    public ArrayList<EventEntity> findByCurrentDayEvents() {
        Date date = new Date();
        return eventDao.findByDayEvents(date);
    }

    @Transactional
    public ArrayList<EventEntity> findBySelected(String website, String clazzName, String testName, String sysweb, String locale, Date startDate, Date endDate) {
        return eventDao.findBySelected(website, clazzName, testName, sysweb, locale, startDate, endDate);
    }

    @Transactional
    public ArrayList<EventEntity> findBySelectedDay(String website, String clazzName, String testName, String sysweb, String locale, Date date) {
        return eventDao.findBySelectedDay(website, clazzName, testName, sysweb, locale, date);
    }

    @Transactional
    public List<EventEntity> findAllChecked() {
        return eventDao.findOnlyChecked();
    }

    @Transactional
    public List<EventEntity> findAllUnChecked() {
        return eventDao.findOnlyUnChecked();
    }

    @Transactional
    public HashSet<String> getTestNames() {
        HashSet<String> result = eventDao.getTestNames();
        return result;
    }

    @Transactional
    public ClazzEntity getClassByClassName(String className) {
        return eventDao.getClassByClassName(className);
    }

    @Transactional
    public HashSet<String> getTestNamesByClazzName(String className) {
        ClazzEntity clazzEntity = eventDao.getClassByClassName(className);
        System.out.println(clazzEntity);
        HashSet<String> result = eventDao.getTestNamesByClazzName(clazzEntity);
        System.out.println(result);
        return result;
    }

    @Transactional
    public HashSet<String> getClazzNames() {
        HashSet<String> result = eventDao.getClazzNames();
        return result;
    }

    @Transactional
    public HashSet<String> getLocaleNames() {
        HashSet<String> result = eventDao.getLocaleNames();
        return result;
    }

    @Transactional
    public HashSet<String> getSyswebNames() {
        HashSet<String> result = eventDao.getSyswebNames();
        return result;
    }

    @Transactional
    public ClazzEntity findClazzByName(String clazzName) {
        ClazzEntity result = eventDao.findClazzByName(clazzName);
        return result;
    }

}
