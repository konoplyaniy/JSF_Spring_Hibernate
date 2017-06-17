package com.entity;;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by geser on 16.06.17.
 */
@Entity
@Table(name = "ticket", schema = "", catalog = "qa")
public class TicketEntity implements Serializable {
    private int id;
    private String url;
    private Date date;
    private String product;
    private int front;
    private int members;
    private Integer open_user_id;
    private Integer close_user_id;
    private Integer zendesk_id;

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
    @Column(name = "product")
    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Basic
    @Column(name = "front")
    public int getFront() {
        return front;
    }

    public void setFront(int front) {
        this.front = front;
    }

    @Basic
    @Column(name = "members")
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
    public Integer getClose_user_id() {
        return close_user_id;
    }

    public void setClose_user_id(Integer closeUserId) {
        this.close_user_id = closeUserId;
    }

    @Basic
    @Column(name = "zendesk_id", nullable = true, insertable = true, updatable = true)
    public Integer getZendesk_id() {
        return zendesk_id;
    }

    public void setZendesk_id(Integer zendeskId) {
        this.zendesk_id = zendeskId;
    }

}
