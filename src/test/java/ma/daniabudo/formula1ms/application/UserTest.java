package ma.daniabudo.formula1ms.application;

import me.daniabudo.formula1ms.application.User;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        // Initialize the user with sample data for testing
        user = new User("testUser", "password123", "Admin");
    }

    // Test: Check if the username is correctly set
    @Test
    public void testGetUsername() {
        assertEquals("testUser", user.getUsername(), "Username should match the expected value");
    }

    // Test: Check if the password is correctly set
    @Test
    public void testGetPassword() {
        assertEquals("password123", user.getPassword(), "Password should match the expected value");
    }

    // Test: Check if the role is correctly set
    @Test
    public void testGetRole() {
        assertEquals("Admin", user.getRole(), "Role should match the expected value");
    }

    // Test: Check if the credentials match correctly
    @Test
    public void testMatchesCredentials_ValidCredentials() {
        assertTrue(user.matchesCredentials("testUser", "password123"), "Credentials should match the ones stored in the user object");
    }

    // Test: Check if the credentials don't match
    @Test
    public void testMatchesCredentials_InvalidCredentials() {
        assertFalse(user.matchesCredentials("testUser", "wrongPassword"), "Credentials should not match if the password is incorrect");
        assertFalse(user.matchesCredentials("wrongUser", "password123"), "Credentials should not match if the username is incorrect");
    }

    // Test: Check if the user is an admin
    @Test
    public void testIsAdmin() {
        assertTrue(user.isAdmin(), "The user should be identified as an Admin");
    }

    // Test: Check if the user is not an admin (change the role)
    @Test
    public void testIsAdmin_NotAdmin() {
        user = new User("testUser", "password123", "User");
        assertFalse(user.isAdmin(), "The user should not be identified as an Admin");
    }
}

