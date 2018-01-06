package com.likg.uiautotest.api;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Location {

    private AndroidDriver<WebElement> driver;

    @Test
    public void byId() {
        //获取单个匹配的元素
        WebElement element = driver.findElementById("com.miui.home:id/search_but");
        WebElement element2 = driver.findElement(By.id("com.miui.home:id/search_but"));

        //获取多个匹配的元素
        List<WebElement> elementList = driver.findElementsById("com.miui.home:id/search_but");
        List<WebElement> elementList2 = driver.findElements(By.id("com.miui.home:id/search_but"));
    }

    @Test
    public void byClassName() {
        //获取单个匹配的元素
        WebElement element = driver.findElementByClassName("android.widget.TextView");
        WebElement element2 = driver.findElement(By.className("android.widget.TextView"));

        //获取多个匹配的元素
        List<WebElement> elementList = driver.findElementsByClassName("android.widget.TextView");
        List<WebElement> elementList2 = driver.findElements(By.className("android.widget.TextView"));
    }

    @Test
    public void byAccessibilityId() throws InterruptedException {
        Thread.sleep(3000);

        String pageSource = driver.getPageSource();
        System.out.println("pageSource=="+pageSource);

        //WebElement element = driver.findElementByName("Add a contact");
        //WebElement element = driver.findElementByAndroidUIAutomator("text(\"Add a contact\")");
        WebElement element = driver.findElementByAccessibilityId("dial pad");
        System.out.println("element===="+element);
        element.click();

        driver.findElementByAndroidUIAutomator("new UiSelector().text(\"每日推荐\")");

        Thread.sleep(10000);
    }

    @BeforeTest
    public void  beforeTest() throws MalformedURLException {
        System.out.println("beforeTest....");
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName","emulator");
        capabilities.setCapability("platformVersion", "6.0");
        capabilities.setCapability("appPackage", "com.android.dialer");
        capabilities.setCapability("appActivity", ".DialtactsActivity");

        capabilities.setCapability("unicodeKeyboard", "true");
        capabilities.setCapability("resetKeyboard", "true");

        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterTest
    public void afterTest(){
        driver.quit();
    }
}
