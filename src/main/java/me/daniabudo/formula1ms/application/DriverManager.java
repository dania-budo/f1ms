package me.daniabudo.formula1ms.application;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DriverManager {
    private static final String DRIVER_FILE = "C:\\Users\\Dania Budo\\IdeaProjects\\Formula1\\src\\drivers.txt";

    // Method to load drivers from file
    public static List<Driver> loadDriversFromFile() {
        List<Driver> drivers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(DRIVER_FILE))) {
            String line;
            // Read each line from the file
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                // Ensure there are enough parts to create a driver
                if (parts.length >= 3) {
                    Integer id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    String nationality = parts[2].trim();
                    Integer teamId = parts.length == 4 ? Integer.parseInt(parts[3].trim()) : null;

                    Driver driver = new Driver(name, id, nationality, teamId);
                    drivers.add(driver); // Add driver to the list
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading drivers from file: " + e.getMessage());
        }
        return drivers;
    }

    public BufferedReader getBufferedReader(File file) throws IOException {
        return new BufferedReader(new FileReader(file));
    }
}
