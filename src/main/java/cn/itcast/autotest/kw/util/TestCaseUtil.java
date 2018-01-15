package cn.itcast.autotest.kw.util;

import cn.itcast.autotest.kw.constant.Constant;
import cn.itcast.autotest.kw.domain.CaseList;
import cn.itcast.autotest.kw.domain.CaseStep;
import cn.itcast.autotest.kw.domain.PageElement;
import cn.itcast.autotest.kw.domain.StepData;
import cn.itcast.autotest.kw.domain.TestCase;
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
import java.util.Date;
import java.util.List;

/**
 * 用例工具类
 */
public class TestCaseUtil {

    /**
     * 查找元素
     * @param driver 驱动
     * @param caseStep 执行步骤
     * @return 该用例步骤操作的元素
     */
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
                element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
                System.out.println("toast element=" + element);
                break;
            }
            default: {
                throw new RuntimeException("unknown location type=" + pageElement.getLocationType());
            }
        }
        return element;
    }

    /**
     * 执行操作
     * @param element 元素
     * @param action 动作
     * @param inputData 输入数据
     * @param expectedResult 期望结果
     * @return 是否操作成功[true:操作成功; false:操作失败]
     */
    private static boolean executeOperation(WebElement element, String action, String inputData, String expectedResult) {
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

    /**
     * 执行用例
     * @param driver 驱动
     * @param testCase 用例
     * @throws IOException ex
     */
    public static void executeTestCase(AndroidDriver<WebElement> driver, TestCase testCase) throws IOException {
        boolean allStepSuccess = true;
        for (CaseStep caseStep : testCase.getCaseStepList()) {
            boolean isSuccess = executeCaseStep(driver, caseStep);

            //更新执行结果
            updateExecuteResult(testCase, caseStep, isSuccess);

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
            testCase.setExecuteResult(Constant.PASS);
            updateExecuteResultOfTestCase(testCase.getCaseCode(), Constant.PASS);
        } else {
            testCase.setExecuteResult(Constant.FAIL);
        }

        //后置处理
        if (StringUtils.isNotBlank(testCase.getPostProcess())) {
            if ("resetApp".equals(testCase.getPostProcess())) {
                System.out.println("resetApp...");
                driver.resetApp();
            }
        }
    }

    private static void saveScreenshot(AndroidDriver<WebElement> driver, TestCase testCase, CaseStep caseStep) throws IOException {
        File screenshot = driver.getScreenshotAs(OutputType.FILE);
        String fileName = new SimpleDateFormat("yyyyMMdd-HHmmss-SSS").format(new Date()) + ".jpg";
        File destFile = new File(Constant.TEST_CASE_DATA_DIR + "screenshot/" + fileName);
        FileUtils.copyFile(screenshot, destFile);

        updateScreenshotOfCaseStep(testCase.getCaseCode(), caseStep.getStepCode(), "screenshot/" + fileName);
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


    private static void updateExecuteResult(TestCase testCase, CaseStep caseStep, boolean isSuccess) throws IOException {
        String result = isSuccess ? Constant.PASS : Constant.FAIL;
        caseStep.setStepExecuteResult(result);
        updateExecuteResultOfCaseStep(testCase.getCaseCode(), caseStep.getStepCode(), result);

        if (!isSuccess) {
            updateExecuteResultOfTestCase(testCase.getCaseCode(), result);
        }
    }

    private static void updateExecuteResultOfTestCase(String caseCode, String result) throws IOException {
        int rowNum = ExcelUtil.getRowNum(Constant.SUITE_FILE_PATH, "testCase", 0, caseCode);

        ExcelUtil.setCellValue(Constant.SUITE_FILE_PATH, "testCase", rowNum, 3, result);
    }

    private static void updateExecuteResultOfCaseStep(String caseCode, String stepCode, String result) throws IOException {
        int rowNum = ExcelUtil.getRowNum(Constant.SUITE_FILE_PATH, "stepData", 0, caseCode, 1, stepCode);
        System.out.println("rowNum===" + rowNum);

        ExcelUtil.setCellValue(Constant.SUITE_FILE_PATH, "stepData", rowNum, 5, result);
    }

    private static void updateScreenshotOfCaseStep(String caseCode, String stepCode, String screenshotPath) throws IOException {
        int rowNum = ExcelUtil.getRowNum(Constant.SUITE_FILE_PATH, "stepData", 0, caseCode, 1, stepCode);

        ExcelUtil.setCellValueOfLink(Constant.SUITE_FILE_PATH, "stepData", rowNum, 6, screenshotPath);
    }

    public static CaseList loadCaseList() throws IOException {
        //获取用例列表数据
        List<String[]> dataList = ExcelUtil.getAllData(Constant.SUITE_FILE_PATH, "testCase", 1);

        //组装数据
        CaseList caseList = new CaseList();
        for (String[] data : dataList) {
            TestCase testCase = new TestCase();
            testCase.setCaseCode(data[0]);
            testCase.setCaseDesc(data[1]);
            testCase.setPostProcess(data[2]);

            //加载用例步骤数据
            loadCaseStep(testCase);

            caseList.getTestCaseList().add(testCase);
        }
        return caseList;
    }

    private static void loadCaseStep(TestCase testCase) throws IOException {
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
                PageElement pageElement = loadPageElement(caseStep, page);
                caseStep.setPageElement(pageElement);

                //加载该步骤的测试数据
                StepData stepData = loadStepData(testCase.getCaseCode(), caseStep.getStepCode());
                caseStep.setStepData(stepData);

                testCase.getCaseStepList().add(caseStep);
            }
        }
    }

    private static StepData loadStepData(String caseCode, String stepCode) throws IOException {
        //获取步骤测试数据
        List<String[]> dataList = ExcelUtil.getAllData(Constant.SUITE_FILE_PATH, "stepData", 1);

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

    private static PageElement loadPageElement(CaseStep caseStep, String page) throws IOException {
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
}