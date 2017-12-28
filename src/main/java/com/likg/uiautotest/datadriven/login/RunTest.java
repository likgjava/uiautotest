package com.likg.uiautotest.datadriven.login;

import com.likg.uiautotest.datadriven.base.CaseStep;
import com.likg.uiautotest.datadriven.base.PageElement;
import com.likg.uiautotest.datadriven.base.TestCase;
import com.likg.uiautotest.datadriven.constant.Constant;
import com.likg.uiautotest.datadriven.util.DriverUtil;
import com.likg.uiautotest.datadriven.util.ExcelUtil;
import com.likg.uiautotest.datadriven.util.TestCaseUtil;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RunTest {

    private String page = "loginPage";

    private List<TestCase> testCaseList = new ArrayList<>();
    private List<PageElement> pageElementList = new ArrayList<>();

    private AndroidDriver<WebElement> driver;

    @BeforeTest
    public void beforeTest() throws IOException {
        //初始化驱动
        this.driver = DriverUtil.getDriver();
    }

    @Test
    public void start() throws IOException {
        System.out.println("start...");

        //加载页面元素
        this.loadPageElement(page);

        //加载用例
        this.loadTestCase(page);

        //加载用例步骤
        this.loadCaseStep(page);

        for (TestCase testCase : this.testCaseList) {
            TestCaseUtil.executeTestCase(driver, testCase, false);
        }
    }

    private void loadPageElement(String page) throws IOException {
        List<String[]> dataList = ExcelUtil.getAllData(Constant.TEST_CASE_DATA_DIR + page + "Element.xlsx", "location", 1);

        for (String[] data : dataList) {
            PageElement pageElement = new PageElement();
            pageElement.setElementName(data[0]);
            pageElement.setElementDesc(data[1]);
            pageElement.setLocationType(data[2]);
            pageElement.setLocationValue(data[3]);
            this.pageElementList.add(pageElement);
        }
    }

    private void loadCaseStep(String page) throws IOException {
        List<String[]> dataList = ExcelUtil.getAllData(Constant.TEST_CASE_DATA_DIR + page + "TestCase.xlsx", "caseStep", 1);

        for (String[] data : dataList) {
            CaseStep caseStep = new CaseStep();
            caseStep.setStepCode(data[0]);
            caseStep.setCaseCode(data[1]);
            caseStep.setStepDesc(data[2]);
            caseStep.setAction(data[3]);
            caseStep.setElementName(data[4]);
            caseStep.setInputData(data[5]);
            caseStep.setExpectedResult(data[6]);

            for (PageElement pageElement : this.pageElementList) {
                if (pageElement.getElementName().equals(caseStep.getElementName())) {
                    caseStep.setPageElement(pageElement);
                    break;
                }
            }

            for (TestCase testCase : this.testCaseList) {
                if (testCase.getCaseCode().equals(caseStep.getCaseCode())) {
                    testCase.getCaseStepList().add(caseStep);
                    break;
                }
            }
        }
    }

    private void loadTestCase(String page) throws IOException {
        List<String[]> dataList = ExcelUtil.getAllData(Constant.TEST_CASE_DATA_DIR + page + "TestCase.xlsx", "testCase", 1);

        for (String[] data : dataList) {
            TestCase testCase = new TestCase();
            testCase.setCaseCode(data[0]);
            testCase.setCaseDesc(data[1]);
            testCase.setPage(page);
            testCaseList.add(testCase);
        }
    }

    @AfterTest
    public void afterTest() {
        DriverUtil.quitDriver();
    }


}
