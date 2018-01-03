package com.likg.uiautotest.api;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 截屏
 */
public class Screenshot {

    private AndroidDriver<AndroidElement> driver;

    @Test
    public void screenshot() throws InterruptedException, IOException {
        System.out.println("screenshot...");
        Thread.sleep(10000);

        File screenshot = driver.getScreenshotAs(OutputType.FILE);
        File destFile = new File("D:/screenshot/a.png");
        FileUtils.copyFile(screenshot, destFile);

        Thread.sleep(10000);
    }

    @BeforeTest
    public void  beforeTest() throws MalformedURLException {
        System.out.println("beforeTest....");
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName","Android Emulator");
        capabilities.setCapability("platformVersion", "6.0");
        capabilities.setCapability("appPackage", "com.android.dialer");
        capabilities.setCapability("appActivity", ".DialtactsActivity");
        capabilities.setCapability("unicodeKeyboard", "True");

        driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        System.out.println("333333333333333333333333");

    }

    @AfterTest
    public void afterTest(){
        driver.quit();
    }




}
