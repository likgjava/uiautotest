package cn.itcast.autotest.testng;

import org.testng.annotations.Test;

/**
 * 异常测试
 *
 */
public class ExceptionTest {

    @Test(expectedExceptions = ArithmeticException.class)
    public void divByZeroTest() {
        int count = this.div(10, 0);
        System.out.println("count=" + count);
    }

    public int div(int a, int b) {
        return a / b;
    }
}
