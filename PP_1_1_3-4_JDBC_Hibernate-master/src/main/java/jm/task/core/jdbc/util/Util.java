package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Util {
    public static String url = "jdbc:mysql://localhost:3306/pp";
    public static String user = "root";
    public static String password = "12175vlaDd!";
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Properties properties = new Properties();
            properties.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            properties.setProperty("hibernate.connection.url", url);
            properties.setProperty("hibernate.connection.username", user);
            properties.setProperty("hibernate.connection.password", password);
            properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
            properties.setProperty("hibernate.hbm2ddl.auto", "update"); // или "create" для пересоздания схемы
            properties.setProperty("hibernate.show_sql", "true");

            return new Configuration()
                    .addProperties(properties)
                    .addAnnotatedClass(User.class) // Добавь свою сущность
                    .buildSessionFactory();
        } catch (Throwable e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Connection connection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при подключении к БД через JDBC", e);
        }
    }
    }



