package com.likg.uiautotest.kw.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 用例套件
 */
public class CaseSuite {

    private List<TestCase> testCaseList = new ArrayList<>();


    public List<TestCase> getTestCaseList() {
        return testCaseList;
    }

    public void setTestCaseList(List<TestCase> testCaseList) {
        this.testCaseList = testCaseList;
    }
}
