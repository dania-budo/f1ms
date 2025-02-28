package me.daniabudo.formula1ms.application;

import java.io.*;
import java.util.Scanner;

import static java.lang.System.exit;

public class AdminMenu {

    public static void display() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add Team");
            System.out.println("2. Remove Team");
            System.out.println("3. Add Driver");
            System.out.println("4. Remove Driver");
            System.out.println("5. Add Circuit");
            System.out.println("6. Remove Circuit");
            System.out.println("7. Exit");

            System.out.print("Enter your choice: ");
            int choice = getValidChoice(scanner);

            switch (choice) {
                case 1 -> addTeam(scanner);
                case 2 -> removeTeam(scanner);
                case 3 -> addDriver(scanner);
                case 4 -> removeDriver(scanner);
                case 5 -> addCircuit(scanner);
                case 6 -> removeCircuit(scanner);
                case 7 -> {
                    System.out.println("Exiting...");
                    exit(0);
                }
            }
        }
    }

    // gets a valid menu choice from the admin user
    public static int getValidChoice(Scanner scanner) {
        int choice;
        while (true) {
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // clears newline
                if (choice >= 1 && choice <= 7) {
                    return choice;
                } else {
                    System.out.println("Invalid choice. Please enter a number between 1 and 7.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // clears invalid input
            }
        }
    }

    // adds a team to the teams file
    public static void addTeam(Scanner scanner) {
        int id = getValidUniqueId(scanner, "team");
        String name = getValidString(scanner, "Enter Team Name: ");
        String country = getValidString(scanner, "Enter Country: ");
        String principal = getValidString(scanner, "Enter Principal: ");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Dania Budo\\IdeaProjects\\Formula1\\src\\teams.txt", true))) {
            writer.write(id + "," + name + "," + country + "," + principal);
            writer.newLine();
            System.out.println("Team added successfully!");
        } catch (IOException e) {
            System.out.println("Error adding team: " + e.getMessage());
        }
    }

    // removes a team from the teams file based on team id
    public static void removeTeam(Scanner scanner) {
        int teamId = getValidId(scanner, "Enter Team ID to remove: ");
        File file = new File("C:\\Users\\Dania Budo\\IdeaProjects\\Formula1\\src\\teams.txt");
        File tempFile = new File("C:\\Users\\Dania Budo\\IdeaProjects\\Formula1\\src\\teams_temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (Integer.parseInt(parts[0]) != teamId) {
                    writer.write(line);
                    writer.newLine();
                } else {
                    found = true;
                }
            }
            if (found) {
                System.out.println("Team removed successfully!");
            } else {
                System.out.println("Team ID not found.");
            }
        } catch (IOException e) {
            System.out.println("Error removing team: " + e.getMessage());
        }

        // replace original file with the temp file if deletion and renaming succeed
        if (!file.delete()) {
            System.out.println("Could not delete the original file.");
        }
        if (!tempFile.renameTo(file)) {
            System.out.println("Could not rename the temporary file.");
        }
    }

    // adds a driver to the drivers file
    public static void addDriver(Scanner scanner) {
        int id = getValidUniqueId(scanner, "driver");
        String name = getValidString(scanner, "Enter Driver Name: ");
        String nationality = getValidString(scanner, "Enter Nationality: ");
        int teamId = getValidId(scanner, "Enter Team ID: ");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Dania Budo\\IdeaProjects\\Formula1\\src\\drivers.txt", true))) {
            writer.write(id + "," + name + "," + nationality + "," + teamId);
            writer.newLine();
            System.out.println("Driver added successfully!");
        } catch (IOException e) {
            System.out.println("Error adding driver: " + e.getMessage());
        }
    }

    // removes a driver from the drivers file based on driver id
    public static void removeDriver(Scanner scanner) {
        int driverId = getValidId(scanner, "Enter Driver ID to remove: ");
        File file = new File("C:\\Users\\Dania Budo\\IdeaProjects\\Formula1\\src\\drivers.txt");
        File tempFile = new File("C:\\Users\\Dania Budo\\IdeaProjects\\Formula1\\src\\drivers_temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (Integer.parseInt(parts[0]) != driverId) {
                    writer.write(line);
                    writer.newLine();
                } else {
                    found = true;
                }
            }
            if (found) {
                System.out.println("Driver removed successfully!");
            } else {
                System.out.println("Driver ID not found.");
            }
        } catch (IOException e) {
            System.out.println("Error removing driver: " + e.getMessage());
        }

        if (!file.delete()) {
            System.out.println("Could not delete the original file.");
        }
        if (!tempFile.renameTo(file)) {
            System.out.println("Could not rename the temporary file.");
        }
    }

    // adds a circuit to the circuits file
    public static void addCircuit(Scanner scanner) {
        int id = getValidUniqueId(scanner, "circuit");
        String name = getValidString(scanner, "Enter Circuit Name: ");
        double length = getValidDouble(scanner, "Enter Circuit Length (in km): ");
        int laps = getValidId(scanner, "Enter Laps: ");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Dania Budo\\IdeaProjects\\Formula1\\src\\circuits.txt", true))) {
            writer.write(id + "," + name + "," + length + "," + laps);
            writer.newLine();
            System.out.println("Circuit added successfully!");
        } catch (IOException e) {
            System.out.println("Error adding circuit: " + e.getMessage());
        }
    }

    // removes a circuit from the circuits file based on circuit id
    public static void removeCircuit(Scanner scanner) {
        int circuitId = getValidId(scanner, "Enter Circuit ID to remove: ");
        File file = new File("C:\\Users\\Dania Budo\\IdeaProjects\\Formula1\\src\\circuits.txt");
        File tempFile = new File("C:\\Users\\Dania Budo\\IdeaProjects\\Formula1\\src\\circuits_temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (Integer.parseInt(parts[0]) != circuitId) {
                    writer.write(line);
                    writer.newLine();
                } else {
                    found = true;
                }
            }
            if (found) {
                System.out.println("Circuit removed successfully!");
            } else {
                System.out.println("Circuit ID not found.");
            }
        } catch (IOException e) {
            System.out.println("Error removing circuit: " + e.getMessage());
        }

        if (!file.delete()) {
            System.out.println("Could not delete the original file.");
        }
        if (!tempFile.renameTo(file)) {
            System.out.println("Could not rename the temporary file.");
        }
    }

    // validates a positive integer input for id
    public static int getValidId(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                int id = scanner.nextInt();
                scanner.nextLine(); // clears newline
                if (id > 0) {
                    return id;
                } else {
                    System.out.println("ID must be a positive integer. Try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next(); // clears invalid input
            }
        }
    }

    // validates double input (e.g., for circuit length)
    public static double getValidDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextDouble()) {
                double value = scanner.nextDouble();
                scanner.nextLine(); // clears newline
                return value;
            } else {
                System.out.println("Invalid input. Please enter a valid decimal number.");
                scanner.next(); // clears invalid input
            }
        }
    }

    // validates non-empty string input
    public static String getValidString(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            } else {
                System.out.println("Input cannot be empty. Please try again.");
            }
        }
    }

    // generates a unique id by checking existing records
    public static int getValidUniqueId(Scanner scanner, String type) {
        int id;
        while (true) {
            id = getValidId(scanner, "Enter " + type + " ID: ");
            if (isUniqueId(id, type)) {
                return id;
            } else {
                System.out.println(type + " ID already exists. Please enter a unique ID.");
            }
        }
    }

    // checks if the id is unique
    public static boolean isUniqueId(int id, String type) {
        String fileName = switch (type) {
            case "team" -> "C:\\Users\\Dania Budo\\IdeaProjects\\Formula1\\src\\teams.txt";
            case "driver" -> "C:\\Users\\Dania Budo\\IdeaProjects\\Formula1\\src\\drivers.txt";
            case "circuit" -> "C:\\Users\\Dania Budo\\IdeaProjects\\Formula1\\src\\circuits.txt";
            default -> "";
        };

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (Integer.parseInt(parts[0]) == id) {
                    return false;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading " + type + " data: " + e.getMessage());
        }
        return true;
    }
}
