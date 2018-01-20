package cn.itcast.autotest.api;

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

public class ToastTest {

    private AndroidDriver<AndroidElement> driver;

    @Test
    public void toast() throws InterruptedException {
        System.out.println("toast...");
        Thread.sleep(3000);

        //直接点击登录按钮
        driver.findElementById("net.csdn.csdnplus:id/csdnsign_in_button").click();

        String toast = "用户名密码不能为空";
        try {
            final WebDriverWait wait = new WebDriverWait(driver, 5);
            String xpath = String.format(".//*[contains(@text,'%s')]", toast);
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
            Assert.assertNotNull(element);
            System.out.println("找到了toast");
        } catch (Exception e) {
            throw new AssertionError("找不到toast:" + toast);
        }

        Thread.sleep(10000);
    }

    @BeforeTest
    public void beforeTest() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "emulator");
        capabilities.setCapability("appPackage", "net.csdn.csdnplus");
        capabilities.setCapability("appActivity", ".activity.SplashActivity");

        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Uiautomator2");

        URL url = new URL("http://127.0.0.1:4723/wd/hub/");
        driver = new AndroidDriver<>(url, capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterTest
    public void afterTest() {
        driver.quit();
    }
}