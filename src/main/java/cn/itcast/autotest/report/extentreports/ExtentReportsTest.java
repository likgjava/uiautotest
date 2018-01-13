package cn.itcast.autotest.report.extentreports;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.NetworkMode;
import com.relevantcodes.extentreports.ReporterType;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({ExtentListener.class})
public class ExtentReportsTest {
    private static final String reportLocation = "test-output/extentReport.html";
    private static ExtentReports extentReports;

    @Test
    public void foo(){
        System.out.println("foo...");
        Reporter.log("foo exception test...");
        "".charAt(2);
    }

    @Test
    public void bar(){
        System.out.println("bar...");
    }

    @BeforeSuite
    public static void beforeSuite() {
        extentReports = new ExtentReports(reportLocation, true, NetworkMode.OFFLINE);
        extentReports.startReporter(ReporterType.DB, reportLocation);

        System.setProperty("org.uncommons.reportng.escape-output", "false");
    }

    @AfterSuite
    public static void afterSuite() {
        extentReports.close();
    }

    public static ExtentReports getExtentReports() {
        return extentReports;
    }
}
