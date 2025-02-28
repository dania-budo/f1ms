package ma.daniabudo.formula1ms.application;

import me.daniabudo.formula1ms.application.Driver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DriverTest {

    @Test
    public void testDriverConstructorWithTeamId() {
        // Given
        Driver driver = new Driver("Lewis Hamilton", 44, "British", 1);

        // Then
        assertEquals("Lewis Hamilton", driver.getName());
        assertEquals(44, driver.getId());
        assertEquals("British", driver.getNationality());
        assertEquals(1, driver.getTeamId());
    }

    @Test
    public void testDriverConstructorWithoutTeamId() {
        // Given
        Driver driver = new Driver("Sebastian Vettel", 5, "German");

        // Then
        assertEquals("Sebastian Vettel", driver.getName());
        assertEquals(5, driver.getId());
        assertEquals("German", driver.getNationality());
        assertNull(driver.getTeamId());
    }

    @Test
    public void testGetPresentationWithTeamId() {
        // Given
        Driver driver = new Driver("Charles Leclerc", 16, "Monegasque", 55);

        // When
        String presentation = driver.getPresentation();

        // Then
        assertEquals("Hello! I am a Monegasque driver! My name is Charles Leclerc and I belong to team ID 55.", presentation);
    }

    @Test
    public void testGetPresentationWithoutTeamId() {
        // Given
        Driver driver = new Driver("Fernando Alonso", 14, "Spanish");

        // When
        String presentation = driver.getPresentation();

        // Then
        assertEquals("Hello! I am a Spanish driver! My name is Fernando Alonso.", presentation);
    }

    @Test
    public void testToStringWithTeamId() {
        // Given
        Driver driver = new Driver("Valtteri Bottas", 77, "Finnish", 2);

        // When
        String result = driver.toString();

        // Then
        assertEquals("ID: 77, Name: Valtteri Bottas, Nationality: Finnish, Team ID: 2", result);
    }

    @Test
    public void testToStringWithoutTeamId() {
        // Given
        Driver driver = new Driver("Daniel Ricciardo", 3, "Australian");

        // When
        String result = driver.toString();

        // Then
        assertEquals("ID: 3, Name: Daniel Ricciardo, Nationality: Australian", result);
    }
}
