package com.likg.uiautotest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class HelloWorld {

    private static Logger log  =  Logger.getLogger(HelloWorld.class );

    //protected final Log log = LogFactory.getLog(getClass());

    AndroidDriver<AndroidElement> driver;

    @Test
    public void swipe() throws InterruptedException {
        log.error("1111111111111111111111111111111111111111111");
        System.out.println("helloWorld........");

        int x = driver.manage().window().getSize().getWidth();
        int y = driver.manage().window().getSize().getHeight();
        System.out.println("x===="+x);
        System.out.println("y===="+y);

        Thread.sleep(10000);

        driver.swipe(x * 9/10, y/2, x/10, y/2, 500);
        //driver.swipe(900, 500, 100, 500, 200);
        //driver.swipe(900, 500, -100, 0, 200);
        System.out.println("...............................1111111111..........................");
        Thread.sleep(10000);
        driver.swipe(x * 9/10, y/2, x/10, y/2, 500);
        Thread.sleep(3000);
    }

    @BeforeTest
    public void  beforeTest() throws MalformedURLException {
        System.out.println("beforeTest....");
        DesiredCapabilities capabilities = new DesiredCapabilities();

        File apk = new File("D://apk/zhihu.apk");
        System.out.println(apk.exists());

        capabilities.setCapability("deviceName", "xiaomi");
        capabilities.setCapability("app", apk.getAbsolutePath());
        URL url = new URL("http://127.0.0.1:4723/wd/hub/");
        System.out.println("11111111111");
        driver = new AndroidDriver<AndroidElement>(url, capabilities);
        System.out.println("2222222");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        System.out.println("333333333333333333333333");


        // set up appium
        /*File app = new File("D://apk/ContactManager.apk");
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName","Android Emulator");
        capabilities.setCapability("platformVersion", "7.1.1");
        capabilities.setCapability("app", app.getAbsolutePath());
        capabilities.setCapability("appPackage", "com.example.android.contactmanager");
        capabilities.setCapability("appActivity", ".ContactManager");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        System.out.println("333333333333333333333333");*/

    }

    @AfterTest
    public void afterTest(){
        driver.quit();
    }


}
