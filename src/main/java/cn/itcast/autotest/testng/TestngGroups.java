package cn.itcast.autotest.testng;

import org.testng.annotations.Test;

/**
 * 组测试
 *
 * @author likg
 */
public class TestngGroups {
    @Test(groups = {"g1"})
    public void f1() {
        System.err.println("groups=g1 f1...");
    }

    @Test(groups = {"g2"})
    public void f2() {
        System.err.println("groups=g2 f2...");
    }

    @Test(groups = {"g1", "g2"})
    public void f3() {
        System.err.println("groups=g1,g2 f3...");
    }

    @Test(groups = {"g1", "g2"})
    public void f4() {
        System.err.println("groups=g1,g2 f4...");
    }
}
