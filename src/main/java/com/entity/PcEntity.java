package com.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pc", schema = "crazydomains")
public class PcEntity implements Serializable {
    private int pc_id;
    private String name;
    private String os;
    private Set<EventEntity> eventsByPcId = new HashSet<>(0);

    public PcEntity(){}

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "pc_id", nullable = false, unique = true)
    public int getPc_id() {
        return pc_id;
    }

    public void setPc_id(int pc_id) {
        this.pc_id = pc_id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "os", nullable = false, length = 45)
    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PcEntity that = (PcEntity) o;

        if (pc_id != that.pc_id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (os != null ? !os.equals(that.os) : that.os != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = pc_id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (os != null ? os.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "pcByPcId", fetch = FetchType.LAZY)
    public Set<EventEntity> getEventsByPcId() {
        return eventsByPcId;
    }

    public void setEventsByPcId(Set<EventEntity> eventsByPcId) {
        this.eventsByPcId = eventsByPcId;
    }
}
