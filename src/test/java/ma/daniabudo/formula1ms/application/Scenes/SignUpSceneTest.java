package ma.daniabudo.formula1ms.application.Scenes;

import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import me.daniabudo.formula1ms.application.AuthManager;
import me.daniabudo.formula1ms.application.Scenes.SignUpScene;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.function.Consumer;

import static org.mockito.Mockito.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isNotNull;

public class SignUpSceneTest extends ApplicationTest {

    private AuthManager mockAuthManager;
    private Consumer<Stage> mockOnSignUpSuccess;

    @Override
    public void start(Stage stage) {
        // Initialize mocked dependencies
        mockAuthManager = mock(AuthManager.class);
        mockOnSignUpSuccess = mock(Consumer.class);

        // Create the SignUpScene
        SignUpScene signUpScene = new SignUpScene(stage, mockOnSignUpSuccess);
        Scene scene = signUpScene.getScene();

        // Set the scene to the stage
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testSignUpButtonSuccess() {
        // Fill in the fields
        clickOn("#usernameField").write("testUser");  // Click and type into username field
        clickOn("#passwordField").write("testPassword");  // Click and type into password field
        clickOn("#roleChoice");  // Click the role choice box
        clickOn("User");  // Select "User" role

        // Click the Sign Up button
        clickOn("#signUpButton");

        // Verify that the sign-up method is called
        verify(mockAuthManager).signUp("testUser", "testPassword", "User", "");

        // Verify that the success callback is called
        verify(mockOnSignUpSuccess).accept(any(Stage.class));

        // Check for alert of success (assuming the alert has a unique title for success)
        verifyThat(".alert", isNotNull());
    }

    @Test
    public void testSignUpButtonFailure() {
        // Simulate an error in the sign-up process
        doThrow(new RuntimeException("Sign up failed")).when(mockAuthManager)
                .signUp(anyString(), anyString(), anyString(), anyString());

        // Fill in the fields
        clickOn("#usernameField").write("testUser");  // Click and type into username field
        clickOn("#passwordField").write("testPassword");  // Click and type into password field
        clickOn("#roleChoice");  // Click the role choice box
        clickOn("User");  // Select "User" role

        // Click the Sign Up button
        clickOn("#signUpButton");

        // Verify that the sign-up method is called
        verify(mockAuthManager).signUp("testUser", "testPassword", "User", "");

        // Check for alert of failure
        verifyThat(".alert", isNotNull());
    }

    @Test
    public void testAdminRoleFieldsVisibility() {
        // Fill in the fields
        clickOn("#usernameField").write("testAdminUser");  // Click and type into username field
        clickOn("#passwordField").write("adminPassword");  // Click and type into password field

        // Set role to Admin
        clickOn("#roleChoice");  // Click the role choice box
        clickOn("Admin");  // Select "Admin" role

        // Check if admin password field is visible
        verifyThat("#adminPasswordField", isNotNull());

        // Set admin password
        clickOn("#adminPasswordField").write("adminPassword123");  // Type into admin password field

        // Verify the field text is updated
        verifyThat("#adminPasswordField", node -> node instanceof PasswordField
                && ((PasswordField) node).getText().equals("adminPassword123"));
    }
}

