package ma.daniabudo.formula1ms.application;

import me.daniabudo.formula1ms.application.Circuit;
import me.daniabudo.formula1ms.application.CircuitManager;
import me.daniabudo.formula1ms.application.Database.DatabaseConnectionCircuits;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CircuitManagerTest {

    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    public void setUp() {
        // Mocking the database connections
        mockConnection = Mockito.mock(Connection.class);
        mockPreparedStatement = Mockito.mock(PreparedStatement.class);
        mockResultSet = Mockito.mock(ResultSet.class);

        // Mocking the static method call to DatabaseConnectionCircuits.getConnection
        try {
            when(DatabaseConnectionCircuits.getConnection()).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSortCircuitsByLengthAscending() {
        Circuit circuit1 = new Circuit(1, "Circuit A", 5.0, 50);
        Circuit circuit2 = new Circuit(2, "Circuit B", 7.5, 60);
        Circuit circuit3 = new Circuit(3, "Circuit C", 4.5, 70);

        List<Circuit> circuits = Arrays.asList(circuit1, circuit2, circuit3);

        List<Circuit> sortedCircuits = CircuitManager.sortCircuitsByLength(true, circuits);

        assertEquals(4.5, sortedCircuits.get(0).getLengthKm());
        assertEquals(5.0, sortedCircuits.get(1).getLengthKm());
        assertEquals(7.5, sortedCircuits.get(2).getLengthKm());
    }

    @Test
    public void testSortCircuitsByLengthDescending() {
        Circuit circuit1 = new Circuit(1, "Circuit A", 5.0, 50);
        Circuit circuit2 = new Circuit(2, "Circuit B", 7.5, 60);
        Circuit circuit3 = new Circuit(3, "Circuit C", 4.5, 70);

        List<Circuit> circuits = Arrays.asList(circuit1, circuit2, circuit3);

        List<Circuit> sortedCircuits = CircuitManager.sortCircuitsByLength(false, circuits);

        assertEquals(7.5, sortedCircuits.get(0).getLengthKm());
        assertEquals(5.0, sortedCircuits.get(1).getLengthKm());
        assertEquals(4.5, sortedCircuits.get(2).getLengthKm());
    }

    @Test
    public void testLoadCircuitsFromDatabase() throws SQLException {
        // Mocking the ResultSet behavior
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("circuitid")).thenReturn(1).thenReturn(2);
        when(mockResultSet.getString("name")).thenReturn("Circuit A").thenReturn("Circuit B");
        when(mockResultSet.getDouble("lengthKm")).thenReturn(5.0).thenReturn(7.5);
        when(mockResultSet.getInt("laps")).thenReturn(50).thenReturn(60);

        // Call the method to test
        List<Circuit> circuits = CircuitManager.loadCircuitsFromDatabase();

        assertNotNull(circuits);
        assertEquals(2, circuits.size());
        assertEquals("Circuit A", circuits.get(0).getName());
        assertEquals(5.0, circuits.get(0).getLengthKm());
        assertEquals("Circuit B", circuits.get(1).getName());
        assertEquals(7.5, circuits.get(1).getLengthKm());
    }

    @Test
    public void testLoadCircuitsFromDatabase_HandleError() throws SQLException {
        // Simulating a database error
        when(mockPreparedStatement.executeQuery()).thenThrow(new SQLException("Database error"));

        List<Circuit> circuits = CircuitManager.loadCircuitsFromDatabase();

        assertNotNull(circuits);
        assertTrue(circuits.isEmpty());
    }
}

