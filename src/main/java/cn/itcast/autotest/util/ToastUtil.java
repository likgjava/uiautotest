package cn.itcast.autotest.util;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ToastUtil {

    public static WebElement findToastElement(AndroidDriver<WebElement> driver, String toast) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        String xpath = String.format(".//*[contains(@text,'%s')]", toast);
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
    }
}
