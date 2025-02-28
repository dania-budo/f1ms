package ma.daniabudo.formula1ms.application;

import me.daniabudo.formula1ms.application.Race;
import me.daniabudo.formula1ms.application.RaceManager;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RaceManagerTest {

    // Test for loadRacesFromFile()
    @Test
    public void testLoadRacesFromFile() throws Exception {
        // Arrange
        String testFilePath = "src/test/resources/races.txt";
        String testRaceData = "1, Grand Prix of Monaco, 2025-05-20T15:30, 25\n" +
                "2, Grand Prix of Spain, 2025-06-10T14:00, 18\n" +
                "3, Grand Prix of France, 2025-07-02T16:00, 25";

        // Mocking FileReader and BufferedReader
        BufferedReader reader = mock(BufferedReader.class);
        FileReader fileReader = mock(FileReader.class);

        when(fileReader.read()).thenReturn(Integer.valueOf(testRaceData));
        when(reader.readLine()).thenReturn("1, Grand Prix of Monaco, 2025-05-20T15:30, 25",
                "2, Grand Prix of Spain, 2025-06-10T14:00, 18",
                "3, Grand Prix of France, 2025-07-02T16:00, 25");

        List<Race> races = RaceManager.loadRacesFromFile();

        // Act & Assert
        assertNotNull(races);
        assertEquals(3, races.size());
        assertEquals("Grand Prix of Monaco", races.get(0).getName());
        assertEquals(25, races.get(0).getPoints());
        assertEquals(LocalDateTime.of(2025, 5, 20, 15, 30), races.get(0).getDate());
    }

    @Test
    public void testLoadRacesFromFile_withInvalidData() throws Exception {
        // Arrange
        String invalidTestData = "1, Invalid Race, 2025-05-20T15:30, ABC\n";
        BufferedReader reader = mock(BufferedReader.class);
        FileReader fileReader = mock(FileReader.class);

        when(reader.readLine()).thenReturn(invalidTestData, null);

        // Act
        List<Race> races = RaceManager.loadRacesFromFile();

        // Assert
        assertTrue(races.isEmpty(), "Races list should be empty due to invalid data");
    }

    // Test for groupRacesByPoints()
    @Test
    public void testGroupRacesByPoints() {
        // Arrange
        LocalDateTime raceDate1 = LocalDateTime.of(2025, 5, 20, 15, 30);
        LocalDateTime raceDate2 = LocalDateTime.of(2025, 6, 10, 14, 0);
        LocalDateTime raceDate3 = LocalDateTime.of(2025, 7, 2, 16, 0);

        Race race1 = new Race(1, "Grand Prix of Monaco", raceDate1, 25);
        Race race2 = new Race(2, "Grand Prix of Spain", raceDate2, 18);
        Race race3 = new Race(3, "Grand Prix of France", raceDate3, 25);

        List<Race> races = Arrays.asList(race1, race2, race3);

        // Act
        Map<Integer, List<Race>> groupedRaces = RaceManager.groupRacesByPoints(races);

        // Assert
        assertEquals(2, groupedRaces.size());
        assertTrue(groupedRaces.containsKey(25));
        assertTrue(groupedRaces.containsKey(18));
        assertEquals(2, groupedRaces.get(25).size());
        assertEquals(1, groupedRaces.get(18).size());
    }

    @Test
    public void testGroupRacesByPoints_EmptyList() {
        // Arrange
        List<Race> races = new ArrayList<>();

        // Act
        Map<Integer, List<Race>> groupedRaces = RaceManager.groupRacesByPoints(races);

        // Assert
        assertTrue(groupedRaces.isEmpty(), "Grouped races should be empty for an empty list");
    }

    @Test
    public void testGroupRacesByPoints_SingleRace() {
        // Arrange
        LocalDateTime raceDate = LocalDateTime.of(2025, 5, 20, 15, 30);
        Race race = new Race(1, "Grand Prix of Monaco", raceDate, 25);
        List<Race> races = Collections.singletonList(race);

        // Act
        Map<Integer, List<Race>> groupedRaces = RaceManager.groupRacesByPoints(races);

        // Assert
        assertEquals(1, groupedRaces.size());
        assertTrue(groupedRaces.containsKey(25));
        assertEquals(1, groupedRaces.get(25).size());
    }
}

