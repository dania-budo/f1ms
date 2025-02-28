package me.daniabudo.formula1ms.application.Scenes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.daniabudo.formula1ms.application.AuthManager;

import java.util.function.Consumer;

public class SignUpScene {
    private final Stage stage;
    private final Consumer<Stage> onSignUpSuccess; // Callback after successful sign-up

    public SignUpScene(Stage stage, Consumer<Stage> onSignUpSuccess) {
        this.stage = stage;
        this.onSignUpSuccess = onSignUpSuccess;
    }

    public Scene getScene() {
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");

        ChoiceBox<String> roleChoice = new ChoiceBox<>();
        roleChoice.getItems().addAll("User", "Admin");
        roleChoice.setValue("User");

        PasswordField adminPasswordField = new PasswordField();
        adminPasswordField.setPromptText("Enter admin password (if creating an Admin account)");
        adminPasswordField.setVisible(false);

        // Show admin password field only if "Admin" is selected
        roleChoice.setOnAction(event -> adminPasswordField.setVisible("Admin".equals(roleChoice.getValue())));

        Button signUpButton = new Button("Sign Up");
        Alert signUpAlert = new Alert(Alert.AlertType.INFORMATION);

        signUpButton.setOnAction(event -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            String role = roleChoice.getValue();
            String adminPassword = adminPasswordField.getText().trim();

            try {
                // Check if the user is signing up as Admin
                AuthManager.signUp(username, password, role, adminPassword);

                signUpAlert.setAlertType(Alert.AlertType.INFORMATION);
                signUpAlert.setTitle("Sign Up Successful");
                signUpAlert.setHeaderText("User registered successfully!");
                signUpAlert.setContentText("You can now log in.");
                signUpAlert.showAndWait();

                onSignUpSuccess.accept(stage); // Redirect to login
            } catch (Exception e) {
                signUpAlert.setAlertType(Alert.AlertType.ERROR);
                signUpAlert.setTitle("Sign Up Failed");
                signUpAlert.setHeaderText("Error during sign up");
                signUpAlert.setContentText(e.getMessage());
                signUpAlert.showAndWait();
            }
        });

        VBox layout = new VBox(15, usernameField, passwordField, roleChoice, adminPasswordField, signUpButton);
        layout.setAlignment(Pos.CENTER);
        return new Scene(layout, 500, 500);
    }
}
