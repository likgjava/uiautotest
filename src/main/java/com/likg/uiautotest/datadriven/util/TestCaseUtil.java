package com.likg.uiautotest.datadriven.util;

import com.likg.uiautotest.datadriven.base.CaseStep;
import com.likg.uiautotest.datadriven.base.PageElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestCaseUtil {

    public static WebElement findElement(AndroidDriver<WebElement> driver, CaseStep caseStep) {
        WebElement element;
        PageElement pageElement = caseStep.getPageElement();
        switch (pageElement.getLocationType()) {
            case "id" : {
                element = driver.findElementById(pageElement.getLocationValue());
                break;
            }
            case "name" : {
                element = driver.findElementByName(pageElement.getLocationValue());
                break;
            }
            case "toast" : {
                WebDriverWait wait = new WebDriverWait(driver, 5);
                String xpath = String.format(".//*[contains(@text,'%s')]", caseStep.getExpectedResult());
                element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
                break;
            }
            default:{
                throw new RuntimeException("unknown location type="+pageElement.getLocationType());
            }
        }
        return element;
    }

    public static boolean executeOperation(WebElement element, String action, String inputData, String expectedResult) {
        switch (action) {
            case "sendKey" : {
                element.sendKeys(inputData);
                return true;
            }
            case "click" : {
                element.click();
                return true;
            }
            case "getText" : {
                String text = element.getText();
                System.out.println("expectedResult="+expectedResult + " text="+text);
                return expectedResult.equals(text);
            }
            case "getToast" : {
                return element != null;
            }
            default:{
                throw new RuntimeException("unknown action="+action);
            }
        }
    }

}
