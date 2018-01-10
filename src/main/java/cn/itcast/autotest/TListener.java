package cn.itcast.autotest;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import org.testng.log4testng.Logger;

/**
 * testng监听器
 */
public class TListener extends TestListenerAdapter {
    @Override
    public void onTestFailure(ITestResult tr) {
        super.onTestFailure(tr);
        //异常处理
        System.out.println("error...");

        Reporter.log("onTestFailure()....deal exception....");
        Reporter.log("error msg!");

        tr.setAttribute("img", "aaaaaaaaaaaaaaa.jpg");

    }
}
