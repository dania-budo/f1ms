package me.daniabudo.formula1ms.application;

import me.daniabudo.formula1ms.application.exceptions.InvalidUsernameException;
import me.daniabudo.formula1ms.application.exceptions.InvalidPasswordException;
import me.daniabudo.formula1ms.application.Database.DatabaseConnectionUsers;

import java.sql.*;

public class AuthManager {

    // User login method using JDBC
    public static User login(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection connection = DatabaseConnectionUsers.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String role = resultSet.getString("role");
                return new User(username, password, role);
            }
        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
        }
        return null; // Login failed
    }

    // User sign-up method using JDBC
    public static void signUp(String username, String password, String role, String adminPassword)
            throws InvalidUsernameException, InvalidPasswordException {
        validateUsername(username);
        validatePassword(password);

        if (!role.equalsIgnoreCase("Admin") && !role.equalsIgnoreCase("User")) {
            throw new IllegalArgumentException("Invalid role. Must be 'Admin' or 'User'.");
        }

        if (role.equalsIgnoreCase("Admin") && !"admin123".equals(adminPassword)) {
            throw new IllegalArgumentException("Invalid admin password.");
        }

        if (isUsernameTaken(username)) {
            throw new InvalidUsernameException("Username already exists. Please choose a different username.");
        }

        String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnectionUsers.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, role);

            statement.executeUpdate();
            System.out.println("User registered successfully!");

        } catch (SQLException e) {
            System.out.println("Error during sign-up: " + e.getMessage());
        }
    }

    // Check if username already exists in the database
    public static boolean isUsernameTaken(String username) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";

        try (Connection connection = DatabaseConnectionUsers.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0; // Username exists if count > 0
            }
        } catch (SQLException e) {
            System.out.println("Error checking username availability: " + e.getMessage());
        }

        return false; // Assume username is not taken if there's an error
    }

    // Validation methods (unchanged from your existing code)
    private static void validateUsername(String username) throws InvalidUsernameException {
        if (username.isEmpty() || username.contains(" ") || !username.matches("[a-zA-Z0-9]+")) {
            throw new InvalidUsernameException("Username must contain only letters and numbers, without spaces or special characters.");
        }
    }

    private static void validatePassword(String password) throws InvalidPasswordException {
        if (password.length() < 4 || !password.matches("[a-zA-Z0-9]+")) {
            throw new InvalidPasswordException("Password must be at least 4 characters long and contain only letters and numbers.");
        }
    }
}

