package cn.itcast.autotest.kw.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 用例步骤测试数据
 */
public class StepData {
    // 用例编号
    private String caseCode;

    // 步骤编号
    private String stepCode;

    // 输入数据
    private String inputData;

    // 预期结果
    private String expectedResult;

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode;
    }


    public String getStepCode() {
        return stepCode;
    }

    public void setStepCode(String stepCode) {
        this.stepCode = stepCode;
    }

    public String getInputData() {
        return inputData;
    }

    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }


}
