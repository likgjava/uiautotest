package cn.itcast.autotest.po.f2;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * 主页
 */
public class HomePage {

    private AndroidDriver<AndroidElement> driver;

    private AndroidElement titleElement;

    public HomePage(AndroidDriver<AndroidElement> driver) {
        this.driver = driver;

        //判断是否是主页面
        this.titleElement = this.driver.findElementById("net.csdn.csdnplus:id/tvtitle");
        if (this.titleElement == null || !"头条".equals(this.titleElement.getText())) {
            throw new RuntimeException("This is not home page!");
        }
    }

    /**
     * 刷新主页
     */
    public void refresh() {
        //...
    }
}
