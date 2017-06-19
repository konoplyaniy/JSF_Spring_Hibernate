package com.beans;


import com.entity.EventEntity;
import com.service.EventService;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@SessionScoped
@ManagedBean(name = "report_exporter")
public class ReportExporter implements Serializable{
    private ArrayList<ReportRow> reportRowArrayList;
    private boolean isDataLoaded = false;

    @ManagedProperty(value = "#{eventService}")
    private EventService eventService;

    public EventService getEventService() {
        return eventService;
    }

    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    public void init() {
        setDataLoaded(false);
        ReportRow crazyAu = new ReportRow("crazy", "com.au");
        ReportRow crazyNz = new ReportRow("crazy", "co.nz");
        ReportRow crazyUk = new ReportRow("crazy", "co.uk");
        ReportRow crazyIn = new ReportRow("crazy", ".in");
        ReportRow crazyAe = new ReportRow("crazy", "ae");
        ReportRow aust = new ReportRow("aust", "com.au");
        ReportRow general = new ReportRow("general", "");

        ArrayList<EventEntity> todayEvents = eventService.findByDayEvents(new Date());
        todayEvents.forEach(eventEntity -> {
            switch (eventEntity.getLocaleByLocaleId().getLocale().toLowerCase()) {
                case "com.au":
                    crazyAu.parseStatus(eventEntity.getStatus());
                    break;
                case "co.nz":
                    crazyNz.parseStatus(eventEntity.getStatus());
                    break;
                case "nz":
                    crazyNz.parseStatus(eventEntity.getStatus());
                    break;
                case "co.uk":
                    crazyUk.parseStatus(eventEntity.getStatus());
                    break;
                case "in":
                    crazyIn.parseStatus(eventEntity.getStatus());
                    break;
                case "ae":
                    crazyAe.parseStatus(eventEntity.getStatus());
                    break;
                case "aust":
                    aust.parseStatus(eventEntity.getStatus());
                    break;
                case "":
                    general.parseStatus(eventEntity.getStatus());
            }

        });
        System.out.println("general events size = " + todayEvents.size());
        reportRowArrayList = new ArrayList<>();
        reportRowArrayList.add(crazyAu);
        reportRowArrayList.add(crazyNz);
        reportRowArrayList.add(crazyUk);
        reportRowArrayList.add(crazyIn);
        reportRowArrayList.add(crazyAe);
        reportRowArrayList.add(aust);
        reportRowArrayList.add(general);
        setDataLoaded(true);
    }

    public class ReportRow {
        String website;
        String locale;
        int checked = 0;
        int unchecked = 0;
        int checkedIssue = 0;
        int checkedFixed = 0;
        int failedCount = 0;

        public ReportRow(String website, String locale) {
            this.website = website;
            this.locale = locale;
        }

        public void parseStatus(String status) {
            failedCount++;
            switch (status) {
                case "Checked":
                    checked++;
                    break;
                case "Unchecked":
                    unchecked++;
                    break;
                case "Checked, Issue":
                    checkedIssue++;
                    break;
                case "Checked, Fixed":
                    checkedFixed++;
                    break;
            }
        }

        public String getWebsite() {
            return website;
        }

        public String getLocale() {
            return locale;
        }

        public int getChecked() {
            return checked;
        }

        public int getUnchecked() {
            return unchecked;
        }

        public int getCheckedIssue() {
            return checkedIssue;
        }

        public int getCheckedFixed() {
            return checkedFixed;
        }

        public int getFailedCount() {
            return failedCount;
        }
    }

    public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        CellStyle style = wb.createCellStyle();
        style.setFillBackgroundColor(IndexedColors.AQUA.getIndex());

        for (Row row : sheet) {
            for (Cell cell : row) {
                cell.setCellValue(cell.getStringCellValue().toUpperCase());
                cell.setCellStyle(style);
            }
        }
    }

    public ArrayList<String> getStatusesList() {
        ArrayList<String> statuses = new ArrayList<>();
        statuses.add("Checked");
        statuses.add("Checked, Issue");
        statuses.add("Checked, Fixed");
        statuses.add("Unchecked");
        return statuses;
    }

    public String getCurrentDate() {
        Date today = new Date();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        return formatter.format(today);
    }

    public ArrayList<ReportRow> getReportRowArrayList() {
        return reportRowArrayList;
    }

    public boolean isDataLoaded() {
        return isDataLoaded;
    }

    public void setDataLoaded(boolean dataLoaded) {
        isDataLoaded = dataLoaded;
    }
}
