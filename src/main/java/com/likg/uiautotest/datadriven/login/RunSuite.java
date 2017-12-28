package com.likg.uiautotest.datadriven.login;

import com.likg.uiautotest.datadriven.base.CaseStep;
import com.likg.uiautotest.datadriven.base.PageElement;
import com.likg.uiautotest.datadriven.base.TestCase;
import com.likg.uiautotest.datadriven.util.DriverUtil;
import com.likg.uiautotest.datadriven.util.ExcelUtil;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RunSuite {

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

        //加载suite中的用例
        this.loadSuiteCase();

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

    private void loadSuiteCase() throws IOException {
        List<String[]> dataList = ExcelUtil.getAllData(baseDir + "suite.xlsx", "testCase", 1);

        for (String[] data : dataList) {
            TestCase testCase = new TestCase();
            testCase.setCaseCode(data[0]);
            testCase.setCaseDesc(data[1]);
            testCaseList.add(testCase);

            //加载用例步骤
            List<String[]> dataList = ExcelUtil.getAllData(baseDir + page + "TestCase.xlsx", "caseStep", 1);



        }

    }

    private List<CaseStep> loadCaseStep(String caseCode) throws IOException {
        String page = caseCode.split("_")[0];
        List<String[]> dataList = ExcelUtil.getAllData(baseDir + page + "TestCase.xlsx", "caseStep", 1);

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
            }
        }
        return caseStepList;
    }


}
