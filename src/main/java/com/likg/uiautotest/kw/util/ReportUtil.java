package com.likg.uiautotest.kw.util;

import com.likg.uiautotest.kw.constant.Constant;
import com.likg.uiautotest.kw.domain.CaseList;
import com.likg.uiautotest.kw.domain.CaseStep;
import com.likg.uiautotest.kw.domain.Report;
import com.likg.uiautotest.kw.domain.TestCase;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 测试报告工具类
 */
public class ReportUtil {


    public static void createReport(CaseList caseList, Report report) throws IOException {
        report.setTotalTests(caseList.getTestCaseList().size());

        int totalSteps = 0;
        int testPassCount = 0;
        int testFailCount = 0;
        int testSkipCount = 0;
        int stepPassCount = 0;
        int stepFailCount = 0;
        int stepSkipCount = 0;
        for (TestCase testCase : caseList.getTestCaseList()) {
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

        updateReportToExcel(caseList.getType(), report);
    }

    private static void updateReportToExcel(String caseType, Report report) throws IOException {
        String filePath = Constant.TEST_CASE_DATA_DIR + caseType + ".xlsx";
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

}
