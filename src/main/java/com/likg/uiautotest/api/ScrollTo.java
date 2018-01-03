package com.likg.uiautotest.api;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class ScrollTo {

    private AndroidDriver<WebElement> driver;
    @Test
    public void sendKeys() throws InterruptedException {
        System.out.println("helloWorld........");

        int x = driver.manage().window().getSize().getWidth();
        int y = driver.manage().window().getSize().getHeight();
        System.out.println("x===="+x);
        System.out.println("y===="+y);


        //左滑
        driver.swipe(x * 9 / 10, y / 2, x / 10, y / 2, 500);
        Thread.sleep(2000);

        //左滑
        driver.swipe(x * 9 / 10, y / 2, x / 10, y / 2, 500);
        Thread.sleep(5000);

        try {
            //AndroidElement zoo = driver.findElementByName("likg");
            //AndroidElement zoo = driver.findElementByXPath("//android.widget.TextView[@text='Zoo']");
            //AndroidElement zoo = driver.findElementByAccessibilityId("Zoo");
            //System.out.println("zoo==1.0=="+zoo);
            //System.out.println("zoo==1=="+zoo.getTagName());
            //System.out.println("zoo==1=="+zoo.getId());
        }catch (Exception e){
            System.out.println("没找到。。。。。");
        }



        System.out.println("start up swipe.....");


        WebElement zoo = driver.scrollTo("Zo"); //模糊查找
        WebElement zoo2 = driver.scrollToExact("Zoo"); //精确查找
        System.out.println("scrollTo zoo===="+zoo);


        //上滑
        //driver.swipe(x / 2, y * 9 / 10, x / 2, y / 10, 500);

        try {
            //AndroidElement zoo = driver.findElementByName("likg");
            //AndroidElement zoo = driver.findElementByXPath("//android.widget.TextView[@text='Zoo']");
            //AndroidElement zoo = driver.findElementByAccessibilityId("Zoo");
            //System.out.println("zoo==222.0=="+zoo);
            //System.out.println("zoo==1=="+zoo.getTagName());
            //System.out.println("zoo==1=="+zoo.getId());
        }catch (Exception e){
            e.printStackTrace();
        }
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

        capabilities.setCapability("unicodeKeyboard", "true");
        capabilities.setCapability("resetKeyboard", "true");

        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        System.out.println("333333333333333333333333");

    }

    @AfterTest
    public void afterTest(){
        driver.quit();
    }




}
