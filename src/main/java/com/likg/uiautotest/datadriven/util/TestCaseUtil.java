package com.likg.uiautotest.datadriven.util;

import com.likg.uiautotest.datadriven.base.CaseStep;
import com.likg.uiautotest.datadriven.base.PageElement;
import com.likg.uiautotest.datadriven.base.TestCase;
import com.likg.uiautotest.datadriven.constant.Constant;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

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
                WebDriverWait wait = new WebDriverWait(driver, 5);
                String xpath = String.format(".//*[contains(@text,'%s')]", caseStep.getExpectedResult());
                element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
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

            //执行失败
            if (!isSuccess) {
                allStepSuccess = false;
                break;
            }
        }

        if (allStepSuccess) {
            updateExecuteResultOfTestCase(testCase.getPage(), testCase.getCaseCode(), "PASS", isSuite);
        }
    }


    private static boolean executeCaseStep(AndroidDriver<WebElement> driver, CaseStep caseStep) {
        System.out.println("executeCaseStep start execute case step=" + caseStep.getStepDesc() + " ...");
        boolean isSuccess = false;

        try {
            //查找元素
            WebElement element = TestCaseUtil.findElement(driver, caseStep);

            //执行操作
            isSuccess = TestCaseUtil.executeOperation(element, caseStep.getAction(), caseStep.getInputData(), caseStep.getExpectedResult());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSuccess;
    }


    private static void updateExecuteResult(TestCase testCase, CaseStep caseStep, boolean isSuccess, boolean isSuite) throws IOException {
        String result = isSuccess ? "PASS" : "FAIL";
        updateExecuteResultOfCaseStep(testCase.getPage(), caseStep.getStepCode(), result);

        if (!isSuccess) {
            updateExecuteResultOfTestCase(testCase.getPage(), testCase.getCaseCode(), result, isSuite);
        }
    }

    private static void updateExecuteResultOfTestCase(String page, String caseCode, String result, boolean isSuite) throws IOException {
        String filePath;
        if (isSuite) {
            filePath = Constant.TEST_CASE_DATA_DIR + page + "TestCase.xlsx";
        } else {
            filePath = Constant.TEST_CASE_DATA_DIR + "suite.xlsx";
        }
        int rowNum = ExcelUtil.getRowNum(filePath, "testCase", 0, caseCode);

        ExcelUtil.setCellValue(filePath, "testCase", rowNum, 2, result);
    }

    private static void updateExecuteResultOfCaseStep(String page, String stepCode, String result) throws IOException {
        int rowNum = ExcelUtil.getRowNum(Constant.TEST_CASE_DATA_DIR + page + "TestCase.xlsx", "caseStep", 0, stepCode);

        ExcelUtil.setCellValue(Constant.TEST_CASE_DATA_DIR + page + "TestCase.xlsx", "caseStep", rowNum, 7, result);
    }

}
