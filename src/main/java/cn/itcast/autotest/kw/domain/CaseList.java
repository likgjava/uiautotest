package cn.itcast.autotest.kw.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 用例列表
 */
public class CaseList {

    //用例列表
    private List<TestCase> testCaseList = new ArrayList<>();

    public List<TestCase> getTestCaseList() {
        return testCaseList;
    }

    public void setTestCaseList(List<TestCase> testCaseList) {
        this.testCaseList = testCaseList;
    }
}