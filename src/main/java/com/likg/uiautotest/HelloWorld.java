package com.likg.uiautotest;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class HelloWorld {

    private AndroidDriver<AndroidElement> driver;

    @Test
    public void swipe() throws InterruptedException {
        System.out.println("helloWorld........");
        Thread.sleep(10000);

        //获取屏幕的宽度和高度
        int width = driver.manage().window().getSize().getWidth();
        int height = driver.manage().window().getSize().getHeight();
        System.out.println("width===="+width);
        System.out.println("height===="+height);

        driver.swipe(width * 9/10, height/2, width/10, height/2, 500);
        Thread.sleep(10000);
        driver.swipe(width * 9/10, height/2, width/10, height/2, 500);
        Thread.sleep(3000);
    }

    @BeforeTest
    public void  beforeTest() throws MalformedURLException {
        System.out.println("beforeTest....");

        File apk = new File("D://apk/zhihu.apk");

        DesiredCapabilities capabilities = new DesiredCapabilities();
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
