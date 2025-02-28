package ma.daniabudo.formula1ms.application;

import me.daniabudo.formula1ms.application.AuthManager;
import me.daniabudo.formula1ms.application.Main;
import me.daniabudo.formula1ms.application.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    public void testTooFewArguments() throws Exception {
        // Arrange: Simulate no arguments passed (empty args)
        String[] args = {};

        // Redirecting System.out and System.in to capture and provide input/output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Act: Call the main method with no arguments
        Main.main(args);

        // Assert: Verify the output
        String output = outputStream.toString().trim();
        assertEquals("Too few arguments provided!", output);
    }

    @Test
    public void testTooManyArguments() throws Exception {
        // Arrange: Simulate passing multiple arguments
        String[] args = {"login", "extraArgument"};

        // Redirecting System.out and System.in to capture and provide input/output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Act: Call the main method with multiple arguments
        Main.main(args);

        // Assert: Verify the output
        String output = outputStream.toString().trim();
        assertEquals("Too many arguments provided!", output);
    }

    @Test
    public void testLoginArgument() throws Exception {
        // Arrange: Simulate the login argument and mock user input
        String[] args = {"login"};
        String userInput = "validUser\nvalidPassword\n"; // Simulating valid user input

        // Mock System.in with the user input
        ByteArrayInputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(inputStream);

        // Mocking AuthManager's login method
        User mockUser = Mockito.mock(User.class);
        Mockito.when(mockUser.getRole()).thenReturn("User"); // Assume the mock user is a regular user

        // Simulate AuthManager login behavior
        Mockito.mockStatic(AuthManager.class);
        Mockito.when(AuthManager.login(Mockito.anyString(), Mockito.anyString())).thenReturn(mockUser);

        // Redirecting System.out to capture printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Act: Call the main method with "login" argument
        Main.main(args);

        // Assert: Verify the output (user menu should be shown)
        String output = outputStream.toString().trim();
        assertTrue(output.contains("User menu"));
    }

    @Test
    public void testSignUpArgument() throws Exception {
        // Arrange: Simulate the signup argument and mock user input
        String[] args = {"signup"};
        String userInput = "newUser\nnewPassword\nUser\n"; // Simulating valid signup input

        // Mock System.in with the user input
        ByteArrayInputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(inputStream);

        // Mock AuthManager's signUp method (no exception)
        Mockito.mockStatic(AuthManager.class);
        Mockito.doNothing().when(AuthManager.class);
        AuthManager.signUp(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());

        // Redirecting System.out to capture printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Act: Call the main method with "signup" argument
        Main.main(args);

        // Assert: Verify the output (sign-up success message)
        String output = outputStream.toString().trim();
        assertEquals("Sign-up successful! You can now log in.", output);
    }

    @Test
    public void testInvalidArgument() throws Exception {
        // Arrange: Simulate an invalid argument
        String[] args = {"invalidArgument"};

        // Redirecting System.out and System.in to capture and provide input/output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Act: Call the main method with an invalid argument
        Main.main(args);

        // Assert: Verify the output
        String output = outputStream.toString().trim();
        assertEquals("Invalid argument! Use 'login' or 'signup'.", output);
    }
}

