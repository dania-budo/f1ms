package ma.daniabudo.formula1ms.application.Scenes;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import me.daniabudo.formula1ms.application.AuthManager;
import me.daniabudo.formula1ms.application.User;
import me.daniabudo.formula1ms.application.Scenes.LoginScene;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.function.BiConsumer;

import static org.mockito.Mockito.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isNotNull;

public class LoginSceneTest extends ApplicationTest {

    private AuthManager mockAuthManager;
    private BiConsumer<Stage, User> mockOnLoginSuccess;
    private User mockUser;

    @Override
    public void start(Stage stage) {
        // Mock the AuthManager and the onLoginSuccess callback
        mockAuthManager = mock(AuthManager.class);
        mockOnLoginSuccess = mock(BiConsumer.class);

        // Create a mock User for successful login
        mockUser = mock(User.class);
        when(mockUser.getUsername()).thenReturn("testUser");

        // Create the LoginScene with mocked dependencies
        LoginScene loginScene = new LoginScene(stage, mockOnLoginSuccess);
        Scene scene = loginScene.getScene();

        // Set the scene to the stage
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testLoginButtonSuccess() {
        // Mock the AuthManager login method to return a successful User
        when(mockAuthManager.login("testUser", "testPassword")).thenReturn(mockUser);

        // Fill in the login fields
        clickOn("#username").write("testUser");
        clickOn("#password").write("testPassword");

        // Click the login button
        clickOn("#loginButton");

        // Verify that the AuthManager login method is called
        verify(mockAuthManager).login("testUser", "testPassword");

        // Verify that the onLoginSuccess callback is called with the correct user
        verify(mockOnLoginSuccess).accept(any(Stage.class), eq(mockUser));

        // Verify that the success alert appears
        verifyThat(".alert", isNotNull());  // Check if an alert appears
    }

    @Test
    public void testLoginButtonFailure() {
        // Mock the AuthManager login method to return null (failed login)
        when(mockAuthManager.login("testUser", "wrongPassword")).thenReturn(null);

        // Fill in the login fields
        clickOn("#username").write("testUser");
        clickOn("#password").write("wrongPassword");

        // Click the login button
        clickOn("#loginButton");

        // Verify that the AuthManager login method is called
        verify(mockAuthManager).login("testUser", "wrongPassword");

        // Verify that the onLoginSuccess callback is NOT called (since login failed)
        verify(mockOnLoginSuccess, never()).accept(any(Stage.class), any(User.class));

        // Verify that the error alert appears
        verifyThat(".alert", isNotNull());  // Check if an alert appears
    }

    @Test
    public void testUIComponentsExist() {
        // Check that the UI components exist
        verifyThat("#username", isNotNull());
        verifyThat("#password", isNotNull());
        verifyThat("#loginButton", isNotNull());
    }
}
