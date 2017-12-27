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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class LoginTest {

    private AndroidDriver<AndroidElement> driver;

    private LoginProxy loginProxy;

    @Test
    public void loginSuccess() throws InterruptedException {
        System.out.println("helloWorld........");
        Thread.sleep(3000);

        loginProxy.login("likg_java", "meimima");

        final WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement until = wait.until(new ExpectedCondition<WebElement>() {
            public WebElement apply(WebDriver webDriver) {
                return webDriver.findElement(By.id("net.csdn.csdnplus:id/tvtitle"));
            }
        });
        System.out.println(until);
        System.out.println(until.getText());
        Assert.assertEquals(until.getText(), "头条");

        Thread.sleep(10000);
    }

    @Test
    public void loginUsernameIsNull() throws InterruptedException {
        System.out.println("helloWorld........");
        Thread.sleep(3000);


        loginProxy.login("", "meimima");

        final String toast = "用户名密码不能为空";
        try {
            System.out.println(toast);
            final WebDriverWait wait = new WebDriverWait(driver, 5);
            String xpath = String.format(".//*[contains(@text,'%s')]", toast);
            System.out.println("xpath==="+xpath);
            WebElement until = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
            Assert.assertNotNull(until);

            System.out.println("88888888888888888888");
            System.out.println("until.getText()====="+until.getText());

            System.out.println("until==="+until);
            System.out.println("找到了toast");
        } catch (Exception e) {
            System.out.println("找不到.....................................");
            throw new AssertionError("找不到" + toast);
        }
        /*try {
            System.out.println(toast);
            final WebDriverWait wait = new WebDriverWait(driver, 10);
            WebElement until = wait.until(new ExpectedCondition<WebElement>() {
                @Override
                public WebElement apply(WebDriver webDriver) {
                    String xpath = String.format(".//*[contains(@text,'%s')]", toast);
                    return driver.findElement(By.xpath(".//*[contains(@text,'" + toast + "')]"));
                }
            });
            Assert.assertNotNull(until);
            System.out.println("until==="+until);
            System.out.println("找到了toast11111");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("找不到.111....................................");
            throw new AssertionError("找不到" + toast);
        }*/

        Thread.sleep(10000);
    }

    @Test
    public void loginPasswordIsError() throws InterruptedException {
        System.out.println("helloWorld........");
        Thread.sleep(3000);


        loginProxy.login("likg_java", "123");

        String toast = "用户名或密码错误";
        try {
            System.out.println(toast);
            final WebDriverWait wait = new WebDriverWait(driver, 5);
            WebElement toastEle = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[contains(@text,'" + toast + "')]")));
            Assert.assertNotNull(toastEle);
            //System.out.println(toastEle.getTagName());
            System.out.println("找到了toast");
        } catch (Exception e) {
            System.out.println("找不到.....................................");
            throw new AssertionError("找不到" + toast);
        }

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

        this.loginProxy = new LoginProxy(driver);
    }

    @AfterTest
    public void afterTest() {
        driver.quit();
    }


}