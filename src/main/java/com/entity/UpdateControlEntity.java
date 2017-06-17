package com.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by geser on 16.06.17.
 */
@Entity
@Table(name = "update_control", schema = "", catalog = "qa")
public class UpdateControlEntity implements Serializable {
    private int id;
    private int user_id;
    private Date date;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

        UpdateControlEntity that = (UpdateControlEntity) o;

        if (id != that.id) return false;
        if (user_id != that.user_id) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + user_id;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
