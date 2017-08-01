package com.beans;

import com.entity.EventEntity;
import com.service.EventService;
import com.service.TicketService;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@ManagedBean
@SessionScoped
public class EventTableExporter implements Serializable {

    private ArrayList<EventEntity> events;
    private EventEntity selectedEvent;
    private List<EventEntity> selectedEvents;

    private boolean isDataLoaded = false;


    @ManagedProperty(value = "#{eventService}")
    private EventService eventService;

    public EventService getEventService() {
        return eventService;
    }

    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    public boolean isDataLoaded() {
        return isDataLoaded;
    }

    public void setDataLoaded(boolean dataLoaded) {
        isDataLoaded = dataLoaded;
    }

    public void init() {
        events = eventService.findByDayEvents(new Date());
        setDataLoaded(true);
    }

    public ArrayList<String> getStatusesList() {
        ArrayList<String> statuses = new ArrayList<>();
        statuses.add("Checked");
        statuses.add("Checked, Issue");
        statuses.add("Checked, Fixed");
        statuses.add("Unchecked");
        return statuses;
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

    public void onCellEdit(CellEditEvent event) {
        Object newValue = event.getNewValue();
        EventEntity eventEntity = (EventEntity) ((DataTable) event.getComponent()).getRowData();
        System.out.println("edit event,  data = " + eventEntity.getData());

        String columnName = event.getColumn().getHeaderText();
        System.out.println("edit cell " + columnName);

        System.out.println("new value " + event.getNewValue());
        System.out.println("old value " + event.getOldValue());
        if (columnName.equals("Status")) {
            eventEntity.setStatus(newValue.toString());
        }
        if (columnName.equals("Ticket")) {
            eventEntity.setTicket(newValue.toString());
        }

        eventService.update(eventEntity);
        System.out.println("end cell edit ");
    }

    public HashSet<String> getLocales() {
        HashSet<String> set = new HashSet<>();
        ArrayList<EventEntity> events = (ArrayList<EventEntity>) getEvents();
        events.forEach(eventEntity -> set.add(eventEntity.getLocaleByLocaleId().getLocale()));
        return set;
    }

    public ArrayList<EventEntity> getEvents() {
        return events;
    }

    public EventEntity getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(EventEntity selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    public List<EventEntity> getSelectedEvents() {
        return selectedEvents;
    }

    public void setSelectedEvents(List<EventEntity> selectedEvents) {
        this.selectedEvents = selectedEvents;
    }

    public String getCurrentDate() {
        Date today = new Date();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        return formatter.format(today);
    }

    public TimeZone getTimeZone() {
        return TimeZone.getDefault();
    }
}

