package cn.itcast.autotest.kw.run;

import cn.itcast.autotest.kw.domain.CaseList;
import cn.itcast.autotest.kw.domain.Report;
import cn.itcast.autotest.kw.domain.TestCase;
import cn.itcast.autotest.kw.util.DriverUtil;
import cn.itcast.autotest.kw.util.ReportUtil;
import cn.itcast.autotest.kw.util.TestCaseUtil;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * 运行用例套件
 */
public class RunSuite {

    private AndroidDriver<WebElement> driver;

    @BeforeTest
    public void beforeTest() throws IOException {
        //初始化驱动
        this.driver = DriverUtil.getDriver();
    }

    @Test
    public void start() throws IOException {
        System.out.println("start...");
        Report report = new Report();
        report.setStartTime(System.currentTimeMillis());

        //加载用例套件
        CaseList caseList = TestCaseUtil.loadCaseList();

        //执行用例
        for (TestCase testCase : caseList.getTestCaseList()) {
            System.out.println(String.format("------start execute case=[%s]", testCase.getCaseDesc()));
            TestCaseUtil.executeTestCase(driver, testCase, true);
            System.out.println(String.format("------end execute case=[%s]", testCase.getCaseDesc()));
        }
        report.setEndTime(System.currentTimeMillis());

        //生成测试报告
        ReportUtil.createReport(caseList, report);
    }

    @AfterTest
    public void afterTest() {
        DriverUtil.quitDriver();
    }
}