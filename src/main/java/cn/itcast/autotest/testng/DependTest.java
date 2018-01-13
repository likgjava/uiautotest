package cn.itcast.autotest.testng;

import org.testng.annotations.Test;

/**
 * 依赖测试
 *
 */
public class DependTest {
    @Test
    public void openApp() {
        System.out.println("openApp...");
    }

    @Test(dependsOnMethods = "openApp")
    public void inputUsername() {
        System.out.println("inputUsername...");
    }

    @Test(dependsOnMethods = "inputUsername")
    public void login() {
        System.out.println("login...");
    }
}
