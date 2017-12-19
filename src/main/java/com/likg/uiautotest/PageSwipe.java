package com.likg.uiautotest;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class PageSwipe {

    private AndroidDriver<AndroidElement> driver;

    @Test
    public void swipe() throws InterruptedException {
        System.out.println("helloWorld........");

        Dimension dimension = driver.manage().window().getSize();
        int x = dimension.getWidth();
        int y = dimension.getHeight();
        System.out.println("x===="+x);
        System.out.println("y===="+y);

        Thread.sleep(10000);

        //从右往左滑
        driver.swipe(x * 9/10, y/2, x/10, y/2, 500);
        Thread.sleep(10000);

        //从下往上滑动
        driver.swipe(x/2, y*9/10, x/2, y/10, 500);
        Thread.sleep(10000);
    }

    @BeforeTest
    public void  beforeTest() throws MalformedURLException {
        System.out.println("beforeTest....");
        DesiredCapabilities capabilities = new DesiredCapabilities();

        File apk = new File("D://apk/zhihu.apk");
        System.out.println(apk.exists());

        capabilities.setCapability("deviceName", "xiaomi");
        capabilities.setCapability("app", apk.getAbsolutePath());
        URL url = new URL("http://127.0.0.1:4723/wd/hub/");
        System.out.println("11111111111");
        driver = new AndroidDriver<AndroidElement>(url, capabilities);
        System.out.println("2222222");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        System.out.println("333333333333333333333333");
    }

    @AfterTest
    public void afterTest(){
        driver.quit();
    }

}
