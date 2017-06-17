package com.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by geser on 16.06.17.
 */
@Entity
@Table(name = "overtime", schema = "", catalog = "qa")
public class OvertimeEntity implements Serializable {
    private int id;
    private int user_id;
    private Timestamp period;
    private String project;
    private int approvement;
    private Date date;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id", nullable = false, insertable = true, updatable = true)
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int userId) {
        this.user_id = userId;
    }

    @Basic
    @Column(name = "period", nullable = false, insertable = true, updatable = true)
    public Timestamp getPeriod() {
        return period;
    }

    public void setPeriod(Timestamp period) {
        this.period = period;
    }

    @Basic
    @Column(name = "project", nullable = false, insertable = true, updatable = true, length = 45)
    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    @Basic
    @Column(name = "approvement", nullable = false, insertable = true, updatable = true)
    public int getApprovement() {
        return approvement;
    }

    public void setApprovement(int approvement) {
        this.approvement = approvement;
    }

    @Basic
    @Column(name = "date", nullable = false, insertable = true, updatable = true)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OvertimeEntity that = (OvertimeEntity) o;

        if (approvement != that.approvement) return false;
        if (id != that.id) return false;
        if (user_id != that.user_id) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (period != null ? !period.equals(that.period) : that.period != null) return false;
        if (project != null ? !project.equals(that.project) : that.project != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + user_id;
        result = 31 * result + (period != null ? period.hashCode() : 0);
        result = 31 * result + (project != null ? project.hashCode() : 0);
        result = 31 * result + approvement;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
