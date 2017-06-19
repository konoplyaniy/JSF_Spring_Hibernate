package com.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "locale", schema = "crazydomains")
public class LocaleEntity implements Serializable {
    private int locale_id;
    private String locale;
    private Set<EventEntity> eventsByLocaleId = new HashSet<>(0);

    public LocaleEntity(){}

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "locale_id", nullable = false, unique = true)
    public int getLocale_id() {
        return locale_id;
    }

    public void setLocale_id(int locale_id) {
        this.locale_id = locale_id;
    }

    @Basic
    @Column(name = "locale", nullable = false, length = 20, unique = true)
    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocaleEntity that = (LocaleEntity) o;

        if (locale_id != that.locale_id) return false;
        if (locale != null ? !locale.equals(that.locale) : that.locale != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = locale_id;
        result = 31 * result + (locale != null ? locale.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "localeByLocaleId", fetch = FetchType.LAZY)
    public Set<EventEntity> getEventsByLocaleId() {
        return eventsByLocaleId;
    }

    public void setEventsByLocaleId(Set<EventEntity> eventsByLocaleId) {
        this.eventsByLocaleId = eventsByLocaleId;
    }
}
