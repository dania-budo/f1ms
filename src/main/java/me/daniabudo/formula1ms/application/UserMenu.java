package me.daniabudo.formula1ms.application;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static java.lang.System.exit;

public class UserMenu {

    // main menu method
    public static void display() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. View Drivers");
            System.out.println("2. View Teams");
            System.out.println("3. View Races");
            System.out.println("4. View Circuits");
            System.out.println("5. View Motorsport News");
            System.out.println("6. Exit");

            int choice = getValidChoice(scanner, 1, 5); // get a valid choice

            switch (choice) {
                case 1 -> viewDrivers();
                case 2 -> viewTeams();
                case 3 -> viewRaces();
                case 4 -> viewCircuits();
                case 5 -> viewF1News(); // Call the new method for news
                case 6 -> {
                    System.out.println("Exiting...");
                    throw new ExitMenuException("Exit option selected");
                }
            }
        }
    }

    // Method to view and filter F1 News
    public static void viewF1News() {
        List<String> newsArticles = F1NewsFetcher.fetchNews();
        if (newsArticles.isEmpty()) {
            System.out.println("⚠️ No news available.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nOptions:");
        System.out.println("1. Show all news");
        System.out.println("2. Search news by keyword");

        int choice = getValidChoice(scanner, 1, 2);

        switch (choice) {
            case 1 -> newsArticles.forEach(news -> {
                System.out.println(news);
                System.out.println("-------------------------");
            });

            case 2 -> {
                System.out.print("Enter keyword to search for in news: ");
                String keyword = scanner.nextLine();
                newsArticles.stream()
                        .filter(news -> news.toLowerCase().contains(keyword.toLowerCase()))
                        .forEach(news -> {
                            System.out.println(news);
                            System.out.println("-------------------------");
                        });
            }
        }

        System.out.println("\nPress Enter to return to the menu...");
        scanner.nextLine();
    }


    // helper method to get a valid choice within range
    private static int getValidChoice(Scanner scanner, int min, int max) {
        int choice;
        while (true) {
            System.out.print("Choose an option (" + min + "-" + max + "): ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
                if (choice >= min && choice <= max) {
                    return choice;
                } else {
                    System.out.println("Please choose a number between " + min + " and " + max + ".");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // consume invalid input
            }
        }
    }

    // Levenshtein distance for fuzzy search
     public static int levenshteinDistance(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = Math.min(Math.min(
                                    dp[i - 1][j] + 1,
                                    dp[i][j - 1] + 1),
                            dp[i - 1][j - 1] + (s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1)
                    );
                }
            }
        }
        return dp[s1.length()][s2.length()];
    }

    // method to view and search drivers
    public static void viewDrivers() {
        List<Driver> drivers = DriverManager.loadDriversFromFile();
        if (drivers.isEmpty()) {
            System.out.println("No drivers found.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Options:");
        System.out.println("1. Show all drivers");
        System.out.println("2. Sort drivers by name");
        System.out.println("3. Search by nationality");
        System.out.println("4. Search drivers by name fuzzy search"); // New option

        int choice = getValidChoice(scanner, 1, 4);

        switch (choice) {
            case 1 -> drivers.forEach(driver -> System.out.println(driver.getPresentation()));
            case 2 -> {
                drivers.sort((d1, d2) -> d1.name.compareToIgnoreCase(d2.name));
                drivers.forEach(driver -> System.out.println(driver.getPresentation()));
            }
            case 3 -> {
                System.out.print("Enter nationality to search for: ");
                String nationality = scanner.nextLine();
                drivers.stream()
                        .filter(driver -> driver.nationality.equalsIgnoreCase(nationality))
                        .forEach(driver -> System.out.println(driver.name));
            }
            case 4 -> searchDriversByFuzzyName(drivers); // New case for fuzzy search
        }
    }


    // Fuzzy search method for drivers
    private static void searchDriversByFuzzyName(List<Driver> drivers) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter driver name to search for: ");
        String searchName = scanner.nextLine();

        Driver closestMatch = null;
        int minDistance = Integer.MAX_VALUE;

        for (Driver driver : drivers) {
            int distance = levenshteinDistance(searchName.toLowerCase(), driver.name.toLowerCase());
            if (distance < minDistance) {
                minDistance = distance;
                closestMatch = driver;
            }
        }

        if (closestMatch != null) {
            System.out.println("Closest match: " + closestMatch.getPresentation());
        } else {
            System.out.println("No close match found.");
        }
    }

    // method to view and search teams
    public static void viewTeams() {
        List<Team> teams;
        try {
            teams = TeamManager.loadTeamsFromFile();
            if (teams.isEmpty()) {
                System.out.println("No teams found.");
                return;
            }
        } catch (Exception e) {
            System.out.println("Error loading teams: " + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Options:");
        System.out.println("1. Show all teams");
        System.out.println("2. Sort teams by name");
        System.out.println("3. Search by country");

        int choice = getValidChoice(scanner, 1, 3);

        switch (choice) {
            case 1 -> teams.forEach(team -> System.out.println(team.getPresentation()));
            case 2 -> {
                teams.sort((t1, t2) -> t1.name.compareToIgnoreCase(t2.name));
                teams.forEach(team -> System.out.println(team.getPresentation()));
            }
            case 3 -> {
                System.out.print("Enter country to search for: ");
                String country = scanner.nextLine();
                teams.stream()
                        .filter(team -> team.location != null && team.location.country.equalsIgnoreCase(country))
                        .forEach(team -> System.out.println(team.getPresentation()));
            }
        }
    }

    public static class ExitMenuException extends RuntimeException {
        public ExitMenuException(String message) {
            super(message);
        }
    }

    // method to view and search races
    public static void viewRaces() {
        List<Race> races = RaceManager.loadRacesFromFile();
        if (races.isEmpty()) {
            System.out.println("No races found.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Options:");
        System.out.println("1. Show all races");
        System.out.println("2. Sort races by date");
        System.out.println("3. Group by points");


        int choice = getValidChoice(scanner, 1, 3);

        switch (choice) {
            case 1 -> races.forEach(race -> System.out.println(race.getPresentation()));
            case 2 -> {
                races.sort(Race::compareTo);
                races.forEach(race -> System.out.println(race.getPresentation()));
            }
            case 3 -> {
                Map<Integer, List<Race>> groupedRaces = RaceManager.groupRacesByPoints(races);
                groupedRaces.forEach((points, raceList) -> {
                    System.out.println("\npoints: " + points);
                    raceList.forEach(race -> System.out.println(race.getPresentation()));
                });
            }
        }
    }

    // method to view and search circuits
    public static void viewCircuits() {
        List<Circuit> circuits = CircuitManager.loadCircuitsFromDatabase(); // Load from database
        if (circuits.isEmpty()) {
            System.out.println("No circuits found.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Options:");
        System.out.println("1. Show all circuits");
        System.out.println("2. Sort circuits by length");
        System.out.println("3. Show dizziness level for each circuit");

        int choice = getValidChoice(scanner, 1, 3);

        switch (choice) {
            case 1 -> circuits.forEach(circuit ->
                    System.out.println(circuit.getName() + ", " + circuit.getLengthKm() + " km"));
            case 2 -> {
                // Use sortCircuitsByLength from CircuitManager for ascending sort
                circuits = CircuitManager.sortCircuitsByLength(true, circuits);
                circuits.forEach(circuit ->
                        System.out.println(circuit.getName() + ", " + circuit.getLengthKm() + " km"));
            }
            case 3 -> circuits.forEach(circuit ->
                    System.out.println(circuit.getName() + ": " + circuit.getDizzinessLevel()));
        }
    }
}
