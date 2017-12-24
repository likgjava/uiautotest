package com.likg.uiautotest.property;

import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesTest {

    @Test
    public void read() throws IOException {
        Properties prop = new Properties();
        prop.load(new FileInputStream("data/conf.properties"));

        String username = prop.getProperty("username");
        String password = prop.getProperty("password");
        String host = prop.getProperty("host", "localhost");
        System.out.println(username);
        System.out.println(password);
        System.out.println(host);
    }

    @Test
    public void write() throws IOException {
        Properties prop = new Properties();
        prop.load(new FileInputStream("data/conf.properties"));

        prop.setProperty("username", "test");
        prop.setProperty("host", "www.baidu.com");

        prop.store(new FileOutputStream("data/conf.properties"), "sys config");
    }
}
