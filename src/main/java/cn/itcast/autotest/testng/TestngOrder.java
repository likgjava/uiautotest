package cn.itcast.autotest.testng;

import org.testng.annotations.Test;

/**
 * 执行顺序测试
 * @author likg
 */
public class TestngOrder {
    @Test
    public void one(){
        System.out.println("one...");
    }

    @Test
    public void two(){
        System.out.println("two...");
    }

    @Test
    public void three(){
        System.out.println("three...");
    }

    @Test
    public void four(){
        System.out.println("four...");
    }
}
