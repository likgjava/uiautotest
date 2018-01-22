package cn.itcast.autotest.api;

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

public class UnlockNineGrid {

    AndroidDriver<AndroidElement> driver;

    @Test
    public void unlock() throws InterruptedException {
        //定位9个圆点
        AndroidElement one = driver.findElement(By.name("one"));
        AndroidElement two = driver.findElement(By.name("two"));
        AndroidElement three = driver.findElement(By.name("three"));
        AndroidElement four = driver.findElement(By.name("four"));
        AndroidElement five = driver.findElement(By.name("five"));
        AndroidElement six = driver.findElement(By.name("six"));
        AndroidElement seven = driver.findElement(By.name("seven"));
        AndroidElement eight = driver.findElement(By.name("eight"));
        AndroidElement nine = driver.findElement(By.name("nine"));

        //1->2->5->8
        TouchAction touchAction = new TouchAction(driver);
        touchAction.press(one).moveTo(two).moveTo(five).moveTo(eight).release().perform();

        Thread.sleep(10000);
    }

    @BeforeTest
    public void beforeTest() throws MalformedURLException {
        System.out.println("beforeTest....");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "emulator");
        capabilities.setCapability("platformVersion", "6.0");
        capabilities.setCapability("appPackage", "com.android.dialer");
        capabilities.setCapability("appActivity", ".DialtactsActivity");
        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @AfterTest
    public void afterTest() {
        driver.quit();
    }
}