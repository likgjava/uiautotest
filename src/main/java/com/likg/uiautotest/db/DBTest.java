package com.likg.uiautotest.db;

import com.mysql.jdbc.Driver;
import org.testng.annotations.Test;

import java.sql.*;

public class DBTest {

    @Test
    public void select() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/appium";
        String username = "root";
        String pwd = "root";
        Connection conn = DriverManager.getConnection(url, username, pwd);

        String sql = "SELECT * FROM student";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int age = resultSet.getInt("age");
            System.out.println("id=" + id + " name=" + name + " age=" + age);
        }

        resultSet.close();
        preparedStatement.close();
        conn.close();
    }

    @Test
    public void insert() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/appium";
        String username = "root";
        String pwd = "root";
        Connection conn = DriverManager.getConnection(url, username, pwd);

        String sql = "INSERT INTO student(name,age) VALUES (?,?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setString(1, "王五");
        preparedStatement.setInt(2, 50);
        int updateCount = preparedStatement.executeUpdate();
        System.out.println("updateCount=" + updateCount);

        preparedStatement.close();
        conn.close();
    }

    @Test
    public void update() throws Exception {
        Connection conn = DBUtil.getConnection();

        String sql = "UPDATE student SET age=? WHERE id=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setInt(1, 500);
        preparedStatement.setInt(2, 3);
        int updateCount = preparedStatement.executeUpdate();
        System.out.println("updateCount=" + updateCount);

        preparedStatement.close();
        conn.close();
    }

    @Test
    public void delete() throws Exception {
        Connection conn = DBUtil.getConnection();

        String sql = "DELETE FROM student WHERE id=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setInt(1, 3);
        int updateCount = preparedStatement.executeUpdate();
        System.out.println("updateCount=" + updateCount);

        preparedStatement.close();
        conn.close();
    }
}
