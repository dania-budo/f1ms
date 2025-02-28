package me.daniabudo.formula1ms.application.Scenes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.daniabudo.formula1ms.application.AuthManager;
import me.daniabudo.formula1ms.application.User;

import java.util.function.BiConsumer;

public class LoginScene {
    private final Stage stage;
    private final BiConsumer<Stage, User> onLoginSuccess;

    public LoginScene(Stage stage, BiConsumer<Stage, User> onLoginSuccess) {
        this.stage = stage;
        this.onLoginSuccess = onLoginSuccess;
    }

    public Scene getScene() {
        TextField username = new TextField();
        username.setPromptText("Username");

        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        Button loginButton = new Button("Login");
        Alert loginAlert = new Alert(Alert.AlertType.INFORMATION);

        loginButton.setOnAction(event -> {
            String userName = username.getText().trim();
            String pass = password.getText().trim();

            User user = AuthManager.login(userName, pass);

            if (user != null) {
                loginAlert.setTitle("Login Successful");
                loginAlert.setHeaderText("Welcome, " + user.getUsername() + "!");
                loginAlert.setContentText("You have successfully logged in.");
                loginAlert.showAndWait();

                onLoginSuccess.accept(stage, user);
            } else {
                loginAlert.setAlertType(Alert.AlertType.ERROR);
                loginAlert.setTitle("Login Failed");
                loginAlert.setHeaderText("Invalid Credentials");
                loginAlert.setContentText("The username or password is incorrect. Please try again.");
                loginAlert.showAndWait();
            }
        });

        VBox layout = new VBox(20, username, password, loginButton);
        layout.setAlignment(Pos.CENTER);

        return new Scene(layout, 500, 500);
    }
}
