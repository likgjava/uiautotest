package cn.itcast.autotest.api;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Swipe {

    private AndroidDriver<AndroidElement> driver;

    @Test
    public void pageSwipe() throws InterruptedException {
        System.out.println("pageSwipe...");
        Thread.sleep(5000);

        Dimension dimension = driver.manage().window().getSize();
        int x = dimension.getWidth();
        int y = dimension.getHeight();
        System.out.println(String.format("x=%s y=%s", x, y));
        Thread.sleep(5000);

        //左滑
        driver.swipe(x * 9 / 10, y / 2, x / 10, y / 2, 500);
        Thread.sleep(5000);
        driver.swipe(x * 9 / 10, y / 2, x / 10, y / 2, 500);
        Thread.sleep(5000);

        //上滑
        driver.swipe(x / 2, y * 9 / 10, x / 2, y / 10, 500);
        Thread.sleep(5000);
    }

    private void leftSwipePage() {
        Dimension dimension = driver.manage().window().getSize();
        int x = dimension.getWidth();
        int y = dimension.getHeight();
        System.out.println(String.format("x=%s y=%s", x, y));

        //左滑
        driver.swipe(x * 9 / 10, y / 2, x / 10, y / 2, 500);
    }

    @Test
    public void elementSwipe() throws InterruptedException {
        System.out.println("elementSwipe...");
        leftSwipePage();
        Thread.sleep(1000);
        leftSwipePage();
        Thread.sleep(1000);

        //获取元素信息
        String xpath = "//android.widget.ListView[@resource-id='android:id/list']//android.view.ViewGroup[@index=0]";
        AndroidElement element = driver.findElementByXPath(xpath);
        int x = element.getLocation().getX();
        int y = element.getLocation().getY();
        int width = element.getSize().getWidth();
        int height = element.getSize().getHeight();
        System.out.println(String.format("x=%s y=%s width=%s height=%s", x, y, width, height));

        //计算起止位置坐标
        int startX = x + width * 9 / 10;
        int startY = y + height / 2;
        int endX = x + width / 10;
        int endY = startY;

        //左滑
        driver.swipe(startX, startY, endX, endY, 500);
        Thread.sleep(5000);
    }


    @Test
    @SuppressWarnings("deprecation")
    public void scrollTo() throws Exception {
        System.out.println("scrollTo...");
        leftSwipePage();
        Thread.sleep(1000);
        leftSwipePage();
        Thread.sleep(1000);


        //模糊查找
        WebElement zoo = driver.scrollTo("zo");
        System.out.println("zoo=" + zoo);

        //精确查找
        WebElement zoo2 = driver.scrollToExact("baby");
        System.out.println("zoo2=" + zoo2);

        Thread.sleep(5000);
    }

    @BeforeTest
    public void beforeTest() throws MalformedURLException {
        System.out.println("beforeTest....");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "emulator");
        capabilities.setCapability("appPackage", "com.android.dialer");
        capabilities.setCapability("appActivity", ".DialtactsActivity");

        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterTest
    public void afterTest() {
        driver.quit();
    }
}