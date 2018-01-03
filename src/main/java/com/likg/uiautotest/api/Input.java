package com.likg.uiautotest.api;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class Input {

    private static Logger log  =  Logger.getLogger(Input. class );

    //protected final Log log = LogFactory.getLog(getClass());

    AndroidDriver<AndroidElement> driver;


    @Test
    public void sendKeys() throws InterruptedException {
        log.error("1111111111111111111111111111111111111111111");
        System.out.println("helloWorld........");

        int x = driver.manage().window().getSize().getWidth();
        int y = driver.manage().window().getSize().getHeight();
        System.out.println("x===="+x);
        System.out.println("y===="+y);

        Thread.sleep(10000);

        AndroidElement element = driver.findElement(By.id("com.android.dialer:id/search_box_start_search"));
        System.out.println(element.getTagName());
        System.out.println(element.getText());
        Thread.sleep(1000);





        System.out.println("sendKeys click...");
        element.click();

        AndroidElement searchView = driver.findElement(By.id("com.android.dialer:id/search_view"));


        driver.pressKeyCode(AndroidKeyCode.KEYCODE_A); // 字母“a”
        driver.pressKeyCode(30); // 字母“a”
        driver.pressKeyCode(31); // 字母“a”
        Thread.sleep(3000);

        searchView.clear();
        Thread.sleep(3000);

        System.out.println("sendKeys start...");
        searchView.sendKeys("1234中国");
        System.out.println("sendKeys end!!!!!");
        Thread.sleep(5000);

        driver.pressKeyCode(AndroidKeyCode.DEL);


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

        driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        System.out.println("333333333333333333333333");

    }

    @AfterTest
    public void afterTest(){
        driver.quit();
    }
}
