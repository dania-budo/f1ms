package me.daniabudo.formula1ms.application;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                int argumentNumber = args.length;
                if (argumentNumber < 1) {
                    System.out.println("Too few arguments provided!");
                    return;
                }
                if (argumentNumber > 1) {
                    System.out.println("Too many arguments provided!");
                    return;
                }
                switch (args[0]) {
                    case "login" -> {
                        System.out.print("Enter username: ");
                        String username = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String password = scanner.nextLine();

                        User user = AuthManager.login(username, password);
                        if (user != null) {
                            if ("Admin".equalsIgnoreCase(user.getRole())) {
                                AdminMenu.display();  // Admin menu for admin users
                            } else {
                                UserMenu.display();   // User menu for non-admin users
                            }
                        }
                    }
                    case "signup" -> {
                        System.out.print("Enter username: ");
                        String username = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String password = scanner.nextLine();
                        System.out.print("Enter role (User or Admin): ");
                        String role = scanner.nextLine();

                        String adminPassword = "";
                        if ("Admin".equalsIgnoreCase(role)) {
                            System.out.print("Enter admin password: ");
                            adminPassword = scanner.nextLine();
                        }

                        try {
                            AuthManager.signUp(username, password, role, adminPassword);
                            System.out.println("Sign-up successful! You can now log in.");
                        } catch (Exception e) {
                            System.out.println("Sign-up failed: " + e.getMessage());
                        }
                    }
                    default -> System.out.println("Invalid argument! Use 'login' or 'signup'.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }
}
