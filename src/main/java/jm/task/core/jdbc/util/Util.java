package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String userName = "root";
    private static final String pass = "LiEbheRR$6";
    private static final String url = "jdbc:mysql://127.0.0.1:3306/new_schema";
    private static SessionFactory sf = null;

    public static Connection connectJDBC() throws SQLException, ClassNotFoundException {

        Connection connection = DriverManager.getConnection(url, userName, pass);
        Class.forName("com.mysql.cj.jdbc.Driver");
        return connection;

    }

    public static SessionFactory connectHibernate() {
        if (sf == null) {
            Configuration cfg = new Configuration();
            cfg.addAnnotatedClass(User.class);
            cfg.setProperty("connection.driver_class", "com.mysql.cj.jdbc.Driver");
            cfg.setProperty("hibernate.connection.url", url);
            cfg.setProperty("hibernate.connection.username", userName);
            cfg.setProperty("hibernate.connection.password", pass);
            cfg.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
            cfg.setProperty("hibernate.hbm2ddl.auto", "update");
            StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder()
                    .applySettings(cfg.getProperties());
            return sf = cfg.buildSessionFactory(ssrb.build());
        }


        return sf;

    }


}
