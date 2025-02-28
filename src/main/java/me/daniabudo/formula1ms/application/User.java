package me.daniabudo.formula1ms.application;

public class User {
    private String username;
    private String password;
    private String role;

    // Constructor to initialize user attributes
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getter for username
    public String getUsername() {
        return username;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }

    // Getter for role
    public String getRole() {
        return role;
    }

    // Check if provided credentials match the stored ones
    public boolean matchesCredentials(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    // Method to check if the user is an admin
    public boolean isAdmin() {
        return "Admin".equalsIgnoreCase(this.role);
    }
}
