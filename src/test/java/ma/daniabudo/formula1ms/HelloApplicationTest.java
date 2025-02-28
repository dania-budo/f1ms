package ma.daniabudo.formula1ms;

import javafx.stage.Stage;
import me.daniabudo.formula1ms.HelloApplication;
import me.daniabudo.formula1ms.application.Scenes.LoginScene;
import me.daniabudo.formula1ms.application.Scenes.SignUpScene;
import me.daniabudo.formula1ms.application.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HelloApplicationTest {

    @Mock
    private Stage mockStage;

    @Test
    public void testStart_ThrowsExceptionWhenModeIsNull() {
        HelloApplication app = new HelloApplication();
        assertThrows(IllegalArgumentException.class, () -> {
            HelloApplication.main(new String[]{}); // No arguments provided
            app.start(mockStage);
        });
    }

    @Test
    public void testStart_ThrowsExceptionForInvalidMode() {
        HelloApplication app = new HelloApplication();
        assertThrows(IllegalArgumentException.class, () -> {
            HelloApplication.main(new String[]{"invalidMode"});
            app.start(mockStage);
        });
    }

    @Test
    public void testLaunchLoginMode_SetsLoginScene() throws Exception {
        HelloApplication app = Mockito.spy(new HelloApplication());
        doNothing().when(app).showAlert(anyString(), anyString()); // Suppress alerts in the test

        app.start(mockStage);

        HelloApplication.main(new String[]{"login"});
        verify(mockStage).setTitle("Login");
    }

    @Test
    public void testLaunchSignUpMode_SetsSignUpScene() throws Exception {
        HelloApplication app = Mockito.spy(new HelloApplication());
        doNothing().when(app).showAlert(anyString(), anyString()); // Suppress alerts in the test

        HelloApplication.main(new String[]{"signup"});
        app.start(mockStage);

        verify(mockStage).setTitle("Sign Up");
    }

    @Test
    public void testNavigateBasedOnRole_AdminNavigatesToAdminScene() {
        HelloApplication app = Mockito.spy(new HelloApplication());
        User mockUser = mock(User.class);
        when(mockUser.isAdmin()).thenReturn(true);

        app.navigateBasedOnRole(mockStage, mockUser);

        verify(app).showAdminScene(mockStage);
        verify(app, never()).showUserScene(mockStage);
    }

    @Test
    public void testNavigateBasedOnRole_UserNavigatesToUserScene() {
        HelloApplication app = Mockito.spy(new HelloApplication());
        User mockUser = mock(User.class);
        when(mockUser.isAdmin()).thenReturn(false);

        app.navigateBasedOnRole(mockStage, mockUser);

        verify(app).showUserScene(mockStage);
        verify(app, never()).showAdminScene(mockStage);
    }
}
