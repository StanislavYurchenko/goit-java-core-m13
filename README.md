# Java Core: Module 13 - HTTP & REST API

This repository contains practical assignments for Module 13 of the GoIT Java Core course, focusing on interacting with RESTful APIs, executing HTTP requests, and processing JSON data.

## Project Description

The project interacts with the public mock API [JSONPlaceholder](https://jsonplaceholder.typicode.com/) and demonstrates how to build a robust HTTP client using modern Java features and the Gson library.

The application is divided into three main tasks implemented in `ua.goit.http.HttpUtil`:

### Task 1: CRUD Operations
Implementation of 6 methods to interact with the `/users` endpoint:
* **Create:** Sends a `POST` request with a new `User` JSON object.
* **Update:** Sends a `PUT` request to update an existing user by ID.
* **Delete:** Sends a `DELETE` request and verifies a `2xx` success status code.
* **Get All:** Retrieves a list of all users via `GET`.
* **Get by ID:** Retrieves a single user by their ID.
* **Get by Username:** Retrieves a user using a query parameter (`?username=...`).

### Task 2: Fetch and Save Comments
* Retrieves all posts for a specific user.
* Finds the last post (the one with the maximum `id`).
* Fetches all comments for this specific post.
* Serializes the comments into formatted JSON and saves them to a local file named `user-X-post-Y-comments.json` (where X is the user ID and Y is the post ID).

### Task 3: Filter Open Todos
* Retrieves all to-do items for a specific user.
* Filters and prints only the "open" tasks (where `completed` status is `false`).

## Project Structure

* `ua.goit.http.dto` — Data Transfer Objects (`User`, `Post`, `Comment`, `Todo`) designed to perfectly map the JSON structure. It includes nested classes like `Address`, `Geo`, and `Company`.
* `ua.goit.http.HttpUtil` — A utility class containing all HTTP request logic and URI configurations.
* `ua.goit.http.Main` — The entry point of the program that sequentially tests all endpoints and tasks.

## Run Instructions

1. Clone the repository:
   ```bash
   git clone <your_repository_link>
   ```
2. Open the project in your preferred IDE (e.g., IntelliJ IDEA).
3. Ensure Maven reloads the dependencies (Gson).
4. Run the `Main.java` class. 
5. Check the console output for the API responses and look for the generated `.json` file in the root directory.

## Tech Stack

* **Java 11+** (using the native `java.net.http.HttpClient`)
* **Maven** (build and dependency management)
* **Gson** (Google library for JSON serialization/deserialization)