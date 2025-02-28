package ma.daniabudo.formula1ms.application;

import me.daniabudo.formula1ms.application.UserMenu;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

public class UserMenuTest {

    private Scanner scannerMock;
    private UserMenu userMenu;

    @BeforeEach
    public void setUp() {
        scannerMock = mock(Scanner.class);
        userMenu = new UserMenu();
    }

    @Test
    public void testDisplay_ViewDrivers() {
        // Simulate the user input for option 1: View Drivers
        when(scannerMock.nextInt()).thenReturn(1);

        UserMenu spyUserMenu = spy(userMenu);
        doNothing().when(spyUserMenu).viewDrivers();

        // Call the display method, which should trigger viewDrivers
        spyUserMenu.display();

        verify(spyUserMenu).viewDrivers();
    }

    @Test
    public void testDisplay_ViewTeams() {
        // Simulate the user input for option 2: View Teams
        when(scannerMock.nextInt()).thenReturn(2);

        UserMenu spyUserMenu = spy(userMenu);
        doNothing().when(spyUserMenu).viewTeams();

        spyUserMenu.display();

        verify(spyUserMenu).viewTeams();
    }

    @Test
    public void testDisplay_ViewRaces() {
        when(scannerMock.nextInt()).thenReturn(3);

        UserMenu spyUserMenu = spy(userMenu);
        doNothing().when(spyUserMenu).viewRaces();

        spyUserMenu.display();

        verify(spyUserMenu).viewRaces();
    }

    @Test
    public void testDisplay_ViewCircuits() {
        when(scannerMock.nextInt()).thenReturn(4);

        UserMenu spyUserMenu = spy(userMenu);
        doNothing().when(spyUserMenu).viewCircuits();

        spyUserMenu.display();

        verify(spyUserMenu).viewCircuits();
    }

    @Test
    public void testDisplay_ExitOption() {
        // Simulate the user input for option 5: Exit
        when(scannerMock.nextInt()).thenReturn(5);

        // We expect the ExitMenuException to be thrown
        assertThrows(UserMenu.ExitMenuException.class, () -> {
            userMenu.display();
        });
    }

    @Test
    public void testLevenshteinDistance() {
        // Test Levenshtein distance method directly
        int distance = UserMenu.levenshteinDistance("kitten", "sitting");

        assertEquals(3, distance);
    }
}
