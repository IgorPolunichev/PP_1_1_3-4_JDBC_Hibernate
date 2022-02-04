package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class Util {
    // реализуйте настройку соеденения с БД
    public static Connection connect() throws SQLException, ClassNotFoundException {
        String userName = "root";
        String pass = "LiEbheRR$6";
        String url = "jdbc:mysql://127.0.0.1:3306/new_schema";
        Connection connection = DriverManager.getConnection(url, userName, pass);
        Class.forName("com.mysql.cj.jdbc.Driver");
        return connection;

    }


}
