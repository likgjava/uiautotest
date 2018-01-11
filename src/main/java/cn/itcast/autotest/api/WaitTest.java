package cn.itcast.autotest.api;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 点击、多击、长按
 */
public class WaitTest {

    private AndroidDriver<AndroidElement> driver;

    @Test
    public void waitTest() throws InterruptedException {
        System.out.println("waitTest...");
        Thread.sleep(5000);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println("start time===" + sdf.format(new Date()));
        final WebDriverWait wait = new WebDriverWait(driver, 10, 1000);
        String id = "com.android.settings:id/search666";
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
        } catch (Exception e) {
            System.out.println("没找到。。。。。。");
            System.out.println("end time=====" + sdf.format(new Date()));
        }

        Thread.sleep(10000);
    }


    @BeforeTest
    public void beforeTest() throws MalformedURLException {
        System.out.println("beforeTest....");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "emulator");
        capabilities.setCapability("appPackage", "com.android.settings");
        capabilities.setCapability("appActivity", "com.android.settings.Settings");
        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @AfterTest
    public void afterTest() {
        driver.quit();
    }
}
