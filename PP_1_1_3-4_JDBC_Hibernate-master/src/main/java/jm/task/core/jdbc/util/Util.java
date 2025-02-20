package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    public static Connection connection(){
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/pp", "root", "12175vlaDd!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
