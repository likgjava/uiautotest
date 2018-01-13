package cn.itcast.autotest.testng;


import org.testng.annotations.Test;

/**
 * 忽略测试
 */
public class Ignore {

    @Test
    public void foo(){
        System.out.println("foo...");
    }

    @Test(enabled = false)
    public void bar(){
        System.out.println("bar...");
    }
}
