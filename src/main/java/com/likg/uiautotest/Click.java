package com.likg.uiautotest;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 点击、多击、长按
 */
public class Click {

    private AndroidDriver<AndroidElement> driver;

    //单击
    @Test
    public void click() throws InterruptedException {
        Thread.sleep(10000);

        //点击显示键盘按钮
        AndroidElement button = driver.findElement(By.id("com.android.dialer:id/floating_action_button_container"));
        button.click();
        Thread.sleep(3000);

        //点击号码
        AndroidElement one = driver.findElement(By.id("com.android.dialer:id/one"));
        AndroidElement two = driver.findElement(By.id("com.android.dialer:id/two"));
        AndroidElement three = driver.findElement(By.id("com.android.dialer:id/three"));
        one.tap(1, 100);
        driver.tap(1, two, 100);
        new TouchAction(driver).tap(three).release().perform();
        Thread.sleep(10000);
    }

    //多击
    @Test
    public void multiClick() throws InterruptedException {
        System.out.println("multiClick...");
        Thread.sleep(10000);

        //点击显示键盘按钮
        AndroidElement button = driver.findElement(By.id("com.android.dialer:id/floating_action_button_container"));
        button.click();
        Thread.sleep(3000);

        //点击号码
        AndroidElement one = driver.findElement(By.id("com.android.dialer:id/one"));
        AndroidElement two = driver.findElement(By.id("com.android.dialer:id/two"));
        AndroidElement three = driver.findElement(By.id("com.android.dialer:id/three"));
        //one.tap(3, 100);
        //driver.tap(3, two, 1000);
        new TouchAction(driver).press(three).waitAction(30).release().press(three).waitAction(30).release().press(three).waitAction(30).release().perform();
        Thread.sleep(10000);
    }

    @Test
    public void longPress() throws InterruptedException {
        System.out.println("longPress...");

        this.multiClick();

        AndroidElement deleteButton = driver.findElementById("com.android.dialer:id/deleteButton");
        TouchAction touchAction = new TouchAction(driver);
        touchAction.longPress(deleteButton).release().perform();
        Thread.sleep(10000);
    }

    @BeforeTest
    public void  beforeTest() throws MalformedURLException {
        System.out.println("beforeTest....");
        DesiredCapabilities capabilities = new DesiredCapabilities();

//        File apk = new File("D://apk/zhihu.apk");
//        System.out.println(apk.exists());
//
//        capabilities.setCapability("deviceName", "xiaomi");
//        capabilities.setCapability("app", apk.getAbsolutePath());
//        URL url = new URL("http://127.0.0.1:4723/wd/hub/");
//        System.out.println("11111111111");
//        driver = new AndroidDriver<AndroidElement>(url, capabilities);
//        System.out.println("2222222");
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//        System.out.println("333333333333333333333333");


        // set up appium
        //File app = new File("D://apk/ContactManager.apk");
        //capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName","Android Emulator");
        capabilities.setCapability("platformVersion", "6.0");
        //capabilities.setCapability("app", app.getAbsolutePath());
        capabilities.setCapability("appPackage", "com.android.dialer");
        capabilities.setCapability("appActivity", ".DialtactsActivity");
        driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        System.out.println("333333333333333333333333");

    }

    @AfterTest
    public void afterTest(){
        driver.quit();
    }




}
