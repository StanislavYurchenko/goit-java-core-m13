package ua.goit.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ua.goit.http.dto.Comment;
import ua.goit.http.dto.Post;
import ua.goit.http.dto.Todo;
import ua.goit.http.dto.User;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

public class HttpUtil {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private static final String USER_URL = BASE_URL + "/users";
    private static final String POST_URL = BASE_URL + "/posts";

    public static User createUser(User user) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(USER_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(GSON.toJson(user)))
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Create User status code: " + response.statusCode());

        return GSON.fromJson(response.body(), User.class);
    }

    public static User updateUser(int id, User user) throws IOException, InterruptedException {
        user.setId(id);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(USER_URL + "/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(GSON.toJson(user)))
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Update User status code: " + response.statusCode());

        return GSON.fromJson(response.body(), User.class);
    }

    public static boolean deleteUser(int id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(USER_URL + "/" + id))
                .DELETE()
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Delete User status code: " + response.statusCode());

        return response.statusCode() >= 200 && response.statusCode() <= 299 ;
    }

    public static List<User> getAllUsers() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(USER_URL))
                .GET()
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Get All Users status code: " + response.statusCode());

        return GSON.fromJson(response.body(), new TypeToken<List<User>>(){}.getType());
    }

    public static User getUserById(int id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(USER_URL + "/" + id))
                .GET()
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Get User by ID status code: " + response.statusCode());

        return GSON.fromJson(response.body(), User.class);
    }

    public static List<User> getUsersByUsername(String username) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(USER_URL + "?username=" + URLEncoder.encode(username, StandardCharsets.UTF_8)))
                .GET()
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Get User by Name status code: " + response.statusCode());

        return GSON.fromJson(response.body(),  new TypeToken<List<User>>(){}.getType());
    }

    public static List<Post> getPostsByUserId(int userId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(USER_URL + "/" + userId + "/posts"))
                .GET()
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Get Posts by User ID status code: " + response.statusCode());

        return GSON.fromJson(response.body(), new TypeToken<List<Post>>(){}.getType());
    }

    public static List<Comment> getCommentsByPostId(int postId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(POST_URL + "/" + postId + "/comments"))
                .GET()
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Get Comments by Post ID status code: " + response.statusCode());

        return GSON.fromJson(response.body(), new TypeToken<List<Comment>>(){}.getType());
    }

    public static void saveCommentsOfLastPost(int userId) throws IOException, InterruptedException {
        List<Post> posts = getPostsByUserId(userId);

        Post lastPost = posts.stream()
                .max(Comparator.comparingInt(Post::getId))
                .orElseThrow(() -> new RuntimeException("У користувача з id=" + userId + " немає постів"));

        List<Comment> comments = getCommentsByPostId(lastPost.getId());

        comments.forEach(System.out::println);

        String fileName = String.format("user-%d-post-%d-comments.json", userId, lastPost.getId());
        String jsonComments = GSON.toJson(comments);
        Files.writeString(Path.of(fileName), jsonComments);

        System.out.println("Коментарі збережено у файл: " + fileName);
    }

    public static List<Todo> getTodosByUserId(int userId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(USER_URL + "/" + userId + "/todos"))
                .GET()
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Get Todos by User ID status code: " + response.statusCode());

        return GSON.fromJson(response.body(), new TypeToken<List<Todo>>(){}.getType());
    }

    public static void printOpenTodos(int userId) throws IOException, InterruptedException {
        List<Todo> todos = getTodosByUserId(userId);
        todos.stream()
                .filter(todo -> !todo.getCompleted())
                .forEach(System.out::println);
    }
}