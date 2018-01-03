package com.likg.uiautotest.api;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 横竖屏切换
 */
public class Rotate {

    private AndroidDriver<AndroidElement> driver;

    @Test
    public void rotate() throws InterruptedException {
        System.out.println("rotate...");
        Thread.sleep(5000);

        //获取屏幕朝向
        ScreenOrientation orientation = driver.getOrientation();
        System.out.println("orientation=" + orientation);

        //切换成横屏
        driver.rotate(ScreenOrientation.LANDSCAPE);
        Thread.sleep(5000);

        //切换成竖屏
        driver.rotate(ScreenOrientation.PORTRAIT);
        Thread.sleep(10000);
    }

    @BeforeTest
    public void beforeTest() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "emulator");
        capabilities.setCapability("appPackage", "com.android.dialer");
        capabilities.setCapability("appActivity", ".DialtactsActivity");
        driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @AfterTest
    public void afterTest() {
        driver.quit();
    }
}
