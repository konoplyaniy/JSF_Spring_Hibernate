package com.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sysweb", schema = "crazydomains")
public class SyswebEntity implements Serializable {
    private int sysweb_id;
    private String name;
    private Set<EventEntity> eventsBySyswebId = new HashSet<>(0);

    public SyswebEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sysweb_id", nullable = false, unique = true)
    public int getSysweb_id() {
        return sysweb_id;
    }

    public void setSysweb_id(int sysweb_id) {
        this.sysweb_id = sysweb_id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 45, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SyswebEntity that = (SyswebEntity) o;

        if (sysweb_id != that.sysweb_id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sysweb_id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "syswebBySyswebId", fetch = FetchType.LAZY)
    public Set<EventEntity> getEventsBySyswebId() {
        return eventsBySyswebId;
    }

    public void setEventsBySyswebId(Set<EventEntity> eventsBySyswebId) {
        this.eventsBySyswebId = eventsBySyswebId;
    }
}
