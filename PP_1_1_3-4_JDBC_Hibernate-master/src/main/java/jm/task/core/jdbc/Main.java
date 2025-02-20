package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService service = new UserServiceImpl();
        service.createUsersTable();
        service.saveUser("Sasha", "Beliy", (byte) 40);
        service.saveUser("Peter", "parker", (byte) 23);
        service.saveUser("Vladislav", "Ivanov", (byte) 23);
        List<User> users = service.getAllUsers();
        System.out.println("Список юзеров:");
        for (User user : users) {
            System.out.println(user);
        }
        service.getAllUsers();
        service.cleanUsersTable();
        service.dropUsersTable();

    }
}
