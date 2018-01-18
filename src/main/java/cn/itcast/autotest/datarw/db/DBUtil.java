package cn.itcast.autotest.datarw.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {

    public static Connection getConnection(String url, String username, String pwd) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(url, username, pwd);
    }

    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/appium";
        String username = "root";
        String pwd = "root";
        return DriverManager.getConnection(url, username, pwd);
    }
}
