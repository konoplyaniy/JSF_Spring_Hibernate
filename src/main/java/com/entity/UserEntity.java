package com.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by geser on 16.06.17.
 */
@Entity
@Table(name = "user", schema = "crazydomains")
public class UserEntity  implements Serializable{
    private int id;
    private String login;
    private String first_name;
    private String last_name;
    private String email;
    private byte[] avatar;
    private String password;
    private String role;
    private OvertimeEntity overtimeByOvertimeId;
    private TicketEntity ticketByTicketId;
    private UpdateControlEntity updateControlByUpdateControlId;

    public UserEntity() {
    }

    public UserEntity(String first_name, String last_name, String email) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "login", nullable = false, insertable = true, updatable = true, length = 45)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "first_name", nullable = false, insertable = true, updatable = true, length = 45)
    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String firstName) {
        this.first_name = firstName;
    }

    @Basic
    @Column(name = "last_name", nullable = false, insertable = true, updatable = true, length = 45)
    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String lastName) {
        this.last_name = lastName;
    }

    @Basic
    @Column(name = "email", nullable = false, insertable = true, updatable = true, length = 45)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "avatar", nullable = true, insertable = true, updatable = true)
    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    @Basic
    @Column(name = "password", nullable = false, insertable = true, updatable = true, length = 45)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "role", nullable = false, insertable = true, updatable = true, length = 45)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @ManyToOne
    @JoinColumn(name = "overtime_id", referencedColumnName = "id", nullable = false)
    public OvertimeEntity getOvertimeByOvertimeId() {
        return overtimeByOvertimeId;
    }

    public void setOvertimeByOvertimeId(OvertimeEntity overtimeByOvertimeId) {
        this.overtimeByOvertimeId = overtimeByOvertimeId;
    }

    @ManyToOne
    @JoinColumn(name = "ticket_id", referencedColumnName = "id", nullable = false)
    public TicketEntity getTicketByTicketId() {
        return ticketByTicketId;
    }

    public void setTicketByTicketId(TicketEntity ticketByTicketId) {
        this.ticketByTicketId = ticketByTicketId;
    }

    @ManyToOne
    @JoinColumn(name = "update_control_id", referencedColumnName = "id", nullable = false)
    public UpdateControlEntity getUpdateControlByUpdateControlId() {
        return updateControlByUpdateControlId;
    }

    public void setUpdateControlByUpdateControlId(UpdateControlEntity updateControlByUpdateControlId) {
        this.updateControlByUpdateControlId = updateControlByUpdateControlId;
    }
}
