package cn.itcast.autotest.api;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * 输入框操作
 */
public class EditTextOper {

    private AndroidDriver<AndroidElement> driver;

    @Test
    public void sendKeys() throws InterruptedException {
        System.out.println("start...");

        //点击搜索按钮
        driver.findElementById("com.android.dialer:id/search_box_start_search").click();

        //输入字符串
        AndroidElement searchView = driver.findElement(By.id("com.android.dialer:id/search_view"));
        searchView.sendKeys("中国123");
        Thread.sleep(3000);

        // 点击键盘
        driver.pressKeyCode(AndroidKeyCode.KEYCODE_A);
        driver.pressKeyCode(AndroidKeyCode.KEYCODE_B);
        driver.pressKeyCode(AndroidKeyCode.KEYCODE_DEL);
        Thread.sleep(3000);

        //清空
        searchView.clear();
        Thread.sleep(3000);
    }

    @BeforeTest
    public void  beforeTest() throws MalformedURLException {
        System.out.println("beforeTest....");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName","emulator");
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
