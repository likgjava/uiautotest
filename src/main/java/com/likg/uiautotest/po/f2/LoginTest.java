package com.likg.uiautotest.po.f2;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
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

public class LoginTest {

    private AndroidDriver<AndroidElement> driver;

    @Test
    public void loginSuccess() throws InterruptedException {
        System.out.println("helloWorld........");
        Thread.sleep(3000);

        LoginPage loginPage = new LoginPage(this.driver);
        HomePage homePage = loginPage.login("likg_java", "meimima");

        Thread.sleep(10000);
    }

    @Test
    public void loginUserNameIsNull() throws InterruptedException {
        System.out.println("helloWorld........");
        Thread.sleep(3000);

        LoginPage loginPage = new LoginPage(this.driver);
        WebElement webElement = loginPage.invalidLogin("", "123", "用户名密码不能为空");
        Assert.assertNotNull(webElement);

        Thread.sleep(10000);
    }

    @Test
    public void loginPasswordIsError() throws InterruptedException {
        System.out.println("helloWorld........");
        Thread.sleep(3000);

        LoginPage loginPage = new LoginPage(this.driver);
        WebElement webElement = loginPage.invalidLogin("likg_java", "123", "用户名或密码错误");
        Assert.assertNotNull(webElement);

        Thread.sleep(10000);
    }

    @Test
    public void loginFrequent() throws InterruptedException {
        System.out.println("helloWorld........");
        Thread.sleep(3000);

        LoginPage loginPage = new LoginPage(this.driver);
        WebElement webElement = loginPage.invalidLogin("likg_java", "123", "登录太频繁");
        Assert.assertNotNull(webElement);

        Thread.sleep(10000);
    }


    @BeforeTest
    public void beforeTest() throws MalformedURLException {
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
    public void afterTest() {
        driver.quit();
    }


}
