package cn.itcast.autotest.datadriven;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DataDrivenTest {

    private AndroidDriver<WebElement> driver;

    @DataProvider
    public Object[][] data() throws IOException {
        File file = new File(System.getProperty("user.dir") + "/data/datadriven/calculator.dat");
        List<String> lineList = FileUtils.readLines(file);
        Object[][] dataList = new Object[lineList.size()][3];
        for (int i = 0; i < lineList.size(); i++) {
            dataList[i] = lineList.get(i).split(",");
        }
        return dataList;
    }

    @Test(dataProvider = "data")
    public void add(String num1, String num2, String sum) throws InterruptedException {
        System.out.println(String.format("num1=%s num2=%s sum=%s", num1, num2, sum));

        this.inputNum(num1);
        driver.findElementById("com.android.calculator2:id/op_add").click();
        this.inputNum(num2);
        driver.findElementById("com.android.calculator2:id/eq").click();

        //获取计算结果
        String result = driver.findElementById("com.android.calculator2:id/formula").getText();
        System.out.println("result=" + result);

        Assert.assertEquals(result, sum);

        Thread.sleep(5000);
    }

    //输入数字
    private void inputNum(String num) {
        for (int i = 0; i < num.length(); i++) {
            String n = String.valueOf(num.charAt(i));
            this.getDigitBut(n).click();
        }
    }

    //获取数字按钮元素
    private WebElement getDigitBut(String num) {
        String id = String.format("com.android.calculator2:id/digit_%s", num);
        return driver.findElementById(id);
    }

    @BeforeTest
    public void beforeTest() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.android.calculator2");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".Calculator");

        URL url = new URL("http://127.0.0.1:4723/wd/hub/");
        driver = new AndroidDriver<>(url, capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterTest
    public void afterTest() {
        driver.quit();
    }
}
