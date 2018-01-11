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
            String filePath = "/screeshot/" + System.currentTimeMillis() + ".png";
            File destFile = new File(System.getProperty("user.dir") + filePath);
            FileUtils.copyFile(screenshot, destFile);

            //输出到测试报告中
            String html = String.format("<img src='..%s'>error step screenshot</img>", filePath);
            System.out.println("html======="+html);
            Reporter.log("error-->" + html);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
