package com.reporter.beans;

import com.reporter.hibernate.entities.EventEntity;
import com.reporter.hibernate.service.EventService;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;

import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "aust_events")
public class AustEventsExporter implements Serializable{
    private ArrayList<EventEntity> events;
    private EventEntity selectedEvent;
    private List<EventEntity> selectedEvents;

    private ArrayList<EventEntity> allAustEvents;
    private ArrayList<EventEntity> eventsAustFront;
    private ArrayList<EventEntity> eventsAustMembers;
    private EventService service;

    private boolean isDataLoaded = false;

    public boolean isDataLoaded() {
        return isDataLoaded;
    }

    public void setDataLoaded(boolean dataLoaded) {
        isDataLoaded = dataLoaded;
    }

    public void init() {
        service = new EventService();
        events = service.findByDayEvents(new Date(), "austdomains.com.au");
        System.out.println("All aust event count = " + events.size());
        eventsAustMembers = new ArrayList<>();
        eventsAustFront = new ArrayList<>();
        divideArrayToMembersAndFrontList(events, eventsAustMembers, eventsAustFront);
        setDataLoaded(true);
    }

    public void divideArrayToMembersAndFrontList(ArrayList<EventEntity> mainList, ArrayList<EventEntity> membersList,
                                                 ArrayList<EventEntity> frontList) {
        mainList.forEach(eventEntity -> {
            String className = eventEntity.getTestByTestId().getClazzByClassId().getName().toLowerCase();
            if (className.contains("members")) {
                membersList.add(eventEntity);
            } else frontList.add(eventEntity);
        });
        System.out.println("result list is ready");
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
        service.update(eventEntity);

        System.out.println("end cell edit ");
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

    public ArrayList<EventEntity> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<EventEntity> events) {
        this.events = events;
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

    public ArrayList<EventEntity> getAllAustEvents() {
        return allAustEvents;
    }

    public void setAllAustEvents(ArrayList<EventEntity> allAustEvents) {
        this.allAustEvents = allAustEvents;
    }

    public ArrayList<EventEntity> getEventsAustFront() {
        return eventsAustFront;
    }

    public void setEventsAustFront(ArrayList<EventEntity> eventsAustFront) {
        this.eventsAustFront = eventsAustFront;
    }

    public ArrayList<EventEntity> getEventsAustMembers() {
        return eventsAustMembers;
    }

    public void setEventsAustMembers(ArrayList<EventEntity> eventsAustMembers) {
        this.eventsAustMembers = eventsAustMembers;
    }
}
