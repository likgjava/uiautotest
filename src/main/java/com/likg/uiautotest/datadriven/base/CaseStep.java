package com.likg.uiautotest.datadriven.base;

public class CaseStep {
    // 步骤编号
    private String stepCode;

    // 用例编号
    private String caseCode;

    // 步骤描述
    private String stepDesc;

    // 执行动作
    private String action;

    // 输入数据
    private String locationFileName;

    // 执行结果
    private String executeResult;

    // 元素名称
    private String elementName;

    //该步骤操作的页面元素
    private PageElement pageElement;

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

    public String getLocationFileName() {
        return locationFileName;
    }

    public void setLocationFileName(String locationFileName) {
        this.locationFileName = locationFileName;
    }

    public String getExecuteResult() {
        return executeResult;
    }

    public void setExecuteResult(String executeResult) {
        this.executeResult = executeResult;
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
}
