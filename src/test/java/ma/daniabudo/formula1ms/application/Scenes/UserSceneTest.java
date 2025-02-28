package ma.daniabudo.formula1ms.application.Scenes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.daniabudo.formula1ms.application.Scenes.UserScene;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserSceneTest extends ApplicationTest {

    private Stage stage;
    private UserScene userScene;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        userScene = new UserScene(stage);
        stage.setScene(userScene.getScene());
        stage.show();
    }

    @Test
    void testSceneStructure() {
        Scene scene = userScene.getScene();
        assertNotNull(scene, "Scene should not be null");

        VBox mainLayout = (VBox) scene.getRoot();
        assertNotNull(mainLayout, "Main layout should not be null");
        assertEquals(2, mainLayout.getChildren().size(), "Main layout should have two children");

        Label titleLabel = (Label) mainLayout.getChildren().get(0);
        assertNotNull(titleLabel, "Title label should not be null");
        assertEquals("User Menu", titleLabel.getText(), "Title label should display 'User Menu'");

        VBox buttonContainer = (VBox) mainLayout.getChildren().get(1);
        assertNotNull(buttonContainer, "Button container should not be null");
        assertEquals(5, buttonContainer.getChildren().size(), "Button container should have five buttons");
    }

    @Test
    void testButtonActions() {
        clickOn("View Drivers");
        // Simulate the behavior of the dialog being displayed, e.g., by mocking DriverManager or observing stage changes.

        clickOn("View Teams");
        // Verify that the method for displaying teams is called.

        clickOn("View Races");
        // Check for the races dialog interaction.

        clickOn("View Circuits");
        // Check the circuit dialog behavior.

        clickOn("Exit");
        // Ensure the stage is closed after clicking Exit.
        assertEquals(false, stage.isShowing(), "Stage should close when 'Exit' is clicked");
    }
}
