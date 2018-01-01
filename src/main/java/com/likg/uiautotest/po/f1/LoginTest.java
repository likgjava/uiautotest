package com.likg.uiautotest.po.f1;

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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * 登录流程测试
 */
public class LoginTest {
    private AndroidDriver<AndroidElement> driver;
    private LoginProxy loginProxy;

    /**
     * 验证登录成功
     * @throws Exception ex
     */
    @Test
    public void loginSuccess() throws Exception {
        loginProxy.login("likg_java", "meimima");

        final WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement until = wait.until(new ExpectedCondition<WebElement>() {
            public WebElement apply(WebDriver webDriver) {
                return webDriver.findElement(By.id("net.csdn.csdnplus:id/tvtitle"));
            }
        });
        Assert.assertEquals(until.getText(), "头条");

        Thread.sleep(10000);
    }

    /**
     * 验证用户名为空
     * @throws Exception ex
     */
    @Test
    public void loginUsernameIsNull() throws Exception {
        loginProxy.login("", "meimima");

        final String toast = "用户名密码不能为空";
        final WebDriverWait wait = new WebDriverWait(driver, 5);
        String xpath = String.format(".//*[contains(@text,'%s')]", toast);
        WebElement until = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        Assert.assertNotNull(until);

        Thread.sleep(10000);
    }

    /**
     * 验证密码错误
     * @throws Exception ex
     */
    @Test
    public void loginPasswordIsError() throws Exception {
        loginProxy.login("likg_java", "123");

        String toast = "用户名或密码错误";
        final WebDriverWait wait = new WebDriverWait(driver, 5);
        String xpath = String.format(".//*[contains(@text,'%s')]", toast);
        WebElement toastEle = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        Assert.assertNotNull(toastEle);

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

        this.loginProxy = new LoginProxy(driver);
    }

    @AfterTest
    public void afterTest() {
        driver.quit();
    }
}
