package me.daniabudo.formula1ms;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import me.daniabudo.formula1ms.application.Scenes.SignUpScene;
import me.daniabudo.formula1ms.application.Scenes.AdminScene;
import me.daniabudo.formula1ms.application.Scenes.LoginScene;
import me.daniabudo.formula1ms.application.Scenes.UserScene;
import me.daniabudo.formula1ms.application.User;

public class HelloApplication extends Application {
    private static String mode;

    @Override
    public void start(Stage stage) {
        try {
            if (mode == null || mode.isEmpty()) {
                throw new IllegalArgumentException("No mode provided. Please provide 'login' or 'signup' as the mode.");
            }

            if ("login".equalsIgnoreCase(mode)) {
                launchLoginMode(stage);
            } else if ("signup".equalsIgnoreCase(mode)) {
                launchSignUpMode(stage);
            } else {
                throw new IllegalArgumentException("Invalid mode. Please use 'login' or 'signup'.");
            }
        } catch (IllegalArgumentException e) {
            showAlert("Argument Input Error", e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            mode = args[0];
        } else {
            mode = "";
        }
        launch(args);
    }

    private void launchLoginMode(Stage stage) {
        try {
            stage.setTitle("Login");
            LoginScene loginScene = new LoginScene(stage, this::navigateBasedOnRole);
            stage.setScene(loginScene.getScene());
            stage.show();
        } catch (Exception e) {
            showAlert("Error", "Failed to launch login mode: " + e.getMessage());
        }
    }

    private void launchSignUpMode(Stage stage) {
        try {
            stage.setTitle("Sign Up");
            SignUpScene signUpScene = new SignUpScene(stage, this::launchLoginMode); // Redirect to login after sign-up
            stage.setScene(signUpScene.getScene());
            stage.show();
        } catch (Exception e) {
            showAlert("Error", "Failed to launch sign-up mode: " + e.getMessage());
        }
    }

    public void navigateBasedOnRole(Stage stage, User user) {
        if (user.isAdmin()) {
            showAdminScene(stage);
        } else {
            showUserScene(stage);
        }
    }

    public void showUserScene(Stage stage) {
        try {
            stage.setTitle("User Menu");
            UserScene userScene = new UserScene(stage);
            stage.setScene(userScene.getScene());
            stage.show();
        } catch (Exception e) {
            showAlert("Error", "Failed to load User Menu: " + e.getMessage());
        }
    }

    public void showAdminScene(Stage stage) {
        try {
            stage.setTitle("Admin Menu");
            AdminScene adminScene = new AdminScene(stage);
            stage.setScene(adminScene.getScene());
            stage.show();
        } catch (Exception e) {
            showAlert("Error", "Failed to load Admin Menu: " + e.getMessage());
        }
    }

    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
