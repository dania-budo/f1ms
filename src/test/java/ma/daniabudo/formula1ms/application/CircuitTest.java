package ma.daniabudo.formula1ms.application;

import me.daniabudo.formula1ms.application.Circuit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CircuitTest {

    @Test
    public void testCircuitConstructorAndGetters() {
        Circuit circuit = new Circuit(1, "Silverstone", 5.891, 52);

        assertEquals(1, circuit.getId());
        assertEquals("Silverstone", circuit.getName());
        assertEquals(5.891, circuit.getLengthKm());
        assertEquals(52, circuit.getLaps());
    }

    @Test
    public void testGetDizzinessLevel_DriversWillBeChill() {
        Circuit circuit = new Circuit(2, "Monza", 5.793, 53);

        String dizzinessLevel = circuit.getDizzinessLevel();

        assertEquals("The drivers will be chill, not dizzy!", dizzinessLevel);
    }

    @Test
    public void testGetDizzinessLevel_DriversWillGetDizzy() {
        Circuit circuit = new Circuit(3, "Monaco", 3.337, 78);

        String dizzinessLevel = circuit.getDizzinessLevel();

        assertEquals("The drivers will get dizzy!", dizzinessLevel);
    }

    @Test
    public void testCompareTo_SameLength_ReturnsZero() {
        Circuit circuit1 = new Circuit(4, "Suzuka", 5.807, 53);
        Circuit circuit2 = new Circuit(5, "Interlagos", 5.807, 71);

        int comparisonResult = circuit1.compareTo(circuit2);

        assertEquals(0, comparisonResult);
    }

    @Test
    public void testCompareTo_ShorterLength_ReturnsNegative() {
        Circuit circuit1 = new Circuit(6, "Hungaroring", 4.381, 70);
        Circuit circuit2 = new Circuit(7, "Spa-Francorchamps", 7.004, 44);

        int comparisonResult = circuit1.compareTo(circuit2);

        assertTrue(comparisonResult < 0);
    }

    @Test
    public void testCompareTo_LongerLength_ReturnsPositive() {
        Circuit circuit1 = new Circuit(8, "Spa-Francorchamps", 7.004, 44);
        Circuit circuit2 = new Circuit(9, "Hungaroring", 4.381, 70);

        int comparisonResult = circuit1.compareTo(circuit2);

        assertTrue(comparisonResult > 0);
    }

    @Test
    public void testToString() {
        Circuit circuit = new Circuit(10, "Bahrain", 5.412, 57);

        String expectedString = "Circuit ID: 10, Name: Bahrain, Length: 5,41 km, Laps: 57, Dizziness: The drivers will be chill, not dizzy!";
        assertEquals(expectedString, circuit.toString());
    }

    @Test
    public void testGetLength() {
        Circuit circuit = new Circuit(11, "Albert Park", 5.278, 58);

        double length = circuit.getLength();

        assertEquals(5.278, length);
    }
}

