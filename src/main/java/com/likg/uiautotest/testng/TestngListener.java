package com.likg.uiautotest.testng;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

/**
 * testng监听器
 */
public class TestngListener extends TestListenerAdapter {
    @Override
    public void onTestFailure(ITestResult tr) {
        super.onTestFailure(tr);
        //异常处理
        System.out.println("error...");
    }
}
