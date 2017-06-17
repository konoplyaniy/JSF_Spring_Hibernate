package com.reporter.hibernate.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "event", schema = "crazydomains")
public class EventEntity implements Serializable {
    private int event_id;
    private Date data;
    private int test_id;
    private int locale_id;
    private int sysweb_id;
    private int pc_id;
    private String url;
    private String message;
    private String params;
    private int browser_id;
    private int checked;
    private String website;
    private String email_id;
    private String caused_by;
    private String steps;
    private String ticket;
    private String status;

    private TestEntity testByTestId;
    private LocaleEntity localeByLocaleId;
    private SyswebEntity syswebBySyswebId;
    private PcEntity pcByPcId;
    private BrowserEntity browserByBrowserId;


    public EventEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id", nullable = false)
    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int eventId) {
        this.event_id = event_id;
    }

    @Basic
    @Column(name = "data", nullable = false)
    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    @Basic
    @Column(name = "url", nullable = true, length = 1000)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "message", nullable = true, length = 100)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Basic
    @Column(name = "params", nullable = true, length = 1000)
    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Basic
    @Column(name = "checked", nullable = false)
    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    @Basic
    @Column(name = "website", nullable = true, length = 100)
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Basic
    @Column(name = "email_id", nullable = true, length = 45)
    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    @Basic
    @Column(name = "caused_by", nullable = true, length = 1000)
    public String getCaused_by() {
        return caused_by;
    }

    public void setCaused_by(String caused_by) {
        this.caused_by = caused_by;
    }

    @Basic
    @Column(name = "steps", nullable = true, length = 1000)
    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    @Basic
    @Column(name = "ticket", nullable = true, length = 300)
    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    @Basic
    @Column(name = "status", nullable = true, length = 45)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id", nullable = false)
    public TestEntity getTestByTestId() {
        return testByTestId;
    }

    public void setTestByTestId(TestEntity testByTestId) {
        this.testByTestId = testByTestId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locale_id", nullable = false)
    public LocaleEntity getLocaleByLocaleId() {
        return localeByLocaleId;
    }

    public void setLocaleByLocaleId(LocaleEntity localeByLocaleId) {
        this.localeByLocaleId = localeByLocaleId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sysweb_id", nullable = false)
    public SyswebEntity getSyswebBySyswebId() {
        return syswebBySyswebId;
    }

    public void setSyswebBySyswebId(SyswebEntity syswebBySyswebId) {
        this.syswebBySyswebId = syswebBySyswebId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pc_id", nullable = false)
    public PcEntity getPcByPcId() {
        return pcByPcId;
    }

    public void setPcByPcId(PcEntity pcByPcId) {
        this.pcByPcId = pcByPcId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "browser_id", nullable = false)
    public BrowserEntity getBrowserByBrowserId() {
        return browserByBrowserId;
    }

    public void setBrowserByBrowserId(BrowserEntity browserByBrowserId) {
        this.browserByBrowserId = browserByBrowserId;
    }
}
