package com.likg.uiautotest.po.f2;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * 登录流程测试
 */
public class LoginTest {

    private AndroidDriver<AndroidElement> driver;

    /**
     * 验证登录成功
     * @throws Exception ex
     */
    @Test
    public void loginSuccess() throws Exception {
        LoginPage loginPage = new LoginPage(this.driver);
        HomePage homePage = loginPage.login("likg_java", "meimima");

        Thread.sleep(10000);
    }

    /**
     * 验证用户名为空
     * @throws Exception ex
     */
    @Test
    public void loginUserNameIsNull() throws Exception {
        LoginPage loginPage = new LoginPage(this.driver);
        WebElement webElement = loginPage.invalidLogin("", "123", "用户名密码不能为空");
        Assert.assertNotNull(webElement);

        Thread.sleep(10000);
    }

    /**
     * 验证密码错误
     * @throws Exception ex
     */
    @Test
    public void loginPasswordIsError() throws Exception {
        LoginPage loginPage = new LoginPage(this.driver);
        WebElement webElement = loginPage.invalidLogin("likg_java", "123", "用户名或密码错误");
        Assert.assertNotNull(webElement);

        Thread.sleep(10000);
    }

    /**
     * 验证登录频繁
     * @throws Exception ex
     */
    @Test
    public void loginFrequent() throws Exception {
        LoginPage loginPage = new LoginPage(this.driver);
        WebElement webElement = loginPage.invalidLogin("likg_java", "123", "登录太频繁");
        Assert.assertNotNull(webElement);

        Thread.sleep(10000);
    }

    @BeforeTest
    public void beforeTest() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "emulator");
        capabilities.setCapability("appPackage", "net.csdn.csdnplus");
        capabilities.setCapability("appActivity", "net.csdn.csdnplus.activity.SplashActivity");
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Uiautomator2");

        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub/"), capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterTest
    public void afterTest() {
        driver.quit();
    }
}
