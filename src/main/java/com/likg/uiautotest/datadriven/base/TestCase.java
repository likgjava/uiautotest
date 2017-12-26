package com.likg.uiautotest.datadriven.base;

import com.likg.uiautotest.datadriven.base.CaseStep;

import java.util.ArrayList;
import java.util.List;

public class TestCase {
    // 用例编号
    private String caseCode;

    // 用例描述
    private String caseDesc;

    // 执行结果
    private String executeResult;

    // 执行步骤
    private List<CaseStep> caseStepList = new ArrayList<CaseStep>();

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode;
    }

    public String getCaseDesc() {
        return caseDesc;
    }

    public void setCaseDesc(String caseDesc) {
        this.caseDesc = caseDesc;
    }

    public String getExecuteResult() {
        return executeResult;
    }

    public void setExecuteResult(String executeResult) {
        this.executeResult = executeResult;
    }

    public List<CaseStep> getCaseStepList() {
        return caseStepList;
    }

    public void setCaseStepList(List<CaseStep> caseStepList) {
        this.caseStepList = caseStepList;
    }
}
