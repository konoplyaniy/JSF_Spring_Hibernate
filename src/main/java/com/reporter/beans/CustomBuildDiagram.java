package com.reporter.beans;

import com.reporter.hibernate.entities.EventEntity;
import com.reporter.hibernate.service.EventService;
import org.hibernate.HibernateException;
import org.primefaces.model.chart.*;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@ManagedBean(name = "custom_diagram")
@SessionScoped
public class CustomBuildDiagram implements Serializable {

    /*for custom build*/
    private LineChartModel lineChartModelCustom;
    private BarChartModel modelByLocaleCustom;
    private BarChartModel modelBySyswebCustom;
    private String sysweb = "";
    private HashSet<String> syswebs;
    private String testName = "";
    private String clazzName = "";
    private HashSet<String> clazzNames;
    private HashSet<String> testNames;
    private String locale = "";
    private HashSet<String> locales;
    private Date startDate;
    private Date endDate;

    private String website = "";
    private ArrayList<String> websites;
    private boolean clickedBuild = false;
    private EventService service;
    private boolean isDataLoad = false;

    private void resetVariables(){
        sysweb = "";
        testName = "";
        clazzName = "";
        locale = "";
    }

    @PostConstruct
    public void init() {
        service = new EventService();
        initDropdownsData();
    }

    //  INIT DROPDOWNS LIST
    public void initDropdownsData() {
        resetVariables();
        EventService service = new EventService();
        /*setTestNames(service.getTestNames());*/
        setLocales(service.getLocaleNames());
        setSyswebs(service.getSyswebNames());
        setClazzNames(service.getClazzNames());
        setWebsites(getWebsites());
    }

    public void clickBuildButton() {
        if (getStartDate() != null && getEndDate() != null &&
                getTestName() != null && getSysweb() != null
                && getLocale() != null) {
            if (getStartDate().before(getEndDate())) {
                lineChartModelCustom = initLineChartModel(true);
                modelByLocaleCustom = initBarChartModel(true, true, "", "Locales");
                modelBySyswebCustom = initBarChartModel(false, true, "", "Syswebs");
                setDataLoad(true);
                setClickedBuild(true);
                /*clickedBuild = true;*/
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Error", " Start Date must be earlier then End Date"));
            }
        }
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
        setTestNames(getTestNamesListBySelectedClassName());
    }

    public HashSet<String> getTestNamesListBySelectedClassName() throws HibernateException {
        if (getClazzName() != null && !getClazzName().equals("")) {
            return service.getTestNamesByClazzName(getClazzName());
        } else
            return service.getTestNames();
    }

    /**
     * @param isBarChartByLocale if set true will be generated diagram by locale, else by syswebs
     * @param addToLabelString   this label will be displayed after key (like "in locale"  or "SYSWEB4.UK.SYRAHOST.COM sysweb"), could be ""
     * @return BarChart diagram with series
     */
    public BarChartModel initBarChartModel(boolean isBarChartByLocale, boolean isCustomDiagram, String addToLabelString, String label) {
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        BarChartModel barChartModel = new BarChartModel();
        HashMap<String, Integer> map = new HashMap<>();
        ArrayList<EventEntity> events;

        /* init events list*/
        events = getEventsCustom();

        /*init map for series*/
        events.forEach(event -> {
            String keyForSearch;
            if (isBarChartByLocale) {
                keyForSearch = event.getLocaleByLocaleId().getLocale();
            } else {
                keyForSearch = event.getSyswebBySyswebId().getName();
            }
            if (!map.containsKey(keyForSearch)) {
                map.put(keyForSearch, 1);
            } else {
                int count = map.get(keyForSearch) + 1;
                map.put(keyForSearch, count);
            }
        });

        /*init series*/
        ChartSeries chartSeries;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            chartSeries = new ChartSeries();
            chartSeries.setLabel(entry.getKey() + " " + addToLabelString);
            chartSeries.set(entry.getKey(), entry.getValue());
            barChartModel.addSeries(chartSeries);
        }

        /*init model settings*/
        Axis xAxis = barChartModel.getAxis(AxisType.X);
        xAxis.setLabel(label);
        Axis yAxis = barChartModel.getAxis(AxisType.Y);
        yAxis.setLabel("Count");
        barChartModel.setLegendPosition("e");
        barChartModel.setLegendPlacement(LegendPlacement.OUTSIDEGRID);


        barChartModel.setTitle("Failed tests for period: " + formatter.format(getStartDate()) + " - " + formatter.format(getEndDate()));
        barChartModel.setAnimate(true);

        return barChartModel;
    }

    /**
     * @param isCustomDiagram if set true will be generated diagram of failed tests count per day (for current month)
     *                        else by selected by user dates
     * @return LineChart diagram with series
     */

    public LineChartModel initLineChartModel(boolean isCustomDiagram) {
        // events per Day
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ");
        Date startDate;
        Date endDate;

        /*get selected (by user) start and end date if this is not current month diagram */
        startDate = getStartDate();
        endDate = getEndDate();

        LineChartSeries series = new LineChartSeries();
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);

        /*per day add to series date and count of failed tests*/
        for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
            /*System.out.println("clazz name " + getClazzName() + " test name " + getTestName() + " sysweb " + getSysweb() + " locale " + getLocale());*/
            ArrayList<EventEntity> eventList = service.findBySelectedDay(getWebsite(), getClazzName(), getTestName(), getSysweb(), getLocale(), date);
            System.out.println("Diagram view class. " + date + " event count = " + eventList.size());
            series.set(formatter.format(date), eventList.size());
        }

        /*init model settings*/
        LineChartModel lineChartModel = new LineChartModel();
        lineChartModel.addSeries(series);
        lineChartModel.setTitle("Failed tests for period: " + formatter.format(startDate) + " - " + formatter.format(endDate));
        lineChartModel.getAxis(AxisType.Y).setLabel("Count");
        DateAxis axis = new DateAxis("Dates");
        axis.setTickAngle(-50);
        axis.setTickFormat("%b %#d, %y");
        lineChartModel.getAxes().put(AxisType.X, axis);

        return lineChartModel;
    }

    private ArrayList<EventEntity> getEventsCustom() {
        ArrayList<EventEntity> resultEventsList = service.findBySelected(getWebsite(), getClazzName(), getTestName(), getSysweb(), getLocale(), getStartDate(), getEndDate());
        return resultEventsList;
    }

    public BarChartModel getModelBySyswebCustom() {
        return modelBySyswebCustom;
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

    public HashSet<String> getSyswebs() {
        return syswebs;
    }

    public void setSyswebs(HashSet<String> syswebs) {
        this.syswebs = syswebs;
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

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public HashSet<String> getClazzNames() {
        return clazzNames;
    }

    public void setClazzNames(HashSet<String> clazzNames) {
        this.clazzNames = clazzNames;
    }

    public BarChartModel getModelByLocaleCustom() {
        return modelByLocaleCustom;
    }

    public LineChartModel getLineChartModelCustom() {
        return lineChartModelCustom;
    }

    public String getSysweb() {
        return sysweb;
    }

    public void setSysweb(String sysweb) {
        this.sysweb = sysweb;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public boolean isClickedBuild() {
        return clickedBuild;
    }

    public void setClickedBuild(boolean clickedBuild) {
        this.clickedBuild = clickedBuild;
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

    public boolean isDataLoad() {
        return isDataLoad;
    }

    public void setDataLoad(boolean dataLoad) {
        isDataLoad = dataLoad;
    }

    public void setWebsites(ArrayList<String> websites) {
        this.websites = websites;
    }
}
