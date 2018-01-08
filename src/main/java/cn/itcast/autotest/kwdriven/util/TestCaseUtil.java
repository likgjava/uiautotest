package cn.itcast.autotest.kwdriven.util;

import cn.itcast.autotest.kwdriven.constant.Constant;
import cn.itcast.autotest.kwdriven.base.CaseStep;
import cn.itcast.autotest.kwdriven.base.PageElement;
import cn.itcast.autotest.kwdriven.base.TestCase;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
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
                String xpath = String.format(".//*[contains(@text,'%s')]", caseStep.getExpectedResult());
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
                saveScreenshot(driver, testCase, caseStep);

                allStepSuccess = false;
                break;
            }
        }

        if (allStepSuccess) {
            updateExecuteResultOfTestCase(testCase.getPage(), testCase.getCaseCode(), "PASS", isSuite);
        }
    }

    private static void saveScreenshot(AndroidDriver<WebElement> driver, TestCase testCase, CaseStep caseStep) throws IOException {

        File screenshot = driver.getScreenshotAs(OutputType.FILE);
        String fileName = new SimpleDateFormat("yyyyMMdd-HHmmss-SSS").format(new Date()) + ".jpg";
        File destFile = new File(Constant.TEST_CASE_DATA_DIR + "screenshot/" + fileName);
        FileUtils.copyFile(screenshot, destFile);


        updateScreenshotOfCaseStep(testCase.getPage(), caseStep.getStepCode(), "screenshot/" + fileName);
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
            filePath = Constant.TEST_CASE_DATA_DIR + page + ".xlsx";
        } else {
            filePath = Constant.TEST_CASE_DATA_DIR + "suite.xlsx";
        }
        int rowNum = ExcelUtil.getRowNum(filePath, "testCase", 0, caseCode);

        ExcelUtil.setCellValue(filePath, "testCase", rowNum, 2, result);
    }

    private static void updateExecuteResultOfCaseStep(String page, String stepCode, String result) throws IOException {
        int rowNum = ExcelUtil.getRowNum(Constant.TEST_CASE_DATA_DIR + page + ".xlsx", "caseStep", 0, stepCode);

        ExcelUtil.setCellValue(Constant.TEST_CASE_DATA_DIR + page + ".xlsx", "caseStep", rowNum, 7, result);
    }

    private static void updateScreenshotOfCaseStep(String page, String stepCode, String screenshotPath) throws IOException {
        int rowNum = ExcelUtil.getRowNum(Constant.TEST_CASE_DATA_DIR + page + ".xlsx", "caseStep", 0, stepCode);

        ExcelUtil.setCellValueOfLink(Constant.TEST_CASE_DATA_DIR + page + ".xlsx", "caseStep", rowNum, 8, screenshotPath);
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
