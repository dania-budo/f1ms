package ma.daniabudo.formula1ms.application;

import me.daniabudo.formula1ms.application.Driver;
import me.daniabudo.formula1ms.application.DriverManager;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DriverManagerTest {

    @Test
    public void testLoadDriversFromFile() throws IOException {
        // Simulate a file content with a StringReader
        String mockFileContent = "1, Lewis Hamilton, British, 44\n" +
                "2, Sebastian Vettel, German\n" +
                "3, Charles Leclerc, Monegasque, 55\n";
        StringReader stringReader = new StringReader(mockFileContent);
        BufferedReader bufferedReader = new BufferedReader(stringReader);

        // Create a spy for the DriverManager class
        DriverManager driverManagerSpy = Mockito.spy(new DriverManager());

        // Mock the getBufferedReader method to return the mocked BufferedReader
        doReturn(bufferedReader).when(driverManagerSpy).getBufferedReader(any(File.class));

        // Act: Call the method to load drivers
        List<Driver> drivers = driverManagerSpy.loadDriversFromFile();

        // Assert: Validate that the drivers are loaded correctly
        assertEquals(3, drivers.size()); // Expecting 3 drivers
        assertEquals("Lewis Hamilton", drivers.get(0).getName());
        assertEquals("Sebastian Vettel", drivers.get(1).getName());
        assertEquals("Charles Leclerc", drivers.get(2).getName());

        // Validate that the last driver has a team ID
        assertEquals(Integer.valueOf(55), drivers.get(2).getTeamId());

        // Check that the second driver (Sebastian Vettel) has no team ID (null)
        assertNull(drivers.get(1).getTeamId());
    }

    @Test
    public void testLoadDriversFromFileWithMalformedData() throws IOException {
        // Simulating a file with a malformed line
        String mockFileContent = "1, Lewis Hamilton, British, 44\n" +
                "2, Invalid Data\n" + // Malformed line
                "3, Charles Leclerc, Monegasque, 55\n";
        StringReader stringReader = new StringReader(mockFileContent);
        BufferedReader bufferedReader = new BufferedReader(stringReader);

        DriverManager driverManagerSpy = Mockito.spy(new DriverManager());
        doReturn(bufferedReader).when(driverManagerSpy).getBufferedReader(any(File.class)); // Mock the file reading

        // Act: Call the method to load drivers
        List<Driver> drivers = driverManagerSpy.loadDriversFromFile();

        // Assert: Ensure the method doesn't crash and only valid data is loaded
        assertEquals(2, drivers.size()); // Expecting only 2 valid drivers to be loaded
        assertEquals("Lewis Hamilton", drivers.get(0).getName());
        assertEquals("Charles Leclerc", drivers.get(1).getName());
    }

    @Test
    public void testLoadDriversFromFileWithIOException() throws IOException {
        // Simulating an IOException (e.g., file not found)
        FileReader fileReader = mock(FileReader.class);
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenThrow(new IOException("File not found"));

        DriverManager driverManagerSpy = Mockito.spy(new DriverManager());
        doReturn(bufferedReader).when(driverManagerSpy).getBufferedReader(any(File.class)); // Mock the file reading

        // Act: Call the method to load drivers
        List<Driver> drivers = driverManagerSpy.loadDriversFromFile();

        // Assert: Ensure an empty list is returned on error
        assertTrue(drivers.isEmpty());
    }
}
