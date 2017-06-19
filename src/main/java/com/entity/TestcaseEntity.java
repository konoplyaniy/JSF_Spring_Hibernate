package com.entity;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "testcase", schema = "crazydomains")
public class TestcaseEntity implements Serializable {
    private int test_case_id;
    private String class_name;
    private String test_name;
    private String parameters;
    private String description;
    private String steps;
    private String expected_result;
    private String aditional_info;
    private String maven_front;
    private String maven_members;

    public TestcaseEntity() {
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "test_case_id", nullable = false)
    public int getTest_case_id() {
        return test_case_id;
    }

    public void setTest_case_id(int test_case_id) {
        this.test_case_id = test_case_id;
    }

    @Basic
    @Column(name = "class_name", nullable = false, length = 400)
    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    @Basic
    @Column(name = "test_name", nullable = false, length = 400)
    public String getTest_name() {
        return test_name;
    }

    public void setTest_name(String test_name) {
        this.test_name = test_name;
    }

    @Basic
    @Column(name = "parameters", nullable = true, length = 400)
    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 400)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "steps", nullable = false, length = 1000)
    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    @Basic
    @Column(name = "expected_result", nullable = true, length = 1000)
    public String getExpected_result() {
        return expected_result;
    }

    public void setExpected_result(String expected_result) {
        this.expected_result = expected_result;
    }

    @Basic
    @Column(name = "aditional_info", nullable = true, length = 500)
    public String getAditional_info() {
        return aditional_info;
    }

    public void setAditional_info(String aditional_info) {
        this.aditional_info = aditional_info;
    }

    @Basic
    @Column(name = "maven_front", nullable = true, length = 400)
    public String getMaven_front() {
        return maven_front;
    }

    public void setMaven_front(String maven_front) {
        this.maven_front = maven_front;
    }

    @Basic
    @Column(name = "maven_members", nullable = true, length = 400)
    public String getMaven_members() {
        return maven_members;
    }

    public void setMaven_members(String maven_members) {
        this.maven_members = maven_members;
    }

    @Override
    public String toString() {
        return class_name + "\n " + test_name + "\n " + parameters + "\n " + description + "\n " + steps + "\n " + expected_result + "\n " + aditional_info + "\n " + maven_front + "\n " + maven_members;
    }
}
