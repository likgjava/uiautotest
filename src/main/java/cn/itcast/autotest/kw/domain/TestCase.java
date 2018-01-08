package cn.itcast.autotest.kw.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 用例
 */
public class TestCase {
    // 用例编号
    private String caseCode;

    // 用例描述
    private String caseDesc;

    // 后置处理，用例执行完之后需要做的操作
    private String postProcess;

    // 执行结果
    private String executeResult;

    // 执行步骤
    private List<CaseStep> caseStepList = new ArrayList<>();

    // 用例所属页面
    private String page;

    //根据用例编号计算用例所属页面
    public String getPage() {
        this.page = this.caseCode.split("_")[0];
        return page;
    }

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

    public String getPostProcess() {
        return postProcess;
    }

    public void setPostProcess(String postProcess) {
        this.postProcess = postProcess;
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
