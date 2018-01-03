package com.likg.uiautotest.hybrid;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class WebTest {

    private AndroidDriver<AndroidElement> driver;

    @Test
    public void search() throws InterruptedException {
        System.out.println("search...");
        driver.get("http://m.baidu.com");

        driver.findElementByXPath("//*[@id='index-kw']").sendKeys("java");
        driver.findElementById("index-bn").click();

        Thread.sleep(10000);
    }

    @BeforeTest
    public void  beforeTest() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator");
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "browser");
        URL url = new URL("http://127.0.0.1:4723/wd/hub/");
        driver = new AndroidDriver<AndroidElement>(url, capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterTest
    public void afterTest(){
        driver.quit();
    }
}
