package cn.itcast.autotest.report.extentreports;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentListener extends TestListenerAdapter {
    private ExtentReports extentReports;
    private ExtentTest test;

    @Override
    public void onTestStart(ITestResult tr) {
        super.onTestStart(tr);
        extentReports = ExtentReportsTest.getExtentReports();
        test = extentReports.startTest(tr.getName());
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        super.onTestFailure(tr);
        test.log(LogStatus.FAIL, tr.getThrowable());
        extentReports.endTest(test);
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        super.onTestSkipped(tr);
        test.log(LogStatus.SKIP, "SKIP");
        extentReports.endTest(test);
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        super.onTestSuccess(tr);
        test.log(LogStatus.PASS, "Pass");
        extentReports.endTest(test);
    }

    @Override
    public void onFinish(ITestContext testContext) {
        super.onFinish(testContext);
    }
}