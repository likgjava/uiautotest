package com.likg.uiautotest.datadriven.login;

import com.likg.uiautotest.datadriven.base.CaseStep;
import com.likg.uiautotest.datadriven.base.PageElement;
import com.likg.uiautotest.datadriven.base.TestCase;
import com.likg.uiautotest.datadriven.base.TestData;
import com.likg.uiautotest.datadriven.util.ExcelUtil;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.lang3.StringUtils;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LoginTest {

    private String baseDir;
    private List<TestCase> testCaseList = new ArrayList<TestCase>();
    private List<PageElement> pageElementList = new ArrayList<PageElement>();
    private Map<String, TestData> testDataMap = new HashMap<String, TestData>();

    private AndroidDriver<AndroidElement> driver;

    @BeforeTest
    public void beforeTest() throws IOException {
        this.baseDir = System.getProperty("user.dir") + "/data/dataDriven/";
        System.out.println("baseDir==="+this.baseDir);

        //加载页面元素
        this.loadPageElement("loginPage");

        //加载用例
        this.loadTestCase("loginPage");

        //加载用例步骤
        this.loadCaseStep("loginPage");

        //加载测试数据
        this.loadTestData("loginPage");

        //初始化驱动
        this.initDriver();

    }

    @Test
    public void  tt() throws IOException {
        ExcelUtil.setCellValue(baseDir  + "loginPageTestCase.xlsx", "caseStep", 1, 6, "aaa");





    }

    private void initDriver() throws MalformedURLException {
        System.out.println("beforeTest...888.");

        File apk = new File("D://apk/CSDN.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "mi");
        //capabilities.setCapability("app", apk.getAbsolutePath());

        capabilities.setCapability("appPackage", "net.csdn.csdnplus");
        capabilities.setCapability("appActivity", "net.csdn.csdnplus.activity.SplashActivity");

        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Uiautomator2");

        URL url = new URL("http://127.0.0.1:4723/wd/hub/");
        driver = new AndroidDriver<AndroidElement>(url, capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void start() throws IOException {
        System.out.println("start...");
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

        ExcelUtil.setCellValue(baseDir + page + "TestCase.xlsx", "caseStep", rowNum, 6, result);
    }

    private boolean executeCaseStep(CaseStep caseStep) {
        System.out.println("executeCaseStep start execute case step="+caseStep.getStepDesc()+" ...");
        boolean isSuccess = false;

        try {
            //获取测试数据
            TestData testData = this.testDataMap.get(caseStep.getStepCode());

            //查找到元素
            WebElement element = this.findElement(caseStep, testData.getData());

            //执行操作
            isSuccess = this.executeOperation(element, caseStep.getAction(), testData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    private boolean executeOperation(WebElement element, String action, TestData testData) {
        switch (action) {
            case "sendKey" : {
                element.sendKeys(testData.getData());
                return true;
            }
            case "click" : {
                element.click();
                return true;
            }
            case "getText" : {
                String text = element.getText();
                return text != null && text.equals(testData.getExpectedResult());
            }
            case "getToast" : {
                return element != null;
            }
            default:{
                throw new RuntimeException("unknown action="+action);
            }
        }
    }

    private WebElement findElement(CaseStep caseStep, String toast) {
        WebElement element;
        PageElement pageElement = caseStep.getPageElement();
        switch (pageElement.getLocationType()) {
            case "id" : {
                element = driver.findElementById(pageElement.getLocationValue());
                break;
            }
            case "name" : {
                element = driver.findElementByName(pageElement.getLocationValue());
                break;
            }
            case "toast" : {
                WebDriverWait wait = new WebDriverWait(driver, 5);
                String xpath = String.format(".//*[contains(@text,'%s')]", toast);
                element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
                break;
            }
            default:{
                throw new RuntimeException("unknown location type="+pageElement.getLocationType());
            }
        }
        return element;
    }

    private void loadTestData(String page) throws IOException {
        List<Object[]> dataList = ExcelUtil.getAllData(baseDir + page + "Data.xlsx", "data", 1);

        for (Object[] data : dataList) {
            TestData testData = new TestData();
            testData.setDataCode(data[0].toString());
            testData.setStepCode(data[1].toString());
            testData.setData(data[2] == null ? null : data[2].toString());
            testData.setExpectedResult(data[3].toString());
            this.testDataMap.put(testData.getStepCode(), testData);
        }
    }

    private void loadPageElement(String page) throws IOException {
        List<Object[]> dataList = ExcelUtil.getAllData(baseDir + page + ".xlsx", "location", 1);

        for (Object[] data : dataList) {
            PageElement pageElement = new PageElement();
            pageElement.setElementName(data[0].toString());
            pageElement.setElementDesc(data[1].toString());
            pageElement.setLocationType(data[2].toString());
            pageElement.setLocationValue(data[3] == null ? null : data[3].toString());
            this.pageElementList.add(pageElement);
        }
    }

    private void loadCaseStep(String page) throws IOException {
        List<Object[]> dataList = ExcelUtil.getAllData(baseDir + page + "TestCase.xlsx", "caseStep", 1);

        for (Object[] data : dataList) {
            CaseStep caseStep = new CaseStep();
            caseStep.setStepCode(data[0].toString());
            caseStep.setCaseCode(data[1].toString());
            caseStep.setStepDesc(data[2].toString());
            caseStep.setAction(data[3].toString());
            caseStep.setLocationFileName(data[4].toString());
            caseStep.setElementName(data[5].toString());

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
        List<Object[]> dataList = ExcelUtil.getAllData(baseDir + page + "TestCase.xlsx", "testCase", 1);

        for (Object[] data : dataList) {
            TestCase testCase = new TestCase();
            testCase.setCaseCode(data[0].toString());
            testCase.setCaseDesc(data[1].toString());
            testCaseList.add(testCase);
        }
    }

    @AfterTest
    public void afterTest() {
        driver.quit();
    }


}
