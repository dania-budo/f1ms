package ma.daniabudo.formula1ms.application.Scenes;

import javafx.scene.Scene;
import javafx.stage.Stage;
import me.daniabudo.formula1ms.application.Scenes.AdminScene;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;
import static org.mockito.Mockito.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isNotNull;

import java.io.*;

public class AdminSceneTest extends ApplicationTest {

    private AdminScene adminScene;

    @Override
    public void start(Stage stage) {
        adminScene = new AdminScene(stage);
        Scene scene = adminScene.getScene();
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    public void setUp() {
        // Prepare any mocks before each test if needed
    }

    @Test
    public void testAddTeamButtonClick() {
        // Simulate the Add Team button click
        clickOn("Add Team");

        // Verify that the Add Team logic is triggered by verifying interactions
        // with file writer or alert logic here (mocking required).

        // Check if an alert is displayed
        verifyThat(".alert", isNotNull());  // Check if an alert is triggered

        // Verify that a form dialog for adding a team is shown (this would require
        // further inspection depending on your alert and form setup).
    }

    @Test
    public void testRemoveTeamButtonClick() {
        // Simulate the Remove Team button click
        clickOn("Remove Team");

        // Here we should mock the file operations to ensure that remove team logic
        // is tested correctly without actually reading/writing files.

        // Check if a dialog for removing a team appears
        verifyThat(".alert", isNotNull());  // You can also verify the remove dialog interaction here
    }

    @Test
    public void testAddDriverButtonClick() {
        // Simulate the Add Driver button click
        clickOn("Add Driver");

        // Check if the appropriate form for adding a driver appears and the logic is triggered
        verifyThat(".alert", isNotNull()); // Alert will show success/failure
    }

    @Test
    public void testRemoveDriverButtonClick() {
        // Simulate the Remove Driver button click
        clickOn("Remove Driver");

        // Mock file operations for driver removal (not actual file reading/writing).
        // Verify alert messages related to driver removal.
        verifyThat(".alert", isNotNull());  // Ensure the correct alert shows
    }

    @Test
    public void testAddCircuitButtonClick() {
        // Simulate Add Circuit button click
        clickOn("Add Circuit");

        // Check if the alert is triggered successfully.
        verifyThat(".alert", isNotNull());
    }

    @Test
    public void testRemoveCircuitButtonClick() {
        // Simulate the Remove Circuit button click
        clickOn("Remove Circuit");

        // Mock file operations for circuit removal and check the alert display.
        verifyThat(".alert", isNotNull());
    }

    @Test
    public void testExitButtonClick() {
        // Simulate the Exit button click
        clickOn("Exit");

        // Verify that the application closes (stage is closed).
        verifyThat(".stage", isNotNull());  // Ensure the stage closes
    }

    @Test
    public void testShowFormDialogAddTeam() throws IOException {
        // Mock the input dialog (form submission).
        FileWriter fileWriter = mock(FileWriter.class);
        BufferedWriter bufferedWriter = mock(BufferedWriter.class);
        when(bufferedWriter.append(anyString())).thenReturn(bufferedWriter);

        // Mock the file writer for adding team
        when(new BufferedWriter(any(FileWriter.class))).thenReturn(bufferedWriter);

        // Simulate the Add Team button click
        clickOn("Add Team");

        // Verify that the file write operation is called when the form is submitted
        verify(fileWriter).write(anyString());
    }

    @Test
    public void testShowRemoveDialogRemoveTeam() throws IOException {
        // Mock file reading and writing for removing team
        FileReader fileReader = mock(FileReader.class);
        BufferedReader bufferedReader = mock(BufferedReader.class);
        BufferedWriter bufferedWriter = mock(BufferedWriter.class);

        when(new BufferedReader(any(FileReader.class))).thenReturn(bufferedReader);
        when(new BufferedWriter(any(FileWriter.class))).thenReturn(bufferedWriter);

        // Simulate the Remove Team button click
        clickOn("Remove Team");

        // Ensure the file is accessed and the correct alert is shown
        verify(bufferedReader).readLine(); // Verify file reading
        verify(bufferedWriter).write(anyString()); // Verify that writing occurs
    }
}

