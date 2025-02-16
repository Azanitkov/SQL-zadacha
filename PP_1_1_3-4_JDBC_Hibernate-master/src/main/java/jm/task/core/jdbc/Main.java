package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pp", "root", "12175vlaDd!");
            createUserTable(connection);
            addUser(connection, "Yaroslav", "Sirnik", (byte) 23);
            addUser(connection, "Sasha", "Beliy", (byte) 40);
            addUser(connection, "Peter", "parker", (byte) 23);
            addUser(connection, "Vladislav", "Ivanov", (byte) 23);
            PrintUsers(connection);
            cleanUsersTable(connection);
            dropUserTable(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void createUserTable(Connection connection) throws SQLException {
        String sql = "CREATE TABLE users (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(50) NOT NULL, " +
                "lastName VARCHAR(50) NOT NULL, " +
                "age INT NOT NULL" +
                ")";

        connection.createStatement().execute(sql);
    }

    public static void dropUserTable(Connection connection) throws SQLException {
        String sql = "DROP TABLE users";
        connection.createStatement().execute(sql);
        System.out.println("База данных удалена");
    }

    public static void addUser(Connection connection, String name, String lastName, byte age) throws SQLException {
        String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, lastName);
            stmt.setByte(3, age);
            stmt.execute();
            System.out.println("User с именем [" + name + "] добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void cleanUsersTable(Connection connection) throws SQLException {
        String sql = "TRUNCATE TABLE users";
        connection.createStatement().execute(sql);
        System.out.println("Таблица пользователей очищена");
    }

    public static void PrintUsers(Connection connection) throws SQLException {
        String sql = "SELECT * FROM users";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            System.out.println("Список юзеров в базе данных: ");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String lastName = rs.getString("lastName");
                byte age = rs.getByte("age");
                System.out.println("User {ID: " + id + ", Name: " + name + ", last Name: " + lastName + ", Age: " + age + "}");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    static UserService userService = new UserServiceImpl();


}
