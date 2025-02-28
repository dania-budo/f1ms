package ma.daniabudo.formula1ms;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import me.daniabudo.formula1ms.HelloController;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloControllerTest extends ApplicationTest {

    private Label welcomeText;
    private Button helloButton;

    @Override
    public void start(Stage stage) throws Exception {
        // Load the FXML file and set the scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/hello-view.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();

        // Access the controller and its elements
        HelloController controller = loader.getController();
        welcomeText = controller.welcomeText;
        helloButton = (Button) scene.lookup("#helloButton"); // Assuming the button has the id "helloButton"
    }

    @Test
    public void testOnHelloButtonClick() {
        // Ensure initial text is empty
        assertEquals("", welcomeText.getText());

        // Simulate the button click
        clickOn(helloButton);

        // Verify the expected outcome
        assertEquals("Welcome to JavaFX Application!", welcomeText.getText());
    }
}
