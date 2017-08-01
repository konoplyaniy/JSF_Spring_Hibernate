package com.beans;

import com.dao.TestCaseDao;
import com.entity.TestcaseEntity;
import com.service.TestCaseService;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

@ManagedBean
@SessionScoped
public class TestCasesView implements Serializable {
    private TestcaseEntity testcaseEntity;

    /*SEARCH EXISTING TES CASE*/
    private String testName = "";
    private String clazzName = "";
    private HashSet<String> clazzNames;
    private HashSet<String> testNames;
    private boolean visibleResultsForm = false;

    /*NEW TEST CASE DETAILS*/
    private String clazzNameNew = "";
    private String testNameNew = "";
    private String parametersNew = "";
    private String descriptionNew = "";
    private String stepsNew = "";
    private String expectedResultNew = "";
    private String aditionalInfoNew = "";
    private String mavenFrontNew = "";
    private String mavenMembersNew = "";
    private TestcaseEntity testcaseNew;
    private boolean visibleAddNewTestCaseForm = false;
    private HashMap<String, HashSet<String>> hashMap;

    /**/
    private boolean visibleEditTestCaseForm = false;

    @PostConstruct
    public void initDropDowns() {
        initDropdownsData();
    }

    private void initDropdownsData() {
        hashMap = new HashMap<>();

        TestCaseService service = new TestCaseService();
        ArrayList<TestcaseEntity> testCaseList = service.findAll();
        HashSet<String> clazzes = new HashSet<>();

        testCaseList.forEach(testCase -> {
            HashSet<String> testNamesList = new HashSet<>();
            if (hashMap.containsKey(testCase.getClass_name())) {
                testNamesList = hashMap.get(testCase.getClass_name());
                testNamesList.add(testCase.getTest_name());
            } else {
                testNamesList.add(testCase.getTest_name());
            }
            hashMap.put(testCase.getClass_name(), testNamesList);

            testNamesList.add(testCase.getTest_name());
            clazzes.add(testCase.getClass_name());
        });

        setClazzNames(clazzes);
    }

    public void clickSearchButton() {
        testcaseEntity = new TestCaseService().findByClassNameTestName(getClazzName(), getTestName());
        setVisibleResultsForm(true);
        setVisibleAddNewTestCaseForm(false);
        setVisibleEditTestCaseForm(false);
    }

    public void clickAddButton() {
        setVisibleResultsForm(false);
        setVisibleEditTestCaseForm(false);
        setVisibleAddNewTestCaseForm(true);
    }

    public void clickEditButton() {
        testcaseEntity = new TestCaseService().findByClassNameTestName(getClazzName(), getTestName());
        if (testcaseEntity == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Search results", " Can't find test case. Contact support"));
        } else {
            setVisibleAddNewTestCaseForm(false);
            setVisibleResultsForm(false);
            setVisibleEditTestCaseForm(true);
        }
    }

