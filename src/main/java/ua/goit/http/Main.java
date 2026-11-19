package ua.goit.http;

import ua.goit.http.dto.User;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("=== 1: (POST) ===");
        User newUser = new User();
        newUser.setName("Stanislav Chernenko");
        newUser.setUsername("stanislav_dev");
        newUser.setEmail("stanislav@example.com");
        newUser.setPhone("1-770-736-8031");
        newUser.setWebsite("stanislav.dev");

        User createdUser = HttpUtil.createUser(newUser);
        System.out.println(createdUser);

        System.out.println("\n=== 1: (PUT) ===");
        User userToUpdate = HttpUtil.getUserById(1);
        userToUpdate.setName("John Doe");
        userToUpdate.setUsername("john_updated");

        User updatedUser = HttpUtil.updateUser(1, userToUpdate);
        System.out.println(updatedUser);

        System.out.println("\n=== 1: (DELETE) ===");
        boolean isDeleted = HttpUtil.deleteUser(1);
        System.out.println("Результат видалення: " + isDeleted);

        System.out.println("\n=== 1: (GET ALL) ===");
        List<User> allUsers = HttpUtil.getAllUsers();
        System.out.println("Знайдено користувачів: " + allUsers.size());
        allUsers.forEach(System.out::println);

        System.out.println("\n=== 1: (GET BY ID) ===");
        User userById = HttpUtil.getUserById(2);
        System.out.println(userById);

        System.out.println("\n=== 1: (GET BY NAME) ===");
        List<User> usersByUsername = HttpUtil.getUsersByUsername("Bret");
        usersByUsername.forEach(System.out::println);

        System.out.println("\n=== 2: (SAVE COMMENTS) ===");
        HttpUtil.saveCommentsOfLastPost(1);

        System.out.println("\n=== 3: (PRINT OPEN TODOS) ===");
        HttpUtil.printOpenTodos(1);

    }
}