package com.reporter.beans;

import com.reporter.hibernate.entities.EventEntity;
import com.reporter.hibernate.service.EventService;
import org.hibernate.HibernateException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.TimeZone;

@ManagedBean
@SessionScoped
public class SearchView implements Serializable {
    private Date startDate;
    private Date endDate;
    private String testName = "";
    private String sysweb = "";
    private String locale = "";
    private String clazzName = "";
    private String website = "";
    private boolean visibleResultsForm = false;
    private HashSet<String> clazzNames;
    private HashSet<String> testNames;
    private HashSet<String> locales;
    private HashSet<String> syswebs;
    private ArrayList<String> websites;
    private ArrayList<EventEntity> resultEventsList;
    private EventService service;

    @PostConstruct
    public void initDropDowns() {
        service = new EventService();
        initDropdownsData();
    }

    public void onWebSiteChange() {
        HashSet<String> allSyswebsList = service.getSyswebNames();
        if (getWebsite().equals("austdomains.com.au")) {
            HashSet<String> austSyswebs = new HashSet<>();
            allSyswebsList.forEach(syswebName -> {
                if (!syswebName.toLowerCase().contains(".uk.")) {
                    austSyswebs.add(syswebName);
                }
            });
            setSyswebs(austSyswebs);
        } else setSyswebs(allSyswebsList);
    }

    public void onClassNameChange() {
        System.out.println("onClassNameChange");
        System.out.println(getClazzName());
        setTestNames(getTestNamesListBySelectedClassName());
    }

    public HashSet<String> getTestNamesListBySelectedClassName() throws HibernateException {
        if (getClazzName() != null && !getClazzName().equals("")) {
            return service.getTestNamesByClazzName(getClazzName());
        } else
            return service.getTestNames();
    }

    private void initDropdownsData() {
        setLocales(service.getLocaleNames());
        setSyswebs(service.getSyswebNames());
        setClazzNames(service.getClazzNames());
        setWebsites(getWebsites());
    }

    public void clickSearchButton() {
        if (getStartDate() != null && getEndDate() != null && getStartDate().before(getEndDate())) {
            setVisibleResultsForm(true);
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Error", " Start Date must be earlier then End Date"));
        }
    }

    public ArrayList<EventEntity> getResultEventsList() {
        resultEventsList = service.findBySelected(getWebsite(), getClazzName(), getTestName(), getSysweb(), getLocale(), getStartDate(), getEndDate());
        return resultEventsList;
    }

    public void setResultEventsList(ArrayList<EventEntity> resultEventsList) {
        this.resultEventsList = resultEventsList;
    }

    public boolean isVisibleResultsForm() {
        return visibleResultsForm;
    }

    public void setVisibleResultsForm(boolean visibleResultsForm) {
        this.visibleResultsForm = visibleResultsForm;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public Date getStartDate() {
        if (startDate != null) {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatter.setTimeZone(TimeZone.getDefault());
            formatter.format(startDate);
        }
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        if (endDate != null) {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatter.setTimeZone(TimeZone.getDefault());
            formatter.format(endDate);
        }
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getSysweb() {
        return sysweb;
    }

    public void setSysweb(String sysweb) {
        this.sysweb = sysweb;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public HashSet<String> getClazzNames() {
        return clazzNames;
    }

    public void setClazzNames(HashSet<String> clazzNames) {
        this.clazzNames = clazzNames;
    }

    public HashSet<String> getTestNames() {
        return testNames;
    }

    public void setTestNames(HashSet<String> testNames) {
        this.testNames = testNames;
    }

    public HashSet<String> getLocales() {
        return locales;
    }

    public void setLocales(HashSet<String> locales) {
        this.locales = locales;
    }

    public HashSet<String> getSyswebs() {
        return syswebs;
    }

    public void setSyswebs(HashSet<String> syswebs) {
        this.syswebs = syswebs;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public ArrayList<String> getWebsites() {
        ArrayList<String> websites = new ArrayList<>();
        websites.add("crazydomains");
        websites.add("austdomains.com.au");
        websites.add("general");
        return websites;
    }

    public void setWebsites(ArrayList<String> websites) {
        this.websites = websites;
    }
}
