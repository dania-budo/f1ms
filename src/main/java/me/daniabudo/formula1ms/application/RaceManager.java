package me.daniabudo.formula1ms.application;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

public class RaceManager {

    private static final String RACE_FILE = "C:\\Users\\Dania Budo\\IdeaProjects\\Formula1\\src\\races.txt";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    // Load races from a file
    public static List<Race> loadRacesFromFile() {
        List<Race> races = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(RACE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",\\s*");
                if (parts.length == 4) {
                    try {
                        int id = Integer.parseInt(parts[0].trim());
                        String name = parts[1].trim();
                        LocalDateTime date = LocalDateTime.parse(parts[2].trim(), DATE_TIME_FORMATTER);
                        int points = Integer.parseInt(parts[3].trim());

                        Race race = new Race(id, name, date, points);
                        races.add(race);
                    } catch (NumberFormatException | DateTimeParseException e) {
                        System.out.println("Invalid data format in line: " + line);
                    }
                } else {
                    System.out.println("Skipping invalid line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading races from file: " + e.getMessage());
        }

        return races;
    }

    // Group races by points
    public static Map<Integer, List<Race>> groupRacesByPoints(List<Race> races) {
        return races.stream()
                .collect(Collectors.groupingBy(race -> race.points));
    }

}
