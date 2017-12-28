package com.likg.uiautotest.datadriven.login;

import com.likg.uiautotest.datadriven.base.CaseStep;
import com.likg.uiautotest.datadriven.base.PageElement;
import com.likg.uiautotest.datadriven.base.TestCase;
import com.likg.uiautotest.datadriven.util.DriverUtil;
import com.likg.uiautotest.datadriven.util.ExcelUtil;
import com.likg.uiautotest.datadriven.util.TestCaseUtil;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RunTest {

    private String page = "loginPage";

    private String baseDir;
    private List<TestCase> testCaseList = new ArrayList<TestCase>();
    private List<PageElement> pageElementList = new ArrayList<PageElement>();

    private AndroidDriver<WebElement> driver;

    @BeforeTest
    public void beforeTest() throws IOException {
        this.baseDir = System.getProperty("user.dir") + "/data/dataDriven/";
        System.out.println("baseDir==="+this.baseDir);

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
            this.executeTestCase(testCase);
        }

    }

    private void executeTestCase(TestCase testCase) throws IOException {

        boolean allStepSuccess = true;
        for (CaseStep caseStep : testCase.getCaseStepList()) {
            boolean isSuccess = this.executeCaseStep(caseStep);

            //更新执行结果
            this.updateExecuteResult(testCase, caseStep, isSuccess);

            //执行失败
            if (!isSuccess) {
                allStepSuccess = false;
                break;
            }
        }

        if (allStepSuccess) {
            this.updateExecuteResultOfTestCase("loginPage", testCase.getCaseCode(), "PASS");
        }

    }

    private void updateExecuteResult(TestCase testCase, CaseStep caseStep, boolean isSuccess) throws IOException {
        String result = isSuccess ? "PASS" : "FAIL";
        this.updateExecuteResultOfCaseStep("loginPage", caseStep.getStepCode(), result);

        if (!isSuccess) {
            this.updateExecuteResultOfTestCase("loginPage", testCase.getCaseCode(), result);
        }
    }

    private void updateExecuteResultOfTestCase(String page, String caseCode, String result) throws IOException {
        int rowNum = ExcelUtil.getRowNum(baseDir + page + "TestCase.xlsx", "testCase", 0, caseCode);

        ExcelUtil.setCellValue(baseDir + page + "TestCase.xlsx", "testCase", rowNum, 2, result);

    }

    private void updateExecuteResultOfCaseStep(String page, String stepCode, String result) throws IOException {
        int rowNum = ExcelUtil.getRowNum(baseDir + page + "TestCase.xlsx", "caseStep", 0, stepCode);

        ExcelUtil.setCellValue(baseDir + page + "TestCase.xlsx", "caseStep", rowNum, 7, result);
    }

    private boolean executeCaseStep(CaseStep caseStep) {
        System.out.println("executeCaseStep start execute case step="+caseStep.getStepDesc()+" ...");
        boolean isSuccess = false;

        try {
            //获取测试数据
            //TestData testData = this.testDataMap.get(caseStep.getStepCode());

            //查找元素
            WebElement element = TestCaseUtil.findElement(driver, caseStep);

            //执行操作
            isSuccess = TestCaseUtil.executeOperation(element, caseStep.getAction(), caseStep.getInputData(), caseStep.getExpectedResult());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSuccess;
    }





    private void loadPageElement(String page) throws IOException {
        List<String[]> dataList = ExcelUtil.getAllData(baseDir + page + "Element.xlsx", "location", 1);

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
        List<String[]> dataList = ExcelUtil.getAllData(baseDir + page + "TestCase.xlsx", "caseStep", 1);

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
        List<String[]> dataList = ExcelUtil.getAllData(baseDir + page + "TestCase.xlsx", "testCase", 1);

        for (String[] data : dataList) {
            TestCase testCase = new TestCase();
            testCase.setCaseCode(data[0]);
            testCase.setCaseDesc(data[1]);
            testCaseList.add(testCase);
        }
    }

    @AfterTest
    public void afterTest() {
        DriverUtil.quitDriver();
    }


}
