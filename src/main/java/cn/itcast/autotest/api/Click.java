package cn.itcast.autotest.api;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 单击、多击、长按
 */
public class Click {

    private AndroidDriver<AndroidElement> driver;

    //单击
    @Test
    public void click() throws InterruptedException {
        Thread.sleep(5000);

        //点击显示键盘按钮
        AndroidElement button = driver.findElementById("com.android.dialer:id/floating_action_button_container");
        button.click();
        Thread.sleep(3000);

        //点击号码
        AndroidElement one = driver.findElementById("com.android.dialer:id/one");
        AndroidElement two = driver.findElementById("com.android.dialer:id/two");
        AndroidElement three = driver.findElementById("com.android.dialer:id/three");
        one.tap(1, 100);
        driver.tap(1, two, 100);
        new TouchAction(driver).tap(three).release().perform();
        Thread.sleep(10000);
    }

    //多击
    @Test
    public void multiClick() throws InterruptedException {
        System.out.println("multiClick...");
        Thread.sleep(5000);

        //点击显示键盘按钮
        AndroidElement button = driver.findElement(By.id("com.android.dialer:id/floating_action_button_container"));
        button.click();
        Thread.sleep(3000);

        //点击号码
        AndroidElement one = driver.findElement(By.id("com.android.dialer:id/one"));
        AndroidElement two = driver.findElement(By.id("com.android.dialer:id/two"));
        for (int i = 0; i < 3; i++) {
            one.click();
        }
        new TouchAction(driver).press(two).waitAction(30).release().press(two).release().perform();
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
    public void beforeTest() throws MalformedURLException {
        System.out.println("beforeTest....");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "emulator");
        capabilities.setCapability("appPackage", "com.android.dialer");
        capabilities.setCapability("appActivity", ".DialtactsActivity");
        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @AfterTest
    public void afterTest() {
        driver.quit();
    }
}
