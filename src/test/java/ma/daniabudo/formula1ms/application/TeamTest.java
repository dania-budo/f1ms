package ma.daniabudo.formula1ms.application;

import me.daniabudo.formula1ms.application.Location;
import me.daniabudo.formula1ms.application.Team;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TeamTest {

    // Test for constructor and getters
    @Test
    public void testTeamConstructorAndGetters() {
        // Arrange
        Location location = new Location("Germany", "Munich", "Kaiserstrasse", 10);
        String name = "Ferrari";
        String principal = "Mattia Binotto";
        Integer id = 1;

        // Act
        Team team = new Team(location, name, principal, id);

        // Assert
        assertEquals(location, team.getLocation(), "Location should be the same as initialized");
        assertEquals(name, team.getName(), "Name should be the same as initialized");
        assertEquals(id, team.getId(), "ID should be the same as initialized");
        assertEquals(principal, team.getPrincipal(), "Principal should be the same as initialized");
    }

    // Test for getPresentation method
    @Test
    public void testGetPresentation() {
        // Arrange
        Location location = new Location("Germany", "Munich", "Kaiserstrasse", 10);
        String name = "Ferrari";
        String principal = "Mattia Binotto";
        Integer id = 1;
        Team team = new Team(location, name, principal, id);

        // Act
        String presentation = team.getPresentation();

        // Assert
        String expectedPresentation = "We are Ferrari! Our principal is Mattia Binotto and we are located in 10 Kaiserstrasse, Munich, Germany.";
        assertEquals(expectedPresentation, presentation, "Presentation should match the expected format");
    }

    // Test for toString method
    @Test
    public void testToString() {
        // Arrange
        Location location = new Location("Germany", "Munich", "Kaiserstrasse", 10);
        String name = "Ferrari";
        String principal = "Mattia Binotto";
        Integer id = 1;
        Team team = new Team(location, name, principal, id);

        // Act
        String teamString = team.toString();

        // Assert
        String expectedString = "Team ID: 1, Name: Ferrari, Principal: Mattia Binotto, Location: 10 Kaiserstrasse, Munich, Germany";
        assertEquals(expectedString, teamString, "toString should return the correct team details");
    }

    // Test for Location class used in the Team
    @Test
    public void testLocationClass() {
        // Arrange
        Location location = new Location("Italy", "Maranello", "Viale Trento", 45);

        // Act & Assert
        assertEquals("Italy", location.getCountry(), "Country should be Italy");
        assertEquals("Maranello", location.city, "City should be Maranello");
        assertEquals("Viale Trento", location.street, "Street should be Viale Trento");
        assertEquals(45, location.number, "Number should be 45");
    }
}

