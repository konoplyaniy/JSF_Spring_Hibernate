package com.entity;;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by geser on 16.06.17.
 */
@Entity
@Table(name = "ticket", schema = "", catalog = "qa")
public class TicketEntity {
    private int id;
    private String url;
    private Date date;
    private String product;
    private int front;
    private int members;
    private Integer open_user_id;
    private Integer close_user_Id;
    private Integer zendesk_id;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "url", nullable = true, insertable = true, updatable = true, length = 200)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "date", nullable = false, insertable = true, updatable = true)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "product", nullable = false, insertable = true, updatable = true, length = 45)
    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Basic
    @Column(name = "front", nullable = false, insertable = true, updatable = true, length = 45)
    public int getFront() {
        return front;
    }

    public void setFront(int front) {
        this.front = front;
    }

    @Basic
    @Column(name = "members", nullable = false, insertable = true, updatable = true, length = 45)
    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }

    @Basic
    @Column(name = "open_user_id", nullable = true, insertable = true, updatable = true)
    public Integer getOpen_user_id() {
        return open_user_id;
    }

    public void setOpen_user_id(Integer openUserId) {
        this.open_user_id = openUserId;
    }

    @Basic
    @Column(name = "close_user_id", nullable = true, insertable = true, updatable = true)
    public Integer getClose_user_Id() {
        return close_user_Id;
    }

    public void setClose_user_Id(Integer closeUserId) {
        this.close_user_Id = closeUserId;
    }

    @Basic
    @Column(name = "zendesk_id", nullable = true, insertable = true, updatable = true)
    public Integer getZendesk_id() {
        return zendesk_id;
    }

    public void setZendesk_id(Integer zendeskId) {
        this.zendesk_id = zendeskId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketEntity that = (TicketEntity) o;

        if (id != that.id) return false;
        if (close_user_Id != null ? !close_user_Id.equals(that.close_user_Id) : that.close_user_Id != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (open_user_id != null ? !open_user_id.equals(that.open_user_id) : that.open_user_id != null) return false;
        if (product != null ? !product.equals(that.product) : that.product != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (zendesk_id != null ? !zendesk_id.equals(that.zendesk_id) : that.zendesk_id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (product != null ? product.hashCode() : 0);
        result = 31 * result + (open_user_id != null ? open_user_id.hashCode() : 0);
        result = 31 * result + (close_user_Id != null ? close_user_Id.hashCode() : 0);
        result = 31 * result + (zendesk_id != null ? zendesk_id.hashCode() : 0);
        return result;
    }
}
