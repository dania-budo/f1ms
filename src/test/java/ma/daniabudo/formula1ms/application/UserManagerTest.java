package ma.daniabudo.formula1ms.application;

import me.daniabudo.formula1ms.application.User;
import me.daniabudo.formula1ms.application.UserManager;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class UserManagerTest {

    private static final String TEST_FILE = "C:\\Users\\Dania Budo\\IdeaProjects\\Formula1\\src\\users.txt";

    // Mocking BufferedReader to simulate file reading behavior
    private BufferedReader bufferedReaderMock;

    @BeforeEach
    public void setUp() {
        bufferedReaderMock = mock(BufferedReader.class);
    }

    @Test
    public void testLoadUsersFromFile_Success() throws IOException {
        // Simulating file content
        String fileContent = "user1,password1,Admin\nuser2,password2,User\n";
        BufferedReader mockedReader = new BufferedReader(new StringReader(fileContent));

        // Simulating BufferedReader behavior
        when(bufferedReaderMock.readLine()).thenReturn("user1,password1,Admin")
                .thenReturn("user2,password2,User")
                .thenReturn(null); // End of file

        // Testing the loadUsersFromFile method
        List<User> users = UserManager.loadUsersFromFile(); // This is where the real logic is tested

        // Verify results
        assertEquals(2, users.size(), "There should be two users loaded.");
        assertEquals("user1", users.get(0).getUsername(), "First user's username should be 'user1'.");
        assertEquals("password1", users.get(0).getPassword(), "First user's password should be 'password1'.");
        assertEquals("Admin", users.get(0).getRole(), "First user's role should be 'Admin'.");

        assertEquals("user2", users.get(1).getUsername(), "Second user's username should be 'user2'.");
        assertEquals("password2", users.get(1).getPassword(), "Second user's password should be 'password2'.");
        assertEquals("User", users.get(1).getRole(), "Second user's role should be 'User'.");
    }

    @Test
    public void testLoadUsersFromFile_FileNotFound() {
        // Simulate FileNotFoundException
        try {
            when(bufferedReaderMock.readLine()).thenThrow(new FileNotFoundException("File not found"));

            List<User> users = UserManager.loadUsersFromFile();
            fail("Expected an exception to be thrown, but it wasn't.");
        } catch (Exception e) {
            assertTrue(e instanceof FileNotFoundException, "The exception should be of type FileNotFoundException");
        }
    }

    @Test
    public void testLoadUsersFromFile_InvalidFormat() throws IOException {
        // Simulating file with invalid format (too few parts in the line)
        String fileContent = "user1,password1\nuser2,password2,User\n";
        BufferedReader mockedReader = new BufferedReader(new StringReader(fileContent));

        // Simulate reading from the file
        when(bufferedReaderMock.readLine()).thenReturn("user1,password1")
                .thenReturn("user2,password2,User")
                .thenReturn(null); // End of file

        List<User> users = UserManager.loadUsersFromFile();

        // Verify that only the valid entry is loaded
        assertEquals(1, users.size(), "Only one valid user should be loaded.");
        assertEquals("user2", users.get(0).getUsername(), "The valid user's username should be 'user2'.");
    }

    @Test
    public void testLoadUsersFromFile_EmptyFile() throws IOException {
        // Simulating an empty file (no lines)
        String fileContent = "";
        BufferedReader mockedReader = new BufferedReader(new StringReader(fileContent));

        // Simulate reading from the file
        when(bufferedReaderMock.readLine()).thenReturn(null); // End of file

        List<User> users = UserManager.loadUsersFromFile();

        // Verify that no users are loaded
        assertEquals(0, users.size(), "No users should be loaded from an empty file.");
    }
}
