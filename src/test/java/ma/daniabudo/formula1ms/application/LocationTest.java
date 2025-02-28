package ma.daniabudo.formula1ms.application;

import me.daniabudo.formula1ms.application.Location;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LocationTest {

    @Test
    public void testLocationConstructor() {
        // Arrange: Create a Location object with test data
        Location location = new Location("France", "Paris", "Champs-Élysées", 12);

        // Assert: Ensure that the attributes are set correctly
        assertEquals("France", location.getCountry());
        assertEquals("Paris", location.city);
        assertEquals("Champs-Élysées", location.street);
        assertEquals(12, location.number);
    }

    @Test
    public void testToString() {
        // Arrange: Create a Location object
        Location location = new Location("Germany", "Berlin", "Unter den Linden", 1);

        // Act: Call the toString method
        String result = location.toString();

        // Assert: Ensure the toString method returns the expected format
        assertEquals("1 Unter den Linden, Berlin, Germany", result);
    }

    @Test
    public void testGetCountry() {
        // Arrange: Create a Location object
        Location location = new Location("Italy", "Rome", "Via del Corso", 20);

        // Act: Call the getCountry method
        String country = location.getCountry();

        // Assert: Ensure that the correct country is returned
        assertEquals("Italy", country);
    }
}

