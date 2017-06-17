package com.reporter.hibernate.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "class", schema = "crazydomains")
public class ClazzEntity implements Serializable {
    private int class_id;
    private String name;
    private Set<TestEntity> testsByClassId = new HashSet<>(0);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id", nullable = false, unique = true)
    public int getClass_Id() {
        return class_id;
    }

    public void setClass_Id(int classId) {
        this.class_id = classId;
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

        ClazzEntity that = (ClazzEntity) o;

        if (class_id != that.class_id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = class_id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clazzByClassId")
    public Set<TestEntity> getTestsByClassId() {
        return testsByClassId;
    }

    public void setTestsByClassId(Set<TestEntity> testsByClassId) {
        this.testsByClassId = testsByClassId;
    }
}
