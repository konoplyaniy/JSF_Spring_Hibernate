package com.reporter.hibernate.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "test", schema = "crazydomains", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "class_id"})})

public class TestEntity implements Serializable {
    private int test_id;
    private String id_value;
    private String name;
    private Set<EventEntity> eventsByTestId = new HashSet<>(0);
    private GroupEntity groupByGroupId;
    private ClazzEntity clazzByClassId;

    public TestEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_id", nullable = false, unique = true)
    public int getTest_id() {
        return test_id;
    }

    public void setTest_id(int test_id) {
        this.test_id = test_id;
    }

    @Basic
    @Column(name = "id_value", nullable = true, length = 45)
    public String getId_value() {
        return id_value;
    }

    public void setId_value(String id_value) {
        this.id_value = id_value;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 300)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "testByTestId", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    public Set<EventEntity> getEventsByTestId() {
        return eventsByTestId;
    }

    public void setEventsByTestId(Set<EventEntity> eventsByTestId) {
        this.eventsByTestId = eventsByTestId;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "group_id", nullable = false)
    public GroupEntity getGroupByGroupId() {
        return groupByGroupId;
    }

    public void setGroupByGroupId(GroupEntity groupByGroupId) {
        this.groupByGroupId = groupByGroupId;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "class_id", nullable = false)
    public ClazzEntity getClazzByClassId() {
        return clazzByClassId;
    }

    public void setClazzByClassId(ClazzEntity clazzByClassId) {
        this.clazzByClassId = clazzByClassId;
    }
}
