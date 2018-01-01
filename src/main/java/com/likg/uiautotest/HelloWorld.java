package com.likg.uiautotest;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * appium入门示例
 * @author likaige
 */
public class HelloWorld {

    private AndroidDriver<WebElement> driver;

    @Test
    public void swipe() throws InterruptedException {
        System.out.println("swipe...");
        Thread.sleep(5000);

        //获取屏幕的宽度和高度
        int width = driver.manage().window().getSize().getWidth();
        int height = driver.manage().window().getSize().getHeight();
        System.out.println("width=" + width);
        System.out.println("height=" + height);

        //左滑
        driver.swipe(width * 9 / 10, height / 2, width / 10, height / 2, 500);
        Thread.sleep(5000);
        //右滑
        driver.swipe(width / 10, height / 2, width * 9 / 10, height / 2, 500);
        Thread.sleep(5000);
    }

    @BeforeTest
    public void beforeTest() throws MalformedURLException {
        File apk = new File("D://apk/zhihu.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "mi");
        capabilities.setCapability("app", apk.getAbsolutePath());

        URL url = new URL("http://127.0.0.1:4723/wd/hub/");
        driver = new AndroidDriver<>(url, capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterTest
    public void afterTest() {
        driver.quit();
    }
}
