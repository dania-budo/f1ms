package me.daniabudo.formula1ms.application;

import java.io.*;
import java.util.*;

public class TeamManager {

    private static final String TEAM_FILE = "C:\\Users\\Dania Budo\\IdeaProjects\\Formula1\\src\\teams.txt";

    // Load teams from file
    public static List<Team> loadTeamsFromFile() {
        List<Team> teams = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(TEAM_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",\\s*");
                if (parts.length == 7) {
                    try {
                        Location location = new Location(
                                parts[1].trim(),
                                parts[2].trim(),
                                parts[3].trim(),
                                Integer.parseInt(parts[4].trim())
                        );
                        Team team = new Team(location, parts[5].trim(), parts[6].trim(), Integer.parseInt(parts[0].trim()));
                        teams.add(team);
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing number in line: " + line);
                    }
                } else {
                    System.out.println("Skipping invalid line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading teams from file: " + e.getMessage());
        }
        return teams;
    }
}
