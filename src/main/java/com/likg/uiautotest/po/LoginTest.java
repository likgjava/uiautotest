package com.likg.uiautotest.po;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * 不使用PO模式，对登录流程进行测试
 */
public class LoginTest {

    private AndroidDriver<AndroidElement> driver;

    /**
     * 验证登录成功
     * @throws Exception ex
     */
    @Test
    public void loginSuccess() throws Exception {
        driver.findElementById("net.csdn.csdnplus:id/editTextUserName").sendKeys("likg_java");
        driver.findElementById("net.csdn.csdnplus:id/password").sendKeys("meimima");
        driver.findElementById("net.csdn.csdnplus:id/csdnsign_in_button").click();

        AndroidElement title = driver.findElement(By.id("net.csdn.csdnplus:id/tvtitle"));
        Assert.assertEquals(title.getText(), "头条");

        Thread.sleep(10000);
    }

    /**
     * 验证用户名为空
     * @throws Exception ex
     */
    @Test
    public void loginUsernameIsNull() throws Exception {
        driver.findElementById("net.csdn.csdnplus:id/editTextUserName").sendKeys("");
        driver.findElementById("net.csdn.csdnplus:id/password").sendKeys("123");
        driver.findElementById("net.csdn.csdnplus:id/csdnsign_in_button").click();

        String toast = "用户名密码不能为空";
        final WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement until = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[contains(@text,'" + toast + "')]")));
        Assert.assertNotNull(until);

        Thread.sleep(3000);
    }

    /**
     * 验证密码错误
     * @throws Exception ex
     */
    @Test
    public void loginPasswordIsError() throws Exception {
        driver.findElementById("net.csdn.csdnplus:id/editTextUserName").sendKeys("likg_java");
        driver.findElementById("net.csdn.csdnplus:id/password").sendKeys("123");
        driver.findElementById("net.csdn.csdnplus:id/csdnsign_in_button").click();

        String toast = "用户名或密码错误";
        final WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement toastEle = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[contains(@text,'" + toast + "')]")));
        Assert.assertNotNull(toastEle);

        Thread.sleep(3000);
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
