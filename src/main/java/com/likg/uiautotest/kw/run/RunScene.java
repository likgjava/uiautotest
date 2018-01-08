package com.likg.uiautotest.kw.run;

import com.likg.uiautotest.kw.constant.Constant;
import com.likg.uiautotest.kw.domain.CaseScene;
import com.likg.uiautotest.kw.domain.CaseStep;
import com.likg.uiautotest.kw.domain.PageElement;
import com.likg.uiautotest.kw.domain.StepData;
import com.likg.uiautotest.kw.domain.TestCase;
import com.likg.uiautotest.kw.util.DriverUtil;
import com.likg.uiautotest.kw.util.ExcelUtil;
import com.likg.uiautotest.kw.util.TestCaseUtil;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

/**
 * 运行场景用例
 */
public class RunScene {

    private CaseScene caseScene;

    private AndroidDriver<WebElement> driver;

    @BeforeTest
    public void beforeTest() throws IOException {
        //初始化驱动
        this.driver = DriverUtil.getDriver();
    }

    @Test
    public void start() throws IOException {
        System.out.println("start...");

        //加载用例场景
        this.caseScene = this.loadCaseScene();

        for (TestCase testCase : this.caseScene.getTestCaseList()) {
            TestCaseUtil.executeTestCase(driver, testCase, false);
        }
    }

    private CaseScene loadCaseScene() throws IOException {
        //获取用例列表数据
        List<String[]> dataList = ExcelUtil.getAllData(Constant.TEST_CASE_DATA_DIR + "scene.xlsx", "testCase", 1);

        //组装数据
        CaseScene caseScene = new CaseScene();
        for (String[] data : dataList) {
            TestCase testCase = new TestCase();
            testCase.setCaseCode(data[0]);
            testCase.setCaseDesc(data[1]);

            //加载用例步骤数据
            this.loadCaseStep(testCase);

            caseScene.getTestCaseList().add(testCase);
        }
        return caseScene;
    }

    private void loadCaseStep(TestCase testCase) throws IOException {
        //获取用例步骤数据
        String page = testCase.getPage();
        List<String[]> dataList = ExcelUtil.getAllData(Constant.TEST_CASE_DATA_DIR + page + ".xlsx", "caseStep", 1);

        //组装用例步骤数据
        for (String[] data : dataList) {
            if (testCase.getCaseCode().equals(data[0])) {
                CaseStep caseStep = new CaseStep();
                caseStep.setCaseCode(data[0]);
                caseStep.setStepCode(data[1]);
                caseStep.setStepDesc(data[2]);
                caseStep.setAction(data[3]);
                caseStep.setElementName(data[4]);

                //加载该步骤操作的元素信息
                PageElement pageElement = this.loadPageElement(caseStep, page);
                caseStep.setPageElement(pageElement);

                //加载该步骤的测试数据
                StepData stepData = this.loadStepData(testCase.getCaseCode(), caseStep.getStepCode());
                caseStep.setStepData(stepData);

                testCase.getCaseStepList().add(caseStep);
            }
        }
    }

    private StepData loadStepData(String caseCode, String stepCode) throws IOException {
        //获取步骤测试数据
        List<String[]> dataList = ExcelUtil.getAllData(Constant.TEST_CASE_DATA_DIR + "scene.xlsx", "stepData", 1);

        //组装数据
        for (String[] data : dataList) {
            if (caseCode.equals(data[0]) && stepCode.equals(data[1])) {
                StepData stepData = new StepData();
                stepData.setCaseCode(data[0]);
                stepData.setStepCode(data[1]);
                stepData.setInputData(data[3]);
                stepData.setExpectedResult(data[4]);
                return stepData;
            }
        }
        return null;
    }

    private PageElement loadPageElement(CaseStep caseStep, String page) throws IOException {
        //获取页面元素信息数据
        List<String[]> dataList = ExcelUtil.getAllData(Constant.TEST_CASE_DATA_DIR + page + ".xlsx", "location", 1);

        //组装数据
        for (String[] data : dataList) {
            if (caseStep.getElementName().equals(data[0])) {
                PageElement pageElement = new PageElement();
                pageElement.setElementName(data[0]);
                pageElement.setElementDesc(data[1]);
                pageElement.setLocationType(data[2]);
                pageElement.setLocationValue(data[3]);
                return pageElement;
            }
        }
        return null;
    }

    @AfterTest
    public void afterTest() {
        DriverUtil.quitDriver();
    }
}
