package com.likg.uiautotest.kw.util;

import com.likg.uiautotest.kw.domain.CaseStep;
import com.likg.uiautotest.kw.domain.PageElement;
import com.likg.uiautotest.kw.domain.TestCase;
import com.likg.uiautotest.kw.constant.Constant;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用例工具类
 */
public class TestCaseUtil {

    public static WebElement findElement(AndroidDriver<WebElement> driver, CaseStep caseStep) {
        WebElement element;
        PageElement pageElement = caseStep.getPageElement();
        switch (pageElement.getLocationType()) {
            case "id": {
                element = driver.findElementById(pageElement.getLocationValue());
                break;
            }
            case "name": {
                element = driver.findElementByName(pageElement.getLocationValue());
                break;
            }
            case "xpath": {
                element = driver.findElementByXPath(pageElement.getLocationValue());
                break;
            }
            case "toast": {
                System.out.println("toast 1111111111111111111111");
                WebDriverWait wait = new WebDriverWait(driver, 15);
                String xpath = String.format(".//*[contains(@text,'%s')]", caseStep.getStepData().getExpectedResult());
                System.out.println("toast 2222222222222222222 xpath=" + xpath);
                element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
                System.out.println("toast 33333333333333333333333333");
                break;
            }
            default: {
                throw new RuntimeException("unknown location type=" + pageElement.getLocationType());
            }
        }
        return element;
    }

    public static boolean executeOperation(WebElement element, String action, String inputData, String expectedResult) {
        switch (action) {
            case "sendKey": {
                element.sendKeys(inputData);
                return true;
            }
            case "click": {
                element.click();
                return true;
            }
            case "getText": {
                String text = element.getText();
                System.out.println("expectedResult=" + expectedResult + " text=" + text);
                return expectedResult.equals(text);
            }
            case "getToast": {
                return element != null;
            }
            default: {
                throw new RuntimeException("unknown action=" + action);
            }
        }
    }

    public static void executeTestCase(AndroidDriver<WebElement> driver, TestCase testCase, boolean isSuite) throws IOException {
        boolean allStepSuccess = true;
        for (CaseStep caseStep : testCase.getCaseStepList()) {
            boolean isSuccess = executeCaseStep(driver, caseStep);

            //更新执行结果
            updateExecuteResult(testCase, caseStep, isSuccess, isSuite);

            //保存步骤截图
            //saveScreenshot(driver, testCase, caseStep);

            //执行失败
            if (!isSuccess) {
                saveScreenshot(driver, testCase, caseStep, isSuite);

                allStepSuccess = false;
                break;
            }
        }

        if (allStepSuccess) {
            testCase.setExecuteResult("PASS");
            updateExecuteResultOfTestCase(testCase.getPage(), testCase.getCaseCode(), "PASS", isSuite);
        }

        //后置处理
        if (StringUtils.isNotBlank(testCase.getPostProcess())) {
            if ("resetApp".equals(testCase.getPostProcess())) {
                System.out.println("resetApp...");
                driver.resetApp();
            }
        }
    }

    private static void saveScreenshot(AndroidDriver<WebElement> driver, TestCase testCase, CaseStep caseStep, boolean isSuite) throws IOException {

        File screenshot = driver.getScreenshotAs(OutputType.FILE);
        String fileName = new SimpleDateFormat("yyyyMMdd-HHmmss-SSS").format(new Date()) + ".jpg";
        File destFile = new File(Constant.TEST_CASE_DATA_DIR + "screenshot/" + fileName);
        FileUtils.copyFile(screenshot, destFile);


        updateScreenshotOfCaseStep(testCase.getCaseCode(), caseStep.getStepCode(), "screenshot/" + fileName, isSuite);
    }


    private static boolean executeCaseStep(AndroidDriver<WebElement> driver, CaseStep caseStep) {
        System.out.println("executeCaseStep start execute case step=" + caseStep.getStepDesc() + " ...");
        boolean isSuccess = false;

        try {
            //查找元素
            WebElement element = TestCaseUtil.findElement(driver, caseStep);

            //执行操作
            isSuccess = TestCaseUtil.executeOperation(element, caseStep.getAction(), caseStep.getStepData().getInputData(),
                    caseStep.getStepData().getExpectedResult());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSuccess;
    }


    private static void updateExecuteResult(TestCase testCase, CaseStep caseStep, boolean isSuccess, boolean isSuite) throws IOException {
        String result = isSuccess ? "PASS" : "FAIL";
        caseStep.setStepExecuteResult(result);
        updateExecuteResultOfCaseStep(testCase.getCaseCode(), caseStep.getStepCode(), result, isSuite);

        if (!isSuccess) {
            updateExecuteResultOfTestCase(testCase.getPage(), testCase.getCaseCode(), result, isSuite);
        }
    }

    private static void updateExecuteResultOfTestCase(String page, String caseCode, String result, boolean isSuite) throws IOException {
        String filePath;
        if (isSuite) {
            filePath = Constant.TEST_CASE_DATA_DIR + "suite.xlsx";
        } else {
            filePath = Constant.TEST_CASE_DATA_DIR + "scene.xlsx";
        }
        int rowNum = ExcelUtil.getRowNum(filePath, "testCase", 0, caseCode);

        ExcelUtil.setCellValue(filePath, "testCase", rowNum, 3, result);
    }

    private static void updateExecuteResultOfCaseStep(String caseCode, String stepCode, String result, boolean isSuite) throws IOException {
        String filePath;
        if (isSuite) {
            filePath = Constant.TEST_CASE_DATA_DIR + "suite.xlsx";
        } else {
            filePath = Constant.TEST_CASE_DATA_DIR + "scene.xlsx";
        }
        int rowNum = ExcelUtil.getRowNum(filePath, "stepData", 0, caseCode, 1, stepCode);
        System.out.println("rowNum==="+rowNum);

        ExcelUtil.setCellValue(filePath, "stepData", rowNum, 5, result);
    }

    private static void updateScreenshotOfCaseStep(String caseCode, String stepCode, String screenshotPath, boolean isSuite) throws IOException {
        String filePath;
        if (isSuite) {
            filePath = Constant.TEST_CASE_DATA_DIR + "suite.xlsx";
        } else {
            filePath = Constant.TEST_CASE_DATA_DIR + "scene.xlsx";
        }
        int rowNum = ExcelUtil.getRowNum(filePath, "stepData", 0, caseCode, 1, stepCode);

        ExcelUtil.setCellValueOfLink(filePath, "stepData", rowNum, 6, screenshotPath);
    }

    public static List<PageElement> loadPageElement(String page) throws IOException {
        List<PageElement> pageElementList = new ArrayList<>();
        List<String[]> dataList = ExcelUtil.getAllData(Constant.TEST_CASE_DATA_DIR + page + ".xlsx", "location", 1);
        for (String[] data : dataList) {
            PageElement pageElement = new PageElement();
            pageElement.setElementName(data[0]);
            pageElement.setElementDesc(data[1]);
            pageElement.setLocationType(data[2]);
            pageElement.setLocationValue(data[3]);
            pageElementList.add(pageElement);
        }
        return pageElementList;
    }

}
