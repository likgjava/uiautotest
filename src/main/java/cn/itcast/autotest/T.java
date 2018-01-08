package cn.itcast.autotest;

import org.testng.annotations.Test;

import java.util.Calendar;

public class T {

    @Test
    public void t() {
        String userDir = System.getProperty("user.dir");
        System.out.println("userDir====" + userDir);

        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DAY_OF_YEAR, 1);

        int i = cal.get(Calendar.DAY_OF_WEEK);
        System.out.println(i);

    }

}
