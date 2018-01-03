package com.likg.uiautotest.api;

import io.appium.java_client.android.AndroidDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class Location {

    private static Logger log  =  Logger.getLogger(Location. class );

    //protected final Log log = LogFactory.getLog(getClass());

    AndroidDriver<WebElement> driver;


    @Test
    public void sendKeys() throws InterruptedException {
        log.error("1111111111111111111111111111111111111111111");
        System.out.println("helloWorld........");

        int x = driver.manage().window().getSize().getWidth();
        int y = driver.manage().window().getSize().getHeight();
        System.out.println("x===="+x);
        System.out.println("y===="+y);

        Thread.sleep(3000);

        //左滑
        //driver.swipe(x * 9 / 10, y / 2, x / 10, y / 2, 500);
        //Thread.sleep(3000);

        //左滑
        //driver.swipe(x * 9 / 10, y / 2, x / 10, y / 2, 500);
        //Thread.sleep(3000);

        String pageSource = driver.getPageSource();
        System.out.println("pageSource=="+pageSource);

        //WebElement element = driver.findElementByName("Add a contact");
        //WebElement element = driver.findElementByAndroidUIAutomator("text(\"Add a contact\")");
        WebElement element = driver.findElementByAccessibilityId("dial pad");
        System.out.println("element===="+element);
        element.click();

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
