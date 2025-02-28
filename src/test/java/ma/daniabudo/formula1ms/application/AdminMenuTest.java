package ma.daniabudo.formula1ms.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import me.daniabudo.formula1ms.application.AdminMenu;
import org.junit.jupiter.api.*;
import java.io.*;
import java.util.Scanner;

class AdminMenuTest {

    private static final String TEAMS_FILE = "teams_test.txt";
    private static final String DRIVERS_FILE = "drivers_test.txt";
    private static final String CIRCUITS_FILE = "circuits_test.txt";

    @BeforeEach
    void setUp() throws IOException {
        // Create temporary files for testing
        new File(TEAMS_FILE).createNewFile();
        new File(DRIVERS_FILE).createNewFile();
        new File(CIRCUITS_FILE).createNewFile();
    }

    @AfterEach
    void tearDown() {
        // Delete temporary files after tests
        new File(TEAMS_FILE).delete();
        new File(DRIVERS_FILE).delete();
        new File(CIRCUITS_FILE).delete();
    }

    @Test
    void testGetValidChoice() {
        Scanner mockScanner = mock(Scanner.class);

        // Simulate user inputs: invalid input ("abc"), valid choice (2)
        when(mockScanner.hasNextInt()).thenReturn(false, true);
        when(mockScanner.next()).thenReturn("abc");
        when(mockScanner.nextInt()).thenReturn(2);

        int choice = AdminMenu.getValidChoice(mockScanner);
        assertEquals(2, choice);
    }

    @Test
    void testAddTeam() throws IOException {
        // Simulate user inputs for adding a team
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextLine())
                .thenReturn("1")  // ID
                .thenReturn("Team Name")  // Name
                .thenReturn("Country")  // Country
                .thenReturn("Principal");  // Principal

        // Redirect file output to test file
        System.setProperty("teamsFile", TEAMS_FILE);
        AdminMenu.addTeam(mockScanner);

        // Verify that the team was added to the file
        try (BufferedReader reader = new BufferedReader(new FileReader(TEAMS_FILE))) {
            String line = reader.readLine();
            assertEquals("1,Team Name,Country,Principal", line);
        }
    }

    @Test
    void testRemoveTeam() throws IOException {
        // Prepare the test file with initial data
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEAMS_FILE))) {
            writer.write("1,Team1,Country1,Principal1");
            writer.newLine();
            writer.write("2,Team2,Country2,Principal2");
            writer.newLine();
        }

        // Simulate user input for removing a team
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextInt()).thenReturn(1);

        // Redirect file output to test file
        System.setProperty("teamsFile", TEAMS_FILE);
        AdminMenu.removeTeam(mockScanner);

        // Verify that the team was removed
        try (BufferedReader reader = new BufferedReader(new FileReader(TEAMS_FILE))) {
            String line = reader.readLine();
            assertEquals("2,Team2,Country2,Principal2", line);
        }
    }

    @Test
    void testAddDriver() throws IOException {
        // Simulate user inputs for adding a driver
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextLine())
                .thenReturn("1")  // ID
                .thenReturn("Driver Name")  // Name
                .thenReturn("Nationality")  // Nationality
                .thenReturn("10");  // Team ID

        // Redirect file output to test file
        System.setProperty("driversFile", DRIVERS_FILE);
        AdminMenu.addDriver(mockScanner);

        // Verify that the driver was added to the file
        try (BufferedReader reader = new BufferedReader(new FileReader(DRIVERS_FILE))) {
            String line = reader.readLine();
            assertEquals("1,Driver Name,Nationality,10", line);
        }
    }

    @Test
    void testRemoveDriver() throws IOException {
        // Prepare the test file with initial data
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DRIVERS_FILE))) {
            writer.write("1,Driver1,Nationality1,Team1");
            writer.newLine();
            writer.write("2,Driver2,Nationality2,Team2");
            writer.newLine();
        }

        // Simulate user input for removing a driver
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextInt()).thenReturn(2);

        // Redirect file output to test file
        System.setProperty("driversFile", DRIVERS_FILE);
        AdminMenu.removeDriver(mockScanner);

        // Verify that the driver was removed
        try (BufferedReader reader = new BufferedReader(new FileReader(DRIVERS_FILE))) {
            String line = reader.readLine();
            assertEquals("1,Driver1,Nationality1,Team1", line);
        }
    }

    @Test
    void testAddCircuit() throws IOException {
        // Simulate user inputs for adding a circuit
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextLine())
                .thenReturn("1")  // ID
                .thenReturn("Circuit Name")  // Name
                .thenReturn("5.3")  // Length
                .thenReturn("50");  // Laps

        // Redirect file output to test file
        System.setProperty("circuitsFile", CIRCUITS_FILE);
        AdminMenu.addCircuit(mockScanner);

        // Verify that the circuit was added to the file
        try (BufferedReader reader = new BufferedReader(new FileReader(CIRCUITS_FILE))) {
            String line = reader.readLine();
            assertEquals("1,Circuit Name,5.3,50", line);
        }
    }

    @Test
    void testRemoveCircuit() throws IOException {
        // Prepare the test file with initial data
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CIRCUITS_FILE))) {
            writer.write("1,Circuit1,4.5,45");
            writer.newLine();
            writer.write("2,Circuit2,6.1,60");
            writer.newLine();
        }

        // Simulate user input for removing a circuit
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextInt()).thenReturn(1);

        // Redirect file output to test file
        System.setProperty("circuitsFile", CIRCUITS_FILE);
        AdminMenu.removeCircuit(mockScanner);

        // Verify that the circuit was removed
        try (BufferedReader reader = new BufferedReader(new FileReader(CIRCUITS_FILE))) {
            String line = reader.readLine();
            assertEquals("2,Circuit2,6.1,60", line);
        }
    }
}

