package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Connection connection = Util.connection();
    public UserDaoJDBCImpl(){

    }
    @Override
    public void createUsersTable() throws SQLException {
        String sql = "CREATE TABLE users (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(50) NOT NULL, " +
                "lastName VARCHAR(50) NOT NULL, " +
                "age INT NOT NULL" +
                ")";

        connection.createStatement().execute(sql);
    }

    @Override
    public void dropUsersTable() {
        try {
            String sql = "DROP TABLE users";
            connection.createStatement().execute(sql);
            System.out.println("База данных удалена");
        } catch (SQLException e) {
            throw new RuntimeException();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
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

    @Override
    public void removeUserById(long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection connection = Util.connection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Пользователь с ID" + id + " удален!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List <User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()){
            while (rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String lastName = rs.getString("lastName");
                Byte age = rs.getByte("age");
                users.add(new User(name, lastName, age, id));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try {
            String sql = "TRUNCATE TABLE users";
            connection.createStatement().execute(sql);
            System.out.println("Таблица пользователей очищена");
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }

}