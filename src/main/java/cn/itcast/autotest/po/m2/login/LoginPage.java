package cn.itcast.autotest.po.m2.login;

import cn.itcast.autotest.po.util.DriverUtil;
import io.appium.java_client.android.AndroidElement;

/**
 * 登录页面-对象库层
 */
public class LoginPage {

    public AndroidElement findUserNameElement() throws Exception {
        return DriverUtil.getDriver().findElementById("net.csdn.csdnplus:id/editTextUserName");
    }

    public AndroidElement findPasswordElement() throws Exception {
        return DriverUtil.getDriver().findElementById("net.csdn.csdnplus:id/password");
    }

    public AndroidElement findLoginButElement() throws Exception {
        return DriverUtil.getDriver().findElementById("net.csdn.csdnplus:id/csdnsign_in_button");
    }
}
