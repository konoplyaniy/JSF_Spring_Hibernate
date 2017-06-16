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

@ManagedBean(name = "crazy_events")
public class CrazyEventsExporter implements Serializable{

    private ArrayList<EventEntity> events;
    private ArrayList<EventEntity> allCrazyEvents;
    private ArrayList<EventEntity> eventsComAuLocale;
    private ArrayList<EventEntity> eventsCoUkLocale;
    private ArrayList<EventEntity> eventsInLocale;
    private ArrayList<EventEntity> eventsCoNzLocale;
    private ArrayList<EventEntity> eventsAeLocale;
    private ArrayList<EventEntity> eventsCrazyFront;
    private ArrayList<EventEntity> eventsCrazyMembers;

    private ArrayList<ListEntities> eventByLocale;
    private EventEntity selectedEvent;
    private List<EventEntity> selectedEvents;
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
        events = service.findByDayEvents(new Date(), "crazydomains");
        System.out.println("All crazy event count = " + events.size());

        eventsComAuLocale = new ArrayList<>();
        eventsCoUkLocale = new ArrayList<>();
        eventsInLocale = new ArrayList<>();
        eventsCoNzLocale = new ArrayList<>();
        eventsAeLocale = new ArrayList<>();

        allCrazyEvents = new ArrayList<>();
        eventsCrazyFront = new ArrayList<>();
        eventsCrazyMembers = new ArrayList<>();

        /*init Crazy by locale lists*/
        events.forEach(eventEntity -> {
            System.out.println("divide events by locales");
            switch (eventEntity.getLocaleByLocaleId().getLocale().toLowerCase()) {
                case "com.au":
                    System.out.println("add COM.AU locale event");
                    eventsComAuLocale.add(eventEntity);
                    break;
                case "co.uk":
                    System.out.println("add CO.UK locale event");
                    eventsCoUkLocale.add(eventEntity);
                    break;
                case "in":
                    System.out.println("add IN locale event");
                    eventsInLocale.add(eventEntity);
                    break;
                case "co.nz":
                    System.out.println("add NZ locale event");
                    eventsCoNzLocale.add(eventEntity);
                    break;
                case "nz":
                    System.out.println("add NZ locale event");
                    eventsCoNzLocale.add(eventEntity);
                    break;
                case "ae":
                    System.out.println("add AE locale event");
                    eventsAeLocale.add(eventEntity);
                    break;
            }
        });

        /*init Crazy members and front events*/
        divideArrayToMembersAndFrontList(events, eventsCrazyMembers, eventsCrazyFront);

        eventByLocale = new ArrayList<>();
        eventByLocale.add(new CrazyEventsExporter.ListEntities("com.au", eventsComAuLocale));
        eventByLocale.add(new CrazyEventsExporter.ListEntities("co.uk", eventsCoUkLocale));
        eventByLocale.add(new CrazyEventsExporter.ListEntities("co.nz", eventsCoNzLocale));
        eventByLocale.add(new CrazyEventsExporter.ListEntities("in", eventsInLocale));
        eventByLocale.add(new CrazyEventsExporter.ListEntities("ae", eventsAeLocale));
        setDataLoaded(true);
    }

    public void divideArrayToMembersAndFrontList(ArrayList<EventEntity> mainList, ArrayList<EventEntity> membersList,
                                                 ArrayList<EventEntity> frontList) {
        mainList.forEach(eventEntity -> {
            String className = eventEntity.getTestByTestId().getClazzByClassId().getName().toLowerCase();
            if (className.contains("members")) {
                System.out.println("add crazy members");
                membersList.add(eventEntity);
            } else {
                System.out.println("add crazy front");
                frontList.add(eventEntity);
            }
        });
    }

    public String getCurrentDate() {
        Date today = new Date();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        return formatter.format(today);
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


    public class ListEntities implements Serializable {
        String locale;
        ArrayList<EventEntity> events;
        ArrayList<EventEntity> uncheckedEvents;

        public String getLocale() {
            return locale;
        }

        public void setLocale(String locale) {
            this.locale = locale;
        }

        public ArrayList<EventEntity> getEvents() {
            return events;
        }

        public ArrayList<EventEntity> getUncheckedEvents() {
            uncheckedEvents = new ArrayList<>();
            events.forEach(event -> {
                if (!event.getStatus().toLowerCase().equals("checked")) {
                    uncheckedEvents.add(event);
                }
            });
            return uncheckedEvents;
        }

        public void setUncheckedEvents(ArrayList<EventEntity> uncheckedEvents) {
            this.uncheckedEvents = uncheckedEvents;
        }

        public void setEvents(ArrayList<EventEntity> events) {
            this.events = events;
        }

        public ListEntities(String locale, ArrayList<EventEntity> events) {
            this.locale = locale;
            this.events = events;
        }

        public int getFailedTestsCount() {
            return events.size();
        }
    }

    public ArrayList<EventEntity> getAllCrazyEvents() {
        return allCrazyEvents;
    }

    public void setAllCrazyEvents(ArrayList<EventEntity> allCrazyEvents) {
        this.allCrazyEvents = allCrazyEvents;
    }

    public ArrayList<EventEntity> getEventsComAuLocale() {
        return eventsComAuLocale;
    }

    public void setEventsComAuLocale(ArrayList<EventEntity> eventsComAuLocale) {
        this.eventsComAuLocale = eventsComAuLocale;
    }

    public ArrayList<EventEntity> getEventsCoUkLocale() {
        return eventsCoUkLocale;
    }

    public void setEventsCoUkLocale(ArrayList<EventEntity> eventsCoUkLocale) {
        this.eventsCoUkLocale = eventsCoUkLocale;
    }

    public ArrayList<EventEntity> getEventsInLocale() {
        return eventsInLocale;
    }

    public void setEventsInLocale(ArrayList<EventEntity> eventsInLocale) {
        this.eventsInLocale = eventsInLocale;
    }

    public ArrayList<EventEntity> getEventsCoNzLocale() {
        return eventsCoNzLocale;
    }

    public void setEventsCoNzLocale(ArrayList<EventEntity> eventsCoNzLocale) {
        this.eventsCoNzLocale = eventsCoNzLocale;
    }

    public ArrayList<EventEntity> getEventsAeLocale() {
        return eventsAeLocale;
    }

    public void setEventsAeLocale(ArrayList<EventEntity> eventsAeLocale) {
        this.eventsAeLocale = eventsAeLocale;
    }

    public ArrayList<EventEntity> getEventsCrazyFront() {
        return eventsCrazyFront;
    }

    public void setEventsCrazyFront(ArrayList<EventEntity> eventsCrazyFront) {
        this.eventsCrazyFront = eventsCrazyFront;
    }

    public ArrayList<EventEntity> getEventsCrazyMembers() {
        return eventsCrazyMembers;
    }

    public void setEventsCrazyMembers(ArrayList<EventEntity> eventsCrazyMembers) {
        this.eventsCrazyMembers = eventsCrazyMembers;
    }

    public ArrayList<ListEntities> getEventByLocale() {
        return eventByLocale;
    }

    public void setEventByLocale(ArrayList<ListEntities> eventByLocale) {
        this.eventByLocale = eventByLocale;
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

    public ArrayList<EventEntity> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<EventEntity> events) {
        this.events = events;
    }

    public ArrayList<String> getStatusesList() {
        ArrayList<String> statuses = new ArrayList<>();
        statuses.add("Checked");
        statuses.add("Checked, Issue");
        statuses.add("Checked, Fixed");
        statuses.add("Unchecked");
        return statuses;
    }
}
