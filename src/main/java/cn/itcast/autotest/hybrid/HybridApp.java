package cn.itcast.autotest.hybrid;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class HybridApp {

    private AndroidDriver<AndroidElement> driver;

    @Test
    public void test() throws InterruptedException {
        System.out.println("test....");
        Thread.sleep(5000);

        driver.findElementById("net.csdn.csdnplus:id/editTextUserName").sendKeys("likg_java");
        driver.findElementById("net.csdn.csdnplus:id/password").sendKeys("meimima");
        driver.findElementById("net.csdn.csdnplus:id/csdnsign_in_button").click();
        driver.findElementByXPath("//android.widget.Button[@text='关闭']").click();

        String xpath = "//android.widget.ListView[@resource-id='android:id/list']//android.widget.LinearLayout[@index=0]";
        driver.findElementByXPath(xpath).click();

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
        System.out.println("----------------------------------------------------");

        Thread.sleep(3000);

        List<AndroidElement> imgList = driver.findElementsByTagName("img");
        for (AndroidElement img : imgList) {
            System.out.println("src=" + img.getAttribute("src"));
        }

        Thread.sleep(10000);
    }

    @BeforeTest
    public void beforeTest() throws MalformedURLException {
        System.out.println("beforeTest....");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "emulator");
        capabilities.setCapability("appPackage", "net.csdn.csdnplus");
        capabilities.setCapability("appActivity", "net.csdn.csdnplus.activity.SplashActivity");
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Uiautomator2");

        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub/"), capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterTest
    public void afterTest() {
        driver.quit();
    }
}
