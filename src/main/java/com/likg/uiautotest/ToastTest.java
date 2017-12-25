package com.likg.uiautotest;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class ToastTest {

    private AndroidDriver<AndroidElement> driver;

    @Test
    public void swipe() throws InterruptedException {
        System.out.println("helloWorld........");
        Thread.sleep(3000);

        //获取屏幕的宽度和高度
        int width = driver.manage().window().getSize().getWidth();
        int height = driver.manage().window().getSize().getHeight();
        System.out.println("width===="+width);
        System.out.println("height===="+height);

        AndroidElement element = driver.findElementById("net.csdn.csdnplus:id/csdnsign_in_button");
        System.out.println(element.getText());

        element.click();

        String toast="用户名密码不能为空";
//toast 寻找测试
        try {
            System.out.println(toast);
            final WebDriverWait wait = new WebDriverWait(driver,5);
            Assert.assertNotNull(wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[contains(@text,'"+ toast + "')]"))));
            System.out.println("找到了toast");
        } catch (Exception e) {
            System.out.println("找不到.....................................");
            throw new AssertionError("找不到"+toast);
        }

        Thread.sleep(10000);
    }

    @BeforeTest
    public void  beforeTest() throws MalformedURLException {
        System.out.println("beforeTest...888.");

        File apk = new File("D://apk/CSDN.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "mi");
        //capabilities.setCapability("app", apk.getAbsolutePath());

        capabilities.setCapability("appPackage", "net.csdn.csdnplus");
        capabilities.setCapability("appActivity", "net.csdn.csdnplus.activity.SplashActivity");

        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Uiautomator2");

        URL url = new URL("http://127.0.0.1:4723/wd/hub/");
        driver = new AndroidDriver<AndroidElement>(url, capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterTest
    public void afterTest(){
        driver.quit();
    }

}
