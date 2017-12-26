package com.likg.uiautotest.datadriven.base;

public class TestData {
    // 数据编号
    private String dataCode;

    // 步骤编号
    private String stepCode;

    // 测试数据
    private String data;

    // 预期结果
    private String expectedResult;

    // 实际结果
    private String actualResult;

    // 测试结果


    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }

    public String getStepCode() {
        return stepCode;
    }

    public void setStepCode(String stepCode) {
        this.stepCode = stepCode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    public String getActualResult() {
        return actualResult;
    }

    public void setActualResult(String actualResult) {
        this.actualResult = actualResult;
    }
}
