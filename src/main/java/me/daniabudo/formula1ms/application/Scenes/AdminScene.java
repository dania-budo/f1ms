package me.daniabudo.formula1ms.application.Scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;

public class AdminScene {
    private final Stage stage;

    public AdminScene(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(15));
        mainLayout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Admin Menu");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button addTeamButton = new Button("Add Team");
        Button removeTeamButton = new Button("Remove Team");
        Button addDriverButton = new Button("Add Driver");
        Button removeDriverButton = new Button("Remove Driver");
        Button addCircuitButton = new Button("Add Circuit");
        Button removeCircuitButton = new Button("Remove Circuit");
        Button exitButton = new Button("Exit");

        addTeamButton.setOnAction(e -> addTeam());
        removeTeamButton.setOnAction(e -> removeTeam());
        addDriverButton.setOnAction(e -> addDriver());
        removeDriverButton.setOnAction(e -> removeDriver());
        addCircuitButton.setOnAction(e -> addCircuit());
        removeCircuitButton.setOnAction(e -> removeCircuit());
        exitButton.setOnAction(e -> exitApplication());

        VBox buttonContainer = new VBox(10, addTeamButton, removeTeamButton, addDriverButton,
                removeDriverButton, addCircuitButton, removeCircuitButton, exitButton);
        buttonContainer.setAlignment(Pos.CENTER);

        mainLayout.getChildren().addAll(titleLabel, buttonContainer);

        return new Scene(mainLayout, 400, 400);
    }

    private void addTeam() {
        showFormDialog("Add Team", new String[]{"Team ID", "Team Name", "Country", "Principal"},
                inputData -> {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/teams.txt", true))) {
                        writer.write(String.join(",", inputData));
                        writer.newLine();
                        showAlert(Alert.AlertType.INFORMATION, "Success", "Team added successfully!");
                    } catch (IOException e) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Failed to add team: " + e.getMessage());
                    }
                });
    }

    private void removeTeam() {
        showRemoveDialog("Remove Team", "Enter Team ID:", "src/teams.txt");
    }

    private void addDriver() {
        showFormDialog("Add Driver", new String[]{"Driver ID", "Driver Name", "Nationality", "Team ID"},
                inputData -> {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/drivers.txt", true))) {
                        writer.write(String.join(",", inputData));
                        writer.newLine();
                        showAlert(Alert.AlertType.INFORMATION, "Success", "Driver added successfully!");
                    } catch (IOException e) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Failed to add driver: " + e.getMessage());
                    }
                });
    }

    private void removeDriver() {
        showRemoveDialog("Remove Driver", "Enter Driver ID:", "src/drivers.txt");
    }

    private void addCircuit() {
        showFormDialog("Add Circuit", new String[]{"Circuit ID", "Circuit Name", "Length (km)", "Laps"},
                inputData -> {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/circuits.txt", true))) {
                        writer.write(String.join(",", inputData));
                        writer.newLine();
                        showAlert(Alert.AlertType.INFORMATION, "Success", "Circuit added successfully!");
                    } catch (IOException e) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Failed to add circuit: " + e.getMessage());
                    }
                });
    }

    private void removeCircuit() {
        showRemoveDialog("Remove Circuit", "Enter Circuit ID:", "src/circuits.txt");
    }

    private void exitApplication() {
        stage.close();
    }

    private void showFormDialog(String title, String[] fields, FormSubmitHandler handler) {
        Stage dialog = new Stage();
        dialog.setTitle(title);

        GridPane formLayout = new GridPane();
        formLayout.setPadding(new Insets(10));
        formLayout.setHgap(10);
        formLayout.setVgap(10);

        TextField[] textFields = new TextField[fields.length];
        for (int i = 0; i < fields.length; i++) {
            formLayout.add(new Label(fields[i] + ":"), 0, i);
            textFields[i] = new TextField();
            formLayout.add(textFields[i], 1, i);
        }

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            String[] inputData = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                inputData[i] = textFields[i].getText().trim();
                if (inputData[i].isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Error", fields[i] + " cannot be empty.");
                    return;
                }
            }
            handler.handle(inputData);
            dialog.close();
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> dialog.close());

        HBox buttons = new HBox(10, submitButton, cancelButton);
        buttons.setAlignment(Pos.CENTER);

        VBox dialogLayout = new VBox(10, formLayout, buttons);
        dialogLayout.setPadding(new Insets(10));

        dialog.setScene(new Scene(dialogLayout));
        dialog.showAndWait();
    }

    private void showRemoveDialog(String title, String prompt, String filePath) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.setContentText(prompt);

        dialog.showAndWait().ifPresent(input -> {
            try {
                File file = new File(filePath);
                File tempFile = new File(filePath + "_temp");

                try (BufferedReader reader = new BufferedReader(new FileReader(file));
                     BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

                    boolean found = false;
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (!line.startsWith(input + ",")) {
                            writer.write(line);
                            writer.newLine();
                        } else {
                            found = true;
                        }
                    }

                    if (found) {
                        showAlert(Alert.AlertType.INFORMATION, "Success", "Item removed successfully!");
                    } else {
                        showAlert(Alert.AlertType.WARNING, "Not Found", "Item ID not found.");
                    }
                }

                if (!file.delete() || !tempFile.renameTo(file)) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to update the file.");
                }
            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to remove item: " + e.getMessage());
            }
        });
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FunctionalInterface
    interface FormSubmitHandler {
        void handle(String[] inputData);
    }
}
