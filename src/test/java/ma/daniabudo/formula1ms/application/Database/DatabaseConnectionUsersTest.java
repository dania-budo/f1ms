package ma.daniabudo.formula1ms.application.Database;

import me.daniabudo.formula1ms.application.Database.DatabaseConnectionUsers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

@org.junit.jupiter.api.extension.ExtendWith(MockitoExtension.class)
public class DatabaseConnectionUsersTest {

    @Mock
    private Connection mockConnection;

    @BeforeEach
    public void setUp() {
        // Prepare for static method mocking
        Mockito.mockStatic(DriverManager.class);
    }

    @Test
    public void testGetConnection() throws SQLException {
        // Define the behavior of DriverManager.getConnection
        when(DriverManager.getConnection(anyString(), anyString(), anyString())).thenReturn(mockConnection);

        // Call the method we want to test
        Connection connection = DatabaseConnectionUsers.getConnection();

        // Verify that getConnection() was called on DriverManager with the expected arguments
        try (var mocked = Mockito.mockStatic(DriverManager.class)) {
            mocked.verify(() -> DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/login_schema", "root", "D@niaUVT"));

            // Verify that the connection returned is the mock connection
            assert(connection != null);
            assert(connection.equals(mockConnection));
        }
    }

    @Test
    public void testGetConnectionThrowsSQLException() throws SQLException {
        // Set up the mock to throw an exception
        when(DriverManager.getConnection(anyString(), anyString(), anyString())).thenThrow(new SQLException("Connection error"));

        // Assert that an exception is thrown
        try (var mocked = Mockito.mockStatic(DriverManager.class)) {
            try {
                DatabaseConnectionUsers.getConnection();
            } catch (SQLException e) {
                assert(e.getMessage().equals("Connection error"));
            }
        }
    }
}

