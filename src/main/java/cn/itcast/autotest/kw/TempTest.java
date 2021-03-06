package cn.itcast.autotest.kw;

import cn.itcast.autotest.kw.util.DriverUtil;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class TempTest {

    private AndroidDriver<WebElement> driver;

    @BeforeTest
    public void beforeTest() throws IOException {
        //初始化驱动
        this.driver = DriverUtil.getDriver();
    }

    @Test
    public void t() {

        driver.findElementById("net.csdn.csdnplus:id/editTextUserName").sendKeys("18911123299");
        driver.findElementById("net.csdn.csdnplus:id/password").sendKeys("LWif1010");
        driver.findElementById("net.csdn.csdnplus:id/csdnsign_in_button").click();

        driver.findElementByXPath("//android.widget.Button[@text='关闭']").click();
        driver.findElementByXPath("//android.widget.LinearLayout[@resource-id='net.csdn.csdnplus:id/ll_skilltag']//android.widget.TextView[@text='前端']").click();

        System.out.println("6666666");



        driver.resetApp();
        System.out.println("8888888888888888");

        driver.findElementById("net.csdn.csdnplus:id/editTextUserName").sendKeys("likg_java");
        driver.findElementById("net.csdn.csdnplus:id/password").sendKeys("meimima");



        /*driver.findElementByXPath("//android.widget.LinearLayout[@resource-id='net.csdn.csdnplus:id/ll_readlist_item'][1]").click();

        driver.findElementById("net.csdn.csdnplus:id/tv_edit_comment").click();
        driver.findElementById("net.csdn.csdnplus:id/edittext").sendKeys("123456789");
        driver.findElementById("net.csdn.csdnplus:id/send").click();


        String toast = "评论成功";
        try {
            System.out.println(toast);
            final WebDriverWait wait = new WebDriverWait(driver, 30, 10);
            System.out.println("111111111111111");
            WebElement until = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[contains(@text,'" + toast + "')]")));
            System.out.println("2222222222222222222222");
            Assert.assertNotNull(until);
            //System.out.println(until.getTagName());
            System.out.println("找到了toast");
        } catch (Exception e) {
            System.out.println("找不到.....................................");
            throw new AssertionError("找不到" + toast);
        }*/

    }


    @AfterTest
    public void afterTest() {
        DriverUtil.quitDriver();
    }


}
