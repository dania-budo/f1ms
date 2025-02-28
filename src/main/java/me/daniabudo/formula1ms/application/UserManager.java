package me.daniabudo.formula1ms.application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private static final String USERS_FILE = "C:\\Users\\Dania Budo\\IdeaProjects\\Formula1\\src\\users.txt";

    // load users from file
    public static List<User> loadUsersFromFile() {
        List<User> users = new ArrayList<>();

        // try reading the file line by line
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                // check if the line contains exactly 3 parts
                if (parts.length == 3) {
                    String username = parts[0].trim();
                    String password = parts[1].trim();
                    String role = parts[2].trim();
                    // create a user object and add it to the list
                    users.add(new User(username, password, role));
                }
            }
        } catch (FileNotFoundException e) {
            // handle file reading errors
            System.out.println("Error reading users from file: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return users;
    }
}
