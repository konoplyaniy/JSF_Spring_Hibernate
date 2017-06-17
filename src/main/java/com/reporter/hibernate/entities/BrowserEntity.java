package com.reporter.hibernate.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "browser", schema = "crazydomains", uniqueConstraints = {
        @UniqueConstraint(columnNames = "browser_id"),
        @UniqueConstraint(columnNames = "browser")})
public class BrowserEntity implements Serializable {
    private int browser_Id;
    private String browser;
    private Set<EventEntity> eventsByBrowserId = new HashSet<>(0);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "browser_id", nullable = false, unique = true)
    public int getBrowser_Id() {
        return browser_Id;
    }

    public void setBrowser_Id(int browser_Id) {
        this.browser_Id = browser_Id;
    }

    @Basic
    @Column(name = "browser", nullable = false, length = 100, unique = true)
    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BrowserEntity that = (BrowserEntity) o;

        if (browser_Id != that.browser_Id) return false;
        if (browser != null ? !browser.equals(that.browser) : that.browser != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = browser_Id;
        result = 31 * result + (browser != null ? browser.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "browserByBrowserId", fetch = FetchType.LAZY)
    public Set<EventEntity> getEventsByBrowserId() {
        return eventsByBrowserId;
    }

    public void setEventsByBrowserId(Set<EventEntity> eventsByBrowserId) {
        this.eventsByBrowserId = eventsByBrowserId;
    }
}
