package ma.daniabudo.formula1ms.application;

import me.daniabudo.formula1ms.application.Race;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class RaceTest {

    @Test
    public void testConstructorAndGetters() {
        // Arrange
        LocalDateTime raceDate = LocalDateTime.of(2025, 5, 20, 15, 30);
        Race race = new Race(1, "Grand Prix of Monaco", raceDate, 25);

        // Act & Assert
        assertEquals(1, race.getId());
        assertEquals("Grand Prix of Monaco", race.getName());
        assertEquals(raceDate, race.getDate());
        assertEquals(25, race.getPoints());
    }

    @Test
    public void testGetPresentation() {
        // Arrange
        LocalDateTime raceDate = LocalDateTime.of(2025, 5, 20, 15, 30);
        Race race = new Race(1, "Grand Prix of Monaco", raceDate, 25);

        // Act
        String presentation = race.getPresentation();

        // Assert
        assertEquals("The Grand Prix of Monaco will take place on 2025-05-20T15:30", presentation);
    }

    @Test
    public void testCompareTo_LowerPoints() {
        // Arrange
        Race race1 = new Race(1, "Race 1", LocalDateTime.of(2025, 5, 20, 15, 30), 25);
        Race race2 = new Race(2, "Race 2", LocalDateTime.of(2025, 5, 21, 15, 30), 50);

        // Act
        int comparisonResult = race1.compareTo(race2);

        // Assert
        assertTrue(comparisonResult < 0, "Race 1 should have fewer points than Race 2");
    }

    @Test
    public void testCompareTo_EqualPoints() {
        // Arrange
        Race race1 = new Race(1, "Race 1", LocalDateTime.of(2025, 5, 20, 15, 30), 25);
        Race race2 = new Race(2, "Race 2", LocalDateTime.of(2025, 5, 21, 15, 30), 25);

        // Act
        int comparisonResult = race1.compareTo(race2);

        // Assert
        assertEquals(0, comparisonResult, "Race 1 and Race 2 should have the same points");
    }

    @Test
    public void testCompareTo_HigherPoints() {
        // Arrange
        Race race1 = new Race(1, "Race 1", LocalDateTime.of(2025, 5, 20, 15, 30), 75);
        Race race2 = new Race(2, "Race 2", LocalDateTime.of(2025, 5, 21, 15, 30), 50);

        // Act
        int comparisonResult = race1.compareTo(race2);

        // Assert
        assertTrue(comparisonResult > 0, "Race 1 should have more points than Race 2");
    }

    @Test
    public void testToString() {
        // Arrange
        LocalDateTime raceDate = LocalDateTime.of(2025, 5, 20, 15, 30);
        Race race = new Race(1, "Grand Prix of Monaco", raceDate, 25);

        // Act
        String raceToString = race.toString();

        // Assert
        assertEquals("Race ID: 1, Name: Grand Prix of Monaco, Date: 2025-05-20T15:30, Points: 25", raceToString);
    }
}
