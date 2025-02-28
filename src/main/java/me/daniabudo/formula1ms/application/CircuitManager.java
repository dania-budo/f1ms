package me.daniabudo.formula1ms.application;

import me.daniabudo.formula1ms.application.Database.DatabaseConnectionCircuits;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CircuitManager {

    // Sort circuits by length in ascending or descending order
    public static List<Circuit> sortCircuitsByLength(boolean ascending, List<Circuit> circuits) {
        circuits.sort(Comparator.comparingDouble(circuit -> ascending ? circuit.getLengthKm() : -circuit.getLengthKm()));
        return circuits;
    }

    // Fetch circuits from the database
    public static List<Circuit> loadCircuitsFromDatabase() {
        List<Circuit> circuits = new ArrayList<>();

        String query = "SELECT circuitid, name, lengthKm, laps FROM circuits";

        try (Connection conn = DatabaseConnectionCircuits.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("circuitid");
                String name = rs.getString("name");
                double lengthKm = rs.getDouble("lengthKm");
                int laps = rs.getInt("laps");

                Circuit circuit = new Circuit(id, name, lengthKm, laps);
                circuits.add(circuit);
            }
        } catch (Exception e) {
            System.out.println("Error loading circuits from database: " + e.getMessage());
        }


        return circuits;
    }
}
