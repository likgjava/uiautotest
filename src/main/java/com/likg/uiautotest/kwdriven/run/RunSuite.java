package com.likg.uiautotest.kwdriven.run;

import com.likg.uiautotest.kwdriven.base.CaseStep;
import com.likg.uiautotest.kwdriven.base.PageElement;
import com.likg.uiautotest.kwdriven.base.TestCase;
import com.likg.uiautotest.kwdriven.constant.Constant;
import com.likg.uiautotest.kwdriven.util.DriverUtil;
import com.likg.uiautotest.kwdriven.util.ExcelUtil;
import com.likg.uiautotest.kwdriven.util.TestCaseUtil;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 运行多个组合用例
 */
public class RunSuite {

    private List<TestCase> testCaseList = new ArrayList<>();

    private AndroidDriver<WebElement> driver;

    @BeforeTest
    public void beforeTest() throws IOException {
        //初始化驱动
        this.driver = DriverUtil.getDriver();
    }

    @Test
    public void start() throws IOException {
        System.out.println("start...");

        //加载suite中的用例
        this.loadSuiteCase();

        for (TestCase testCase : this.testCaseList) {
            TestCaseUtil.executeTestCase(driver, testCase, true);
        }
    }

    private void loadSuiteCase() throws IOException {
        List<String[]> dataList = ExcelUtil.getAllData(Constant.TEST_CASE_DATA_DIR + "suite.xlsx", "testCase", 1);

        for (String[] data : dataList) {
            TestCase testCase = new TestCase();
            testCase.setCaseCode(data[0]);
            testCase.setCaseDesc(data[1]);
            testCase.setPage(data[0].split("_")[0]);
            testCaseList.add(testCase);

            //加载用例步骤
            List<CaseStep> caseStepList = this.loadCaseStep(testCase.getPage(), testCase.getCaseCode());
            testCase.setCaseStepList(caseStepList);
        }

    }

    private List<CaseStep> loadCaseStep(String page, String caseCode) throws IOException {
        //获取用例步骤
        List<String[]> dataList = ExcelUtil.getAllData(Constant.TEST_CASE_DATA_DIR + page + ".xlsx", "caseStep", 1);

        //获取页面元素
        List<String[]> elementList = ExcelUtil.getAllData(Constant.TEST_CASE_DATA_DIR + page + ".xlsx", "location", 1);

        List<CaseStep> caseStepList = new ArrayList<>();
        for (String[] data : dataList) {
            if (caseCode.equals(data[1])) {
                CaseStep caseStep = new CaseStep();
                caseStep.setStepCode(data[0]);
                caseStep.setCaseCode(data[1]);
                caseStep.setStepDesc(data[2]);
                caseStep.setAction(data[3]);
                caseStep.setElementName(data[4]);
                caseStep.setInputData(data[5]);
                caseStep.setExpectedResult(data[6]);
                caseStepList.add(caseStep);

                for (String[] element : elementList) {
                    if (caseStep.getElementName().equals(element[0])) {
                        PageElement pageElement = new PageElement();
                        pageElement.setElementName(element[0]);
                        pageElement.setElementDesc(element[1]);
                        pageElement.setLocationType(element[2]);
                        pageElement.setLocationValue(element[3]);
                        caseStep.setPageElement(pageElement);
                        break;
                    }
                }
            }
        }
        return caseStepList;
    }

    @AfterTest
    public void afterTest() {
        DriverUtil.quitDriver();
    }
}
