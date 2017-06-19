package com.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "group", schema = "crazydomains")
public class GroupEntity implements Serializable {
    private int group_id;
    private String name;
    private Set<TestEntity> testsByGroupId = new HashSet<>(0);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id", nullable = false, unique = true)
    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    @Column(name = "name", nullable = false, length = 100, unique = true)
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

        GroupEntity that = (GroupEntity) o;

        if (group_id != that.group_id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = group_id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "groupByGroupId", fetch = FetchType.LAZY)
    public Set<TestEntity> getTestsByGroupId() {
        return testsByGroupId;
    }

    public void setTestsByGroupId(Set<TestEntity> testsByGroupId) {
        this.testsByGroupId = testsByGroupId;
    }
}