    public void clickSaveChangesButton() {
        TestCaseService service = new TestCaseService();


        if (service.exist(testcaseEntity.getClass_name(), testcaseEntity.getTest_name())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Saving test case", " Edited test case successfully saved"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Saving test case Error", " Can't save edited test case, please try again later"));
        }

        setVisibleEditTestCaseForm(false);
    }

    public void clickSaveButton() {
        testcaseNew = new TestcaseEntity();
        testcaseNew.setClass_name(getClazzNameNew());
        testcaseNew.setTest_name(getTestNameNew());
        testcaseNew.setParameters(getParametersNew());
        testcaseNew.setSteps(getStepsNew());
        testcaseNew.setDescription(getDescriptionNew());
        testcaseNew.setExpected_result(getExpectedResultNew());
        testcaseNew.setAditional_info(getAditionalInfoNew());
        testcaseNew.setMaven_front(getMavenFrontNew());

        TestCaseService service = new TestCaseService();


        // TODO need rewrite method
         /*   if (service.exist(testcaseNew.getClass_name(), testcaseNew.getTest_name())) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Can't save test case", "Test case already exist, try edit it"));
            } else {
                if (service.(testcaseNew)) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Saving test case", " New test case successfully saved"));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Saving test case Error", " Can't save new test case, please try again later"));
                }
                tr.commit();
                session.flush();
                initDropDowns();
            }*/
        setVisibleAddNewTestCaseForm(false);
    }

    public void onClassNameChange() {
        if (getClazzName() != null && !getClazzName().equals("")) {
            HashSet<String> tests = hashMap.get(getClazzName());
            setTestNames(tests);
            initDropdownsData();
        }
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public HashSet<String> getClazzNames() {
        return clazzNames;
    }

    public void setClazzNames(HashSet<String> clazzNames) {
        this.clazzNames = clazzNames;
    }

    public HashSet<String> getTestNames() {
        return testNames;
    }

    public void setTestNames(HashSet<String> testNames) {
        this.testNames = testNames;
    }

    public boolean isVisibleResultsForm() {
        return visibleResultsForm;
    }

    public void setVisibleResultsForm(boolean visibleResultsForm) {
        this.visibleResultsForm = visibleResultsForm;
    }

    public boolean isVisibleAddNewTestCaseForm() {
        return visibleAddNewTestCaseForm;
    }

    public void setVisibleAddNewTestCaseForm(boolean visibleAddNewTestCaseForm) {
        this.visibleAddNewTestCaseForm = visibleAddNewTestCaseForm;
    }

    public TestcaseEntity getTestcaseEntity() {
        return testcaseEntity;
    }

    public void setTestcaseEntity(TestcaseEntity testcaseEntity) {
        this.testcaseEntity = testcaseEntity;
    }

    public String getClazzNameNew() {
        return clazzNameNew;
    }

    public void setClazzNameNew(String clazzNameNew) {
        this.clazzNameNew = clazzNameNew;
    }

    public String getTestNameNew() {
        return testNameNew;
    }

    public void setTestNameNew(String testNameNew) {
        this.testNameNew = testNameNew;
    }

    public String getParametersNew() {
        return parametersNew;
    }

    public void setParametersNew(String parametersNew) {
        this.parametersNew = parametersNew;
    }

    public String getDescriptionNew() {
        return descriptionNew;
    }

    public void setDescriptionNew(String descriptionNew) {
        this.descriptionNew = descriptionNew;
    }

    public String getStepsNew() {
        return stepsNew;
    }

    public void setStepsNew(String steps) {
        this.stepsNew = steps;
    }

    public String getExpectedResultNew() {
        return expectedResultNew;
    }

    public void setExpectedResultNew(String expectedResultNew) {
        this.expectedResultNew = expectedResultNew;
    }

    public String getAditionalInfoNew() {
        return aditionalInfoNew;
    }

    public void setAditionalInfoNew(String aditionalInfoNew) {
        this.aditionalInfoNew = aditionalInfoNew;
    }

    public String getMavenFrontNew() {
        return mavenFrontNew;
    }

    public void setMavenFrontNew(String mavenFrontNew) {
        this.mavenFrontNew = mavenFrontNew;
    }

    public String getMavenMembersNew() {
        return mavenMembersNew;
    }

    public void setMavenMembersNew(String mavenMembersNew) {
        this.mavenMembersNew = mavenMembersNew;
    }

    public TestcaseEntity getTestcaseNew() {
        return testcaseNew;
    }

    public void setTestcaseNew(TestcaseEntity testcaseNew) {
        this.testcaseNew = testcaseNew;
    }

    public boolean isVisibleEditTestCaseForm() {
        return visibleEditTestCaseForm;
    }

    public void setVisibleEditTestCaseForm(boolean visibleEditTestCaseForm) {
        this.visibleEditTestCaseForm = visibleEditTestCaseForm;
    }
}
