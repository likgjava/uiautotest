package com.likg.uiautotest.hybrid;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class HybridApp {

    private AndroidDriver<AndroidElement> driver;

    @Test
    public void test() throws InterruptedException {
        System.out.println("test....");
        Thread.sleep(5000);

        MobileElement element = driver.findElementById("com.zhihu.android:id/ad_float")
                .findElement(By.xpath("//android.view.ViewGroup[@index=0]"));
        System.out.println(element.getTagName());

        element.click();
        Thread.sleep(5000);

        String context = driver.getContext();
        System.out.println("context=" + context);
        Set<String> contextHandles = driver.getContextHandles();
        System.out.println("contextHandles=" + contextHandles);

        String pageSource = driver.getPageSource();
        System.out.println("pageSource==1=" + pageSource);

        driver.context((String) contextHandles.toArray()[1]);
        //driver.context("WEBVIEW_com.zhihu.android");

        pageSource = driver.getPageSource();
        System.out.println("pageSource==2=" + pageSource);

        Thread.sleep(3000);

        AndroidElement elementByClassName = driver.findElementByClassName("AnswerItem-appViewFootnotes");
        System.out.println("text==" + elementByClassName.getText());

        Thread.sleep(10000);
    }

    @BeforeTest
    public void beforeTest() throws MalformedURLException {
        System.out.println("beforeTest....");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.zhihu.android");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.zhihu.android.app.ui.activity.MainActivity");

        URL url = new URL("http://127.0.0.1:4723/wd/hub/");
        driver = new AndroidDriver<>(url, capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterTest
    public void afterTest() {
        driver.quit();
    }
}
