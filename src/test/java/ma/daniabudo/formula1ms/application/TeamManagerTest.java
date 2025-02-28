package ma.daniabudo.formula1ms.application;

import me.daniabudo.formula1ms.application.Team;
import me.daniabudo.formula1ms.application.TeamManager;
import org.junit.jupiter.api.*;
import org.mockito.*;
import java.io.*;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TeamManagerTest {

    private static final String TEAM_FILE = "C:\\Users\\Dania Budo\\IdeaProjects\\Formula1\\src\\teams.txt";

    @Mock
    private BufferedReader bufferedReaderMock;

    @InjectMocks
    private TeamManager teamManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test: Successful loading of teams from file
    @Test
    public void testLoadTeamsFromFile_ValidData() throws IOException {
        // Arrange
        String validData = "1, Germany, Munich, Kaiserstrasse, 10, Ferrari, Mattia Binotto\n" +
                "2, Italy, Maranello, Viale Trento, 45, Mercedes, Toto Wolff";

        // Mock the bufferedReader behavior to return the lines one by one
        when(bufferedReaderMock.readLine()).thenReturn("1, Germany, Munich, Kaiserstrasse, 10, Ferrari, Mattia Binotto")
                .thenReturn("2, Italy, Maranello, Viale Trento, 45, Mercedes, Toto Wolff")
                .thenReturn(null); // End of file

        // Act
        List<Team> teams = teamManager.loadTeamsFromFile();

        // Assert
        assertNotNull(teams, "Teams list should not be null");
        assertEquals(2, teams.size(), "There should be 2 teams loaded from the file");

        Team team1 = teams.get(0);
        assertEquals("Ferrari", team1.getName(), "First team should be Ferrari");
        assertEquals("Mattia Binotto", team1.getPrincipal(), "First team principal should be Mattia Binotto");

        Team team2 = teams.get(1);
        assertEquals("Mercedes", team2.getName(), "Second team should be Mercedes");
        assertEquals("Toto Wolff", team2.getPrincipal(), "Second team principal should be Toto Wolff");
    }

    // Test: Invalid data format in the file (e.g., wrong number of fields)
    @Test
    public void testLoadTeamsFromFile_InvalidData() throws IOException {
        // Arrange
        String invalidData = "1, Germany, Munich, Kaiserstrasse, 10, Ferrari"; // Missing principal

        // Mock invalid file reading
        when(bufferedReaderMock.readLine()).thenReturn(invalidData).thenReturn(null);

        // Act
        List<Team> teams = teamManager.loadTeamsFromFile();

        // Assert
        assertEquals(0, teams.size(), "No teams should be loaded due to invalid data format");
    }

    // Test: IOException during file reading
    @Test
    public void testLoadTeamsFromFile_FileReadError() throws IOException {
        // Arrange: Simulate IOException
        when(bufferedReaderMock.readLine()).thenThrow(new IOException("Error reading file"));

        // Act & Assert
        assertDoesNotThrow(() -> {
            List<Team> teams = teamManager.loadTeamsFromFile();
            assertEquals(0, teams.size(), "No teams should be loaded if file reading fails");
        });
    }

    // Test: Number format exception while parsing team id or other numeric fields
    @Test
    public void testLoadTeamsFromFile_NumberFormatError() throws IOException {
        // Arrange: Invalid number format in the line (e.g., non-numeric ID)
        String invalidData = "1, Germany, Munich, Kaiserstrasse, ten, Ferrari, Mattia Binotto";

        // Mock invalid file reading
        when(bufferedReaderMock.readLine()).thenReturn(invalidData).thenReturn(null);

        // Act
        List<Team> teams = teamManager.loadTeamsFromFile();

        // Assert
        assertEquals(0, teams.size(), "No teams should be loaded if there is a number format error");
    }
}

