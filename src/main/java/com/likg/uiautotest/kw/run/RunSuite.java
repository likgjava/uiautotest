package com.likg.uiautotest.kw.run;

import com.likg.uiautotest.kw.constant.Constant;
import com.likg.uiautotest.kw.domain.CaseStep;
import com.likg.uiautotest.kw.domain.CaseSuite;
import com.likg.uiautotest.kw.domain.PageElement;
import com.likg.uiautotest.kw.domain.Report;
import com.likg.uiautotest.kw.domain.StepData;
import com.likg.uiautotest.kw.domain.TestCase;
import com.likg.uiautotest.kw.util.DriverUtil;
import com.likg.uiautotest.kw.util.ExcelUtil;
import com.likg.uiautotest.kw.util.TestCaseUtil;
import io.appium.java_client.android.AndroidDriver;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * 运行用例套件
 */
public class RunSuite {

    private CaseSuite caseSuite;

    private Report report = new Report();

    private AndroidDriver<WebElement> driver;

    @BeforeTest
    public void beforeTest() throws IOException {
        //初始化驱动
        this.driver = DriverUtil.getDriver();
    }

    @Test
    public void start() throws IOException {
        System.out.println("start...");
        this.report.setStartTime(System.currentTimeMillis());

        //加载用例套件
        this.caseSuite = this.loadCaseSuite();

        //执行用例
        for (TestCase testCase : this.caseSuite.getTestCaseList()) {
            System.out.println(String.format("------start execute test case=[%s]", testCase.getCaseDesc()));
            TestCaseUtil.executeTestCase(driver, testCase, true);
            System.out.println(String.format("------end execute test case=[%s]", testCase.getCaseDesc()));
        }
        this.report.setEndTime(System.currentTimeMillis());

        //生成测试报告
        this.createReport(this.caseSuite);

    }

    private void createReport(CaseSuite caseSuite) throws IOException {
        report.setTotalTests(caseSuite.getTestCaseList().size());

        int totalSteps = 0;
        int testPassCount = 0;
        int testFailCount = 0;
        int testSkipCount = 0;
        int stepPassCount = 0;
        int stepFailCount = 0;
        int stepSkipCount = 0;
        for (TestCase testCase : caseSuite.getTestCaseList()) {
            if (Constant.PASS.equals(testCase.getExecuteResult())) {
                testPassCount++;
            } else if (Constant.FAIL.equals(testCase.getExecuteResult())) {
                testFailCount++;
            } else {
                testSkipCount++;
            }

            for (CaseStep caseStep : testCase.getCaseStepList()) {
                totalSteps++;
                if (Constant.PASS.equals(caseStep.getStepExecuteResult())) {
                    stepPassCount++;
                } else if (Constant.FAIL.equals(caseStep.getStepExecuteResult())) {
                    stepFailCount++;
                } else {
                    stepSkipCount++;
                }
            }
        }

        report.setTotalSteps(totalSteps);
        report.setTestPassCount(testPassCount);
        report.setTestFailCount(testFailCount);
        report.setTestSkipCount(testSkipCount);
        report.setStepPassCount(stepPassCount);
        report.setStepFailCount(stepFailCount);
        report.setStepSkipCount(stepSkipCount);
        report.setTakeTime((int) ((report.getEndTime() - report.getStartTime()) / 1000));

        this.updateReportToExcel(report);
    }

    private void updateReportToExcel(Report report) throws IOException {
        String filePath = Constant.TEST_CASE_DATA_DIR + "suite.xlsx";
        Workbook workbook = ExcelUtil.getWorkbook(filePath);
        Sheet sheet = workbook.getSheet("report");

        //概述
        Row row2 = sheet.getRow(2);
        row2.createCell(0).setCellValue(report.getTotalTests());
        row2.createCell(1).setCellValue(report.getTotalSteps());
        row2.createCell(2).setCellValue(report.getTakeTime());

        //用例统计
        Row row6 = sheet.getRow(6);
        row6.createCell(0).setCellValue(report.getTestPassCount());
        row6.createCell(1).setCellValue(report.getTestFailCount());
        row6.createCell(2).setCellValue(report.getTestSkipCount());

        //步骤统计
        Row row10 = sheet.getRow(10);
        row10.createCell(0).setCellValue(report.getStepPassCount());
        row10.createCell(1).setCellValue(report.getStepFailCount());
        row10.createCell(2).setCellValue(report.getStepSkipCount());

        FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath));
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        workbook.close();
    }

    private CaseSuite loadCaseSuite() throws IOException {
        //获取用例列表数据
        List<String[]> dataList = ExcelUtil.getAllData(Constant.TEST_CASE_DATA_DIR + "suite.xlsx", "testCase", 1);

        //组装数据
        CaseSuite caseSuite = new CaseSuite();
        for (String[] data : dataList) {
            TestCase testCase = new TestCase();
            testCase.setCaseCode(data[0]);
            testCase.setCaseDesc(data[1]);
            testCase.setPostProcess(data[2]);

            //加载用例步骤数据
            this.loadCaseStep(testCase);

            caseSuite.getTestCaseList().add(testCase);
        }
        return caseSuite;
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
        List<String[]> dataList = ExcelUtil.getAllData(Constant.TEST_CASE_DATA_DIR + "suite.xlsx", "stepData", 1);

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
