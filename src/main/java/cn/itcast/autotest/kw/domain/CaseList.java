package cn.itcast.autotest.kw.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 用例列表
 */
public class CaseList {

    //类型[suite:套件用例；scene:场景用例]
    //private String type;

    //用例列表
    private List<TestCase> testCaseList = new ArrayList<>();

//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }

    public List<TestCase> getTestCaseList() {
        return testCaseList;
    }

    public void setTestCaseList(List<TestCase> testCaseList) {
        this.testCaseList = testCaseList;
    }
}
