package com.likg.uiautotest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class HybridApp {

    private AndroidDriver<AndroidElement> driver;

    @Test
    public void swipe() throws InterruptedException {
        System.out.println("helloWorld.......11.");
        Thread.sleep(5000);

        MobileElement element = driver.findElementById("com.zhihu.android:id/ad_float")
                .findElement(By.xpath("//android.view.ViewGroup[@index=0]"));
        System.out.println(element.getTagName());

        element.click();
        Thread.sleep(10000);

        String context = driver.getContext();
        System.out.println(context);
        Set<String> contextHandles = driver.getContextHandles();
        System.out.println(contextHandles);

        String pageSource = driver.getPageSource();
        System.out.println("pageSource==1111="+pageSource);

        driver.context("WEBVIEW_com.zhihu.android");

        pageSource = driver.getPageSource();
        System.out.println("pageSource==22222="+pageSource);

        Thread.sleep(3000);

        AndroidElement elementByClassName = driver.findElementByClassName("AnswerItem-appViewFootnotes");
        System.out.println("text=="+elementByClassName.getText());


        //设置等待10s，每2s检查一次元素是否加载成功
        /*WebDriverWait wait = new WebDriverWait(driver, 10, 2000);
        wait.until(new ExpectedCondition<WebElement>() {
            public WebElement apply(WebDriver webDriver) {
                return webDriver.findElement(By.name("赞"));
            }
        }).click();


        AndroidElement elementById = driver.findElementById("com.zhihu.android:id/name");
        System.out.println(elementById.getText());*/


        Thread.sleep(10000);
    }

    @BeforeTest
    public void  beforeTest() throws MalformedURLException {
        System.out.println("beforeTest....");

        File apk = new File("D://apk/zhihu.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "mi");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.zhihu.android");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.zhihu.android.app.ui.activity.MainActivity");

        URL url = new URL("http://127.0.0.1:4723/wd/hub/");
        driver = new AndroidDriver<AndroidElement>(url, capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterTest
    public void afterTest(){
        driver.quit();
    }

}
