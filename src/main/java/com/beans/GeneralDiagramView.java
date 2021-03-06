package com.beans;

import com.entity.EventEntity;
import com.service.EventService;
import org.primefaces.model.chart.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@ManagedBean
@SessionScoped
public class GeneralDiagramView implements Serializable {
    private LineChartModel modelPerDate;
    private BarChartModel modelByLocale;
    private BarChartModel modelBySysweb;


    @ManagedProperty(value = "#{eventService}")
    private EventService eventService;

    public EventService getEventService() {
        return eventService;
    }

    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    public void init() {
        modelPerDate = initLineChartModel();
        modelByLocale = initBarChartModel(true, "Locales");
        modelBySysweb = initBarChartModel(false, "Syswebs");
    }

    /**
     * @param isBarChartByLocale if set true will be generated diagram by locale, else by syswebs
     * @return BarChart diagram with series
     */
    public BarChartModel initBarChartModel(boolean isBarChartByLocale, String label) {
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        BarChartModel barChartModel = new BarChartModel();
        HashMap<String, Integer> map = new HashMap<>();
        ArrayList<EventEntity> events;

        /* init events list*/
        events = getEvents();


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
            chartSeries.setLabel(entry.getKey());
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

        barChartModel.setTitle("Failed tests count");


        return barChartModel;
    }

    /**
     * @return LineChart diagram with series
     */
    public LineChartModel initLineChartModel() {
        // events per Day
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ");
        Date startDate;
        Date endDate;

            /*here get start date - first day in month*/
        Calendar calendar_start = Calendar.getInstance();
        calendar_start.set(Calendar.DAY_OF_MONTH, calendar_start.getActualMinimum(Calendar.DAY_OF_MONTH));
        startDate = calendar_start.getTime();
        endDate = new Date();

        LineChartSeries series = new LineChartSeries();
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);

            /*System.out.println("default diagram");*/
        for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
            ArrayList<EventEntity> eventList = eventService.findByDayEvents(date, "general");
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

    private ArrayList<EventEntity> getEvents() {
        return eventService.findByMonthEvents(new Date(), "general");
    }

    public BarChartModel getModelByLocale() {
        return modelByLocale;
    }

    public LineChartModel getModelPerDate() {
        return modelPerDate;
    }

    public BarChartModel getModelBySysweb() {
        return modelBySysweb;
    }
}
