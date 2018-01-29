package cn.itcast.autotest.api;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 元素定位
 */
public class Location {

    private AndroidDriver<WebElement> driver;

    @Test
    public void getPageSource() {
        String pageSource = driver.getPageSource();
        System.out.println(pageSource);
    }

    @Test
    public void getAttribute() {
        WebElement element = driver.findElementById("com.android.dialer:id/floating_action_button");
        //获取标签名称
        String tagName = element.getTagName();
        System.out.println("tagName=" + tagName);
        //获取text的属性值
        String text = element.getText();
        System.out.println("text=" + text);

        //获取content-desc或text的属性值
        String name = element.getAttribute("name");
        System.out.println("name=" + name);
        //获取class的属性值
        String className = element.getAttribute("className");
        System.out.println("className=" + className);
        //获取resource-id的属性值
        String resourceId = element.getAttribute("resourceId");
        System.out.println("resourceId=" + resourceId);
    }

    @Test
    public void byId() {
        //获取单个匹配的元素
        WebElement element = driver.findElementById("com.android.dialer:id/floating_action_button");
        //WebElement element = driver.findElement(By.id("com.android.dialer:id/floating_action_button"));
        System.out.println("name=" + element.getAttribute("name"));

        //获取多个匹配的元素
        List<WebElement> elementList = driver.findElementsById("com.android.dialer:id/floating_action_button");
        //List<WebElement> elementList = driver.findElements(By.id("com.android.dialer:id/floating_action_button"));
        System.out.println("elementList.size=" + elementList.size());
    }

    @Test
    public void byClassName() {
        //获取单个匹配的元素
        WebElement element = driver.findElementByClassName("android.widget.ImageButton");
        //WebElement element = driver.findElement(By.className("android.widget.ImageButton"));
        System.out.println("name=" + element.getAttribute("name"));

        //获取多个匹配的元素
        List<WebElement> elementList = driver.findElementsByClassName("android.widget.ImageButton");
        //List<WebElement> elementList = driver.findElements(By.className("android.widget.ImageButton"));
        System.out.println("elementList.size=" + elementList.size());
        for (WebElement e : elementList) {
            System.out.println("name=" + e.getAttribute("name"));
        }
    }

    @Test
    public void byName() {
        //该方式已不支持
        WebElement element = driver.findElementByName("dial pad");
        System.out.println(element);
    }

    @Test
    public void byAccessibilityId() {
        WebElement element = driver.findElementByAccessibilityId("dial pad");
        System.out.println("name=" + element.getAttribute("name"));
    }

    @Test
    public void byXPath() {
        WebElement element = driver.findElementByXPath("//android.widget.ImageButton");
        System.out.println("name=" + element.getAttribute("name"));

        //属性等于指定字符
        element = driver.findElementByXPath("//android.widget.ImageButton[@index=2]");
        System.out.println("name=" + element.getAttribute("name"));

        //属性包含指定字符
        element = driver.findElementByXPath("//android.widget.TextView[contains(@text, 'Add')]");
        System.out.println("name=" + element.getAttribute("name"));
    }

    @Test
    public void byUiAutomator() {
        //text定位
        WebElement element = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"Add a favorite\")");
        System.out.println("name=" + element.getAttribute("name"));

        //resourceId定位
        element = driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.android.dialer:id/floating_action_button\")");
        System.out.println("name=" + element.getAttribute("name"));

        //className定位
        element = driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.ImageButton\")");
        System.out.println("name=" + element.getAttribute("name"));

        //description定位
        element = driver.findElementByAndroidUIAutomator("new UiSelector().description(\"dial pad\")");
        System.out.println("name=" + element.getAttribute("name"));

        //index定位
        List<WebElement> list = driver.findElementsByAndroidUIAutomator("new UiSelector().index(1)");
        System.out.println("list.size=" + list.size());

        //多个属性自由组合
        element = driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.ImageButton\").index(2)");
        System.out.println("name=" + element.getAttribute("name"));

        //模糊匹配
        element = driver.findElementByAndroidUIAutomator("new UiSelector().textContains(\"Add\")");
        System.out.println("name=" + element.getAttribute("name"));

        //正则表达式匹配
        element = driver.findElementByAndroidUIAutomator("new UiSelector().textMatches(\"^Add.*\")");
        System.out.println("name=" + element.getAttribute("name"));
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