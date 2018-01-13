package cn.itcast.autotest.po.m2.listener;

import cn.itcast.autotest.po.util.DriverUtil;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

import java.io.File;

/**
 * testng监听器
 */
public class TestngListener extends TestListenerAdapter {
    @Override
    public void onTestFailure(ITestResult tr) {
        super.onTestFailure(tr);
        System.out.println("onTestFailure start...");

        try {
            //保存失败截图
            File screenshot = DriverUtil.getDriver().getScreenshotAs(OutputType.FILE);
            String filePath = "/screenshot/error" + System.currentTimeMillis() + ".png";
            File destFile = new File(System.getProperty("user.dir") + filePath);
            FileUtils.copyFile(screenshot, destFile);

            //输出到测试报告中
            Reporter.setEscapeHtml(false);
            String html = String.format("error step screenshot:<img src='..%s' />", filePath);
            Reporter.log(html, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
