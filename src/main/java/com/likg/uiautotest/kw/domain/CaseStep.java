package com.likg.uiautotest.kw.domain;

/**
 * 用例步骤
 */
public class CaseStep {
    // 用例编号
    private String caseCode;

    // 步骤编号
    private String stepCode;

    // 步骤描述
    private String stepDesc;

    // 执行动作
    private String action;

    // 元素名称
    private String elementName;

    //该步骤操作的页面元素
    private PageElement pageElement;

    // 步骤数据
    private StepData stepData;

    // 步骤执行结果
    private String stepExecuteResult;

    public String getStepCode() {
        return stepCode;
    }

    public void setStepCode(String stepCode) {
        this.stepCode = stepCode;
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode;
    }

    public String getStepDesc() {
        return stepDesc;
    }

    public void setStepDesc(String stepDesc) {
        this.stepDesc = stepDesc;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public PageElement getPageElement() {
        return pageElement;
    }

    public void setPageElement(PageElement pageElement) {
        this.pageElement = pageElement;
    }

    public StepData getStepData() {
        return stepData;
    }

    public void setStepData(StepData stepData) {
        this.stepData = stepData;
    }

    public String getStepExecuteResult() {
        return stepExecuteResult;
    }

    public void setStepExecuteResult(String stepExecuteResult) {
        this.stepExecuteResult = stepExecuteResult;
    }
}
