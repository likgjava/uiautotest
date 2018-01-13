package cn.itcast.autotest.testng;

import org.testng.annotations.Test;

/**
 * 组测试
 *
 */
public class TestngGroupsOfGroups {
    @Test(groups = {"android.4"})
    public void f1() {
        System.err.println("groups=android.4 f1...");
    }

    @Test(groups = {"android.5"})
    public void f2() {
        System.err.println("groups=android.5 f2...");
    }

    @Test(groups = {"android.5.2"})
    public void f3() {
        System.err.println("groups=android.5.2 f3...");
    }

    @Test(groups = {"android.6"})
    public void f4() {
        System.err.println("groups=android.6 f4...");
    }
}
