package ma.daniabudo.formula1ms.application;

import me.daniabudo.formula1ms.application.AuthManager;
import me.daniabudo.formula1ms.application.User;
import me.daniabudo.formula1ms.application.exceptions.InvalidPasswordException;
import me.daniabudo.formula1ms.application.exceptions.InvalidUsernameException;
import me.daniabudo.formula1ms.application.Database.DatabaseConnectionUsers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthManagerTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        // Mock the behavior of DatabaseConnectionUsers.getConnection()
        mockStatic(DatabaseConnectionUsers.class);
        when(DatabaseConnectionUsers.getConnection()).thenReturn(mockConnection);
    }

    @Test
    public void testLogin_ValidCredentials_ReturnsUser() throws SQLException {
        String username = "validUser";
        String password = "validPass";
        String role = "User";

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true); // Simulate user exists
        when(mockResultSet.getString("role")).thenReturn(role);

        User user = AuthManager.login(username, password);

        assertNotNull(user);
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(role, user.getRole());
    }

    @Test
    public void testLogin_InvalidCredentials_ReturnsNull() throws SQLException {
        String username = "invalidUser";
        String password = "invalidPass";

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false); // Simulate user doesn't exist

        User user = AuthManager.login(username, password);

        assertNull(user);
    }

    @Test
    public void testSignUp_ValidData_SuccessfulRegistration() throws SQLException, InvalidUsernameException, InvalidPasswordException {
        String username = "newUser";
        String password = "newPass";
        String role = "User";

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false); // Simulate username not taken

        AuthManager.signUp(username, password, role, "");

        verify(mockStatement, times(1)).executeUpdate();
    }

    @Test
    public void testSignUp_UsernameAlreadyExists_ThrowsInvalidUsernameException() throws SQLException {
        String username = "existingUser";
        String password = "validPass";
        String role = "User";

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true); // Simulate username exists

        assertThrows(InvalidUsernameException.class, () -> {
            AuthManager.signUp(username, password, role, "");
        });
    }

    @Test
    public void testSignUp_InvalidUsername_ThrowsInvalidUsernameException() {
        String username = "invalid user!"; // Contains spaces and special characters
        String password = "validPass";
        String role = "User";

        assertThrows(InvalidUsernameException.class, () -> {
            AuthManager.signUp(username, password, role, "");
        });
    }

    @Test
    public void testSignUp_InvalidPassword_ThrowsInvalidPasswordException() {
        String username = "validUser";
        String password = "123"; // Too short
        String role = "User";

        assertThrows(InvalidPasswordException.class, () -> {
            AuthManager.signUp(username, password, role, "");
        });
    }

    @Test
    public void testSignUp_InvalidRole_ThrowsIllegalArgumentException() {
        String username = "validUser";
        String password = "validPass";
        String role = "InvalidRole";

        assertThrows(IllegalArgumentException.class, () -> {
            AuthManager.signUp(username, password, role, "");
        });
    }

    @Test
    public void testSignUp_AdminWithoutValidPassword_ThrowsIllegalArgumentException() {
        String username = "adminUser";
        String password = "adminPass";
        String role = "Admin";

        assertThrows(IllegalArgumentException.class, () -> {
            AuthManager.signUp(username, password, role, "wrongAdminPassword");
        });
    }

    @Test
    public void testIsUsernameTaken_UsernameExists_ReturnsTrue() throws SQLException {
        String username = "existingUser";

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(1); // Simulate username exists

        boolean result = AuthManager.isUsernameTaken(username);

        assertTrue(result);
    }

    @Test
    public void testIsUsernameTaken_UsernameNotExists_ReturnsFalse() throws SQLException {
        String username = "newUser";

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(0); // Simulate username doesn't exist

        boolean result = AuthManager.isUsernameTaken(username);

        assertFalse(result);
    }
}

