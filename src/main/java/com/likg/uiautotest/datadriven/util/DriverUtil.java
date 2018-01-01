package com.likg.uiautotest.datadriven.util;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * 驱动工具类
 */
public class DriverUtil {

    private static AndroidDriver<WebElement> driver;

    public static AndroidDriver<WebElement> getDriver() throws MalformedURLException {
        System.out.println("getDriver...");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "emulator");
        capabilities.setCapability("appPackage", "net.csdn.csdnplus");
        capabilities.setCapability("appActivity", "net.csdn.csdnplus.activity.SplashActivity");
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Uiautomator2");

        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub/"), capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
