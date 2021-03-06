package com.beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.entity.TicketEntity;
import com.entity.UserEntity;
import com.service.TicketService;
import com.service.UpdateControlService;
import com.service.UserService;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LegendPlacement;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.PieChartModel;

@ManagedBean
@RequestScoped
public class ChartsBean implements Serializable {
    @ManagedProperty(value = "#{updateControlService}")
    UpdateControlService updateControlService;
    @ManagedProperty(value = "#{ticketService}")
    TicketService ticketService;
    @ManagedProperty(value = "#{calendarBean}")
    CalendarBean calendarBean;
    @ManagedProperty(value = "#{loginBean}")
    LoginBean loginBean;
    @ManagedProperty(value = "#{userService}")
    UserService userService;
    private PieChartModel pieModel;
    private LineChartModel lineChart;
    private BarChartModel barChartModel;
    private String option = "Both";

    public CalendarBean getCalendarBean() {
        return calendarBean;
    }

    public void setCalendarBean(CalendarBean calendarBean) {
        this.calendarBean = calendarBean;
    }

    public TicketService getTicketService() {
        return ticketService;
    }

    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public PieChartModel getPieModel() {
        return pieModel;
    }

    public void setPieModel(PieChartModel pieModel) {
        this.pieModel = pieModel;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public LineChartModel getLineChart() {
        return lineChart;
    }

    public void setLineChart(LineChartModel lineChart) {
        this.lineChart = lineChart;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public BarChartModel getBarChartModel() {
        return barChartModel;
    }

    public void setBarChartModel(BarChartModel barChartModel) {
        this.barChartModel = barChartModel;
    }

    public UpdateControlService getUpdateControlService() {
        return updateControlService;
    }

    public void setUpdateControlService(UpdateControlService updateControlService) {
        this.updateControlService = updateControlService;
    }

    @PostConstruct
    public void init() {
        createLineModels();
        createPieModels();
        createBarModel();
    }

    private void createPieModels() {
        createPieModel();
    }

    private void createLineModels() {
        createDateModel();
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public void selectOneButtonChart() {
        FacesMessage message = null;
        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Choose area", option);
        FacesContext.getCurrentInstance().addMessage(null, message);
        createPieModel();
    }

    private void createPieModel() {
        pieModel = new PieChartModel();
        HashMap<String, Integer> sortedTicketsHashMap = getSortedTicketsHashMap();
        for (Map.Entry<String, Integer> value : sortedTicketsHashMap.entrySet()) {
            pieModel.set(value.getKey(), value.getValue());
        }
        pieModel.setSeriesColors(
                "0080ff,0055ff,8080ff,4040bf,666699,8000ff,c61aff,ff1aff,ff0080,ff0000,ff5500,ffaa00,bf8040,aaaa55,66cc00,00cc00,40bf80,00ffff,00ccff,99ffcc,ffff33,ff7733,ff0066,ff33ff");
        pieModel.setLegendPosition("s");
        pieModel.setLegendPlacement(LegendPlacement.OUTSIDE);
        pieModel.setShowDataLabels(true);
        pieModel.setLegendCols(2);
        pieModel.setShowDatatip(true);
        pieModel.setExtender("customExtender");
        pieModel.setShadow(false);
    }

    /**
     * For displaying data in "Most Failed Product" fields PRODUCT and COUNT
     *
     * @return Product name and count of created tickets for it
     */
    public List<Map.Entry<String, Integer>> getProducts() {
        Set<Map.Entry<String, Integer>> productsSet = getSortedTicketsHashMap().entrySet();
        return new ArrayList<Map.Entry<String, Integer>>(productsSet);
    }

    /**
     * For displaying data in "Most Failed Product" field "PART"
     *
     * @return Product name and count of created tickets for it
     */
    public Integer getTotalProducts() {
        int total = 0;
        HashMap<String, Integer> sortedTicketsHashMap = getSortedTicketsHashMap();
        for (Map.Entry<String, Integer> value : sortedTicketsHashMap.entrySet()) {
            total = total + value.getValue();
        }
        return total;
    }

    public HashMap<String, Integer> getSortedTicketsHashMap() {
        ArrayList<TicketEntity> currentMonthTickets = new ArrayList<>();
        ArrayList<TicketEntity> sortedByAreaTickets = new ArrayList<>();

        switch (loginBean.getUserRole()) {
            case "admin": {
                currentMonthTickets = ticketService.getAllUsersTicketsByMonth(calendarBean.getDate());
                break;
            }
            case "user": {
                currentMonthTickets = ticketService.getTicketsByDateUserId(loginBean.getUser_id(), calendarBean.getDate());
                break;
            }
        }
//		if (loginBean.getUserRole().equals("admin")) {
//			ticketsBefore = ticketService.getAllUsersTicketsByMonth(calendarBean.getDate());
//		} else if (loginBean.getUserRole().equals("user")) {
//			ticketsBefore = ticketService.getTicketsByDateUserId(loginBean.getUser_id(), calendarBean.getDate());
//		} else {
//			return null;
//		}
        switch (option) {
            case "Front": {
                for (TicketEntity ticket : currentMonthTickets) {
                    if (ticket.getFront() == 1) {
                        sortedByAreaTickets.add(ticket);
                    }
                }
                break;
            }
            case "Members": {
                for (TicketEntity ticket : currentMonthTickets) {
                    if (ticket.getMembers() == 1) {
                        sortedByAreaTickets.add(ticket);
                    }
                }
                break;
            }
            case "Both": {
                for (TicketEntity ticket : currentMonthTickets) {
                    if (ticket.getMembers() == 1) {
                        sortedByAreaTickets.add(ticket);
                    }
                    if (ticket.getFront() == 1) {
                        sortedByAreaTickets.add(ticket);
                    }
                }
                break;
            }
        }
//		if (option.equals("Front")) {
//			for (TicketEntity ticket : ticketsBefore) {
//				if (ticket.getFront() == 1) {
//					tickets.add(ticket);
//				}
//			}
//		} else if (option.equals("Members")) {
//			for (TicketEntity ticket : ticketsBefore) {
//				if (ticket.getMembers() == 1) {
//					tickets.add(ticket);
//				}
//			}
//		} else if (option.equals("Both")) {
//			for (TicketEntity ticket : ticketsBefore) {
//				if (ticket.getMembers() == 1 ) {
//					tickets.add(ticket);
//				}if (ticket.getFront() == 1) {
//					tickets.add(ticket);
//				}
//			}
//		}
        HashMap<String, Integer> sortedTicketsHashMap = new HashMap<>();
        if (sortedByAreaTickets.isEmpty()) {
            sortedTicketsHashMap.put("No data", 0);
            return sortedTicketsHashMap;
        } else {
            for (TicketEntity ticket : sortedByAreaTickets) {
                if (!sortedTicketsHashMap.containsKey(ticket.getProduct())) {
                    sortedTicketsHashMap.put(ticket.getProduct(), 1);
                } else if (sortedTicketsHashMap.containsKey(ticket.getProduct())) {
                    int count = sortedTicketsHashMap.get(ticket.getProduct());
                    count++;
                    sortedTicketsHashMap.put(ticket.getProduct(), count);
                }
            }
            return sortedTicketsHashMap;
        }
    }

    private void createDateModel() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(calendarBean.getDate());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        lineChart = new LineChartModel();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        LineChartSeries series = new LineChartSeries();
        HashMap<Date, Integer> ticketsCountHashMap = new HashMap<>();

        switch (loginBean.getUserRole()) {
            case "admin": {
                List<UserEntity> users = userService.getAllUsers();
                for (UserEntity user : users) {
                    series.setLabel(user.getFirst_name() + " " + user.getLast_name());
                    ArrayList<TicketEntity> todayTicketsList = ticketService.getTicketsByDateUserId(user.getId(),
                            calendarBean.getDate());
                    if (todayTicketsList.size() == 0) {
                        series.set(dateFormat.format(calendar.getTime()), 0);
                    } else {
                        for (TicketEntity ticket : todayTicketsList) {
                            if (!ticketsCountHashMap.containsKey(ticket.getDate())) {
                                ticketsCountHashMap.put(ticket.getDate(), 1);
                            } else if (ticketsCountHashMap.containsKey(ticket.getDate())) {
                                int count = ticketsCountHashMap.get(ticket.getDate());
                                count++;
                                ticketsCountHashMap.put(ticket.getDate(), count);
                            }
                        }
                    }
                    break;
                }
            }
            case "user": {
                UserEntity user = loginBean.getUser();
                series.setLabel(user.getFirst_name() + " " + user.getLast_name());
                ArrayList<TicketEntity> tickets = ticketService.getTicketsByDateUserId(user.getId(),
                        calendarBean.getDate());
                if (tickets.size() == 0) {
                    series.set(dateFormat.format(calendar.getTime()), 0);
                } else {
                    for (TicketEntity ticket : tickets) {
                        if (!ticketsCountHashMap.containsKey(ticket.getDate())) {
                            ticketsCountHashMap.put(ticket.getDate(), 1);
                        } else if (ticketsCountHashMap.containsKey(ticket.getDate())) {
                            int count = ticketsCountHashMap.get(ticket.getDate());
                            count++;
                            ticketsCountHashMap.put(ticket.getDate(), count);
                        }
                    }
                }
                break;
            }
        }
//        if (loginBean.getUserRole().equals("admin")) {
//            List<UserEntity> users = userService.getAllUsers();
//            for (UserEntity user : users) {
//                LineChartSeries series1 = new LineChartSeries();
//                HashMap<Date, Integer> ticketCount = new HashMap<>();
//                series1.setLabel(user.getFirst_name() + " " + user.getLast_name());
//                ArrayList<TicketEntity> tickets = ticketService.getTicketsByDateUserId(user.getId(),
//                        calendarBean.getDate());
//                if (tickets.size() == 0) {
//                    series1.set(dateFormat.format(calendar.getTime()), 0);
//                } else {
//                    for (TicketEntity ticket : tickets) {
//                        if (!ticketCount.containsKey(ticket.getDate())) {
//                            ticketCount.put(ticket.getDate(), 1);
//                        } else if (ticketCount.containsKey(ticket.getDate())) {
//                            int count = ticketCount.get(ticket.getDate());
//                            count++;
//                            ticketCount.put(ticket.getDate(), count);
//                        }
//
//                    }
//                    for (Map.Entry<Date, Integer> value : ticketCount.entrySet()) {
//                        series1.set(dateFormat.format(value.getKey()), value.getValue());
//                    }
//
//                }
//                series1.setFill(true);
//                series1.setFillAlpha(0.5);
//                series1.setShowLine(true);
//                lineChart.addSeries(series1);
//            }
//        } else if (loginBean.getUserRole().equals("user")) {
//            UserEntity user = loginBean.getUser();
//            LineChartSeries series1 = new LineChartSeries();
//            HashMap<Date, Integer> ticketCount = new HashMap<>();
//            series1.setLabel(user.getFirst_name() + " " + user.getLast_name());
//            ArrayList<TicketEntity> tickets = ticketService.getTicketsByDateUserId(user.getId(),
//                    calendarBean.getDate());
//            if (tickets.size() == 0) {
//                series1.set(dateFormat.format(calendar.getTime()), 0);
//            } else {
//                for (TicketEntity ticket : tickets) {
//                    if (!ticketCount.containsKey(ticket.getDate())) {
//                        ticketCount.put(ticket.getDate(), 1);
//                    } else if (ticketCount.containsKey(ticket.getDate())) {
//                        int count = ticketCount.get(ticket.getDate());
//                        count++;
//                        ticketCount.put(ticket.getDate(), count);
//                    }
//
//                }
//                for (Map.Entry<Date, Integer> value : ticketCount.entrySet()) {
//                    series1.set(dateFormat.format(value.getKey()), value.getValue());
//                }
//
//            }
//            series1.setFill(true);
//            series1.setFillAlpha(0.5);
//            series1.setShowLine(true);
//            lineChart.addSeries(series1);
//
//        }

        for (Map.Entry<Date, Integer> value : ticketsCountHashMap.entrySet()) {
            series.set(dateFormat.format(value.getKey()), value.getValue());
        }
        series.setFill(true);
        series.setFillAlpha(0.5);
        series.setShowLine(true);
        lineChart.addSeries(series);
        lineChart.setResetAxesOnResize(false);
        lineChart.setBreakOnNull(true);
        Axis yAxis = lineChart.getAxis(AxisType.Y);
        yAxis.setTickInterval("1");
        yAxis.setMin(0);
        lineChart.setLegendPosition("s");
        lineChart.setLegendCols(2);
        lineChart.setShowPointLabels(true);
        lineChart.setBreakOnNull(true);
        lineChart.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
        lineChart.setZoom(false);
        lineChart.setMouseoverHighlight(true);
        lineChart.setExtender("customExtenderLine");
        lineChart.setSeriesColors(
                "0080ff,0055ff,8080ff,4040bf,666699,8000ff,c61aff,ff1aff,ff0080,ff0000,ff5500,ffaa00,bf8040,aaaa55,66cc00,00cc00,40bf80,00ffff,00ccff,99ffcc,ffff33,ff7733,ff0066,ff33ff");

        DateAxis axis = new DateAxis();
        axis.setTickAngle(-90);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        axis.setMax(dateFormat.format(calendar.getTime()));
        axis.setTickInterval("172800");
        axis.setTickFormat("%b %#d, %y");

        lineChart.getAxes().put(AxisType.X, axis);
    }

    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();
        for (UserEntity user : loginBean.getAllUsers()) {
            ChartSeries chart = new ChartSeries();
            chart.setLabel(user.getFirst_name() + " " + user.getLast_name());
            chart.set("Created Tickets",
                    ticketService.getUserCreatedTicketsByDate(user.getId(), calendarBean.getDate()).size());
            chart.set("Closed Tickets",
                    ticketService.getUserClosedTicketsByDate(user.getId(), calendarBean.getDate()).size());
            chart.set("Update Control",
                    updateControlService.getUserUpdateControlByDate(user.getId(), calendarBean.getDate()).size());
            model.addSeries(chart);
        }
        return model;
    }

    private void createBarModel() {
        barChartModel = initBarModel();
        barChartModel.setShowPointLabels(true);
        barChartModel.setLegendPosition("s");
        barChartModel.setExtender("customExtenderBar");
        barChartModel.setLegendCols(2);
        barChartModel.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
        barChartModel.setSeriesColors(
                "0080ff,0055ff,8080ff,4040bf,666699,8000ff,c61aff,ff1aff,ff0080,ff0000,ff5500,ffaa00,bf8040,aaaa55,66cc00,00cc00,40bf80,00ffff,00ccff,99ffcc,ffff33,ff7733,ff0066,ff33ff");

        Axis yAxis = barChartModel.getAxis(AxisType.Y);
        yAxis.setTickInterval("1");
        yAxis.setMin(0);
        Axis xAxis = barChartModel.getAxis(AxisType.X);
    }

}
