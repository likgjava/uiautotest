package cn.itcast.autotest.testng;


import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 断言测试
 */
public class AssertTest {
    @Test
    public void foo(){
        int sum = this.add(1, 1);
        Assert.assertEquals(sum, 2);
        Assert.assertNotEquals(sum, 3);
        Assert.assertTrue(true);
        Assert.assertFalse(false);
    }

    public int add(int a, int b){
        return a + b;
    }
}
