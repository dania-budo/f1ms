package me.daniabudo.formula1ms.application.Scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.daniabudo.formula1ms.application.*;
import me.daniabudo.formula1ms.application.DriverImageService;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserScene {
    private final Stage stage;

    public UserScene(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(15));
        mainLayout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("User Menu");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button viewDriversButton = new Button("View Drivers");
        Button viewTeamsButton = new Button("View Teams");
        Button viewRacesButton = new Button("View Races");
        Button viewCircuitsButton = new Button("View Circuits");
        Button viewNewsButton = new Button("View Motorsport News");
        Button exitButton = new Button("Exit");

        viewDriversButton.setOnAction(e -> showDriversDialog());
        viewTeamsButton.setOnAction(e -> showTeamsDialog());
        viewRacesButton.setOnAction(e -> showRacesDialog());
        viewCircuitsButton.setOnAction(e -> showCircuitsDialog());
        viewNewsButton.setOnAction(e -> showNewsDialog());
        exitButton.setOnAction(e -> stage.close());

        VBox buttonContainer = new VBox(10,
                viewDriversButton,
                viewTeamsButton,
                viewRacesButton,
                viewCircuitsButton,
                viewNewsButton,
                exitButton);
        buttonContainer.setAlignment(Pos.CENTER);

        mainLayout.getChildren().addAll(titleLabel, buttonContainer);

        return new Scene(mainLayout, 400, 400);
    }

    // -------------------- NEWS DIALOG --------------------
    private void showNewsDialog() {
        List<String> newsArticles = F1NewsFetcher.fetchNews();
        if (newsArticles == null || newsArticles.isEmpty()) {
            showAlert("No news available.");
            return;
        }

        Stage dialog = new Stage();
        dialog.setTitle("Motorsport News");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("\uD83D\uDCEC Latest News");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        ComboBox<String> filterBox = new ComboBox<>();
        filterBox.getItems().addAll("Show All", "Search by Keyword");
        filterBox.setValue("Show All");

        ListView<String> listView = new ListView<>();
        listView.getItems().addAll(newsArticles);

        TextField searchField = new TextField();
        searchField.setPromptText("Enter keyword to search...");
        searchField.setVisible(false);

        Button applyButton = new Button("Apply");
        applyButton.setOnAction(e -> {
            if ("Search by Keyword".equals(filterBox.getValue())) {
                String keyword = searchField.getText().toLowerCase();
                listView.getItems().setAll(newsArticles.stream()
                        .filter(news -> news.toLowerCase().contains(keyword))
                        .collect(Collectors.toList()));
            } else {
                listView.getItems().setAll(newsArticles);
            }
        });

        filterBox.setOnAction(e -> searchField.setVisible("Search by Keyword".equals(filterBox.getValue())));

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> dialog.close());

        layout.getChildren().addAll(titleLabel, filterBox, searchField, applyButton, listView, closeButton);

        Scene dialogScene = new Scene(layout, 500, 500);
        dialog.setScene(dialogScene);
        dialog.showAndWait();
    }

    // -------------------- DRIVERS DIALOG --------------------
    // -------------------- DRIVERS DIALOG --------------------
    private void showDriversDialog() {
        List<Driver> drivers = DriverManager.loadDriversFromFile();
        if (drivers == null || drivers.isEmpty()) {
            showAlert("No drivers found.");
            return;
        }
        showDataWithOptions("Drivers", drivers,
                List.of("Show All", "Sort by Name", "Search by Nationality", "Fuzzy Search and Show Picture"),
                choice -> {
                    switch (choice) {
                        case 0:
                            return drivers;
                        case 1:
                            drivers.sort((d1, d2) -> d1.getName().compareToIgnoreCase(d2.getName()));
                            return drivers;
                        case 2: {
                            String nationality = getUserInput("Enter nationality to search for:");
                            if (nationality != null) {
                                return drivers.stream()
                                        .filter(driver -> nationality.equalsIgnoreCase(driver.getNationality()))
                                        .collect(Collectors.toList());
                            }
                            return List.of();
                        }
                        case 3: {
                            String searchName = getUserInput("Enter driver name to search for:");
                            if (searchName != null) {
                                Driver match = findClosestMatch(searchName, drivers);
                                if (match != null) {
                                    String imageUrl = DriverImageService.getDriverImageUrl(match.getName());
                                    showDriverImageDialog(match, imageUrl);
                                    return List.of(match.getName() + " image displayed in new window.");
                                } else {
                                    return List.of("No match found.");
                                }
                            }
                            return List.of();
                        }
                        default:
                            throw new IllegalStateException("Unexpected choice: " + choice);
                    }
                });
    }

    // -------------------- IMAGE DIALOG FOR DRIVER --------------------
    private void showDriverImageDialog(Driver driver, String imageUrl) {
        Stage dialog = new Stage();
        dialog.setTitle("Driver Image - " + driver.getName());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.setAlignment(Pos.CENTER);

        Label label = new Label("Driver: " + driver.getName());
        ImageView imageView = new ImageView();
        try {
            Image image = new Image(imageUrl, 300, 300, true, true);
            imageView.setImage(image);
        } catch (Exception e) {
            imageView.setImage(null);
            showAlert("Unable to load image.");
        }

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> dialog.close());

        layout.getChildren().addAll(label, imageView, closeButton);
        Scene scene = new Scene(layout, 350, 400);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    // -------------------- TEAMS DIALOG --------------------
    private void showTeamsDialog() {
        List<Team> teams = TeamManager.loadTeamsFromFile();
        if (teams == null || teams.isEmpty()) {
            showAlert("No teams found.");
            return;
        }
        showDataWithOptions("Teams", teams,
                List.of("Show All", "Sort by Name", "Search by Country"),
                choice -> {
                    switch (choice) {
                        case 0:
                            return teams;
                        case 1:
                            teams.sort((t1, t2) -> t1.getName().compareToIgnoreCase(t2.getName()));
                            return teams;
                        case 2: {
                            String country = getUserInput("Enter country to search for:");
                            if (country != null) {
                                return teams.stream()
                                        .filter(team -> team.getLocation() != null &&
                                                country.equalsIgnoreCase(team.getLocation().getCountry()))
                                        .collect(Collectors.toList());
                            }
                            return List.of();
                        }
                        default:
                            throw new IllegalStateException("Unexpected choice: " + choice);
                    }
                });
    }

    // -------------------- RACES DIALOG --------------------
    private void showRacesDialog() {
        List<Race> races = RaceManager.loadRacesFromFile();
        if (races == null || races.isEmpty()) {
            showAlert("No races found.");
            return;
        }
        showDataWithOptions("Races", races,
                List.of("Show All", "Sort by Date", "Group by Points"),
                choice -> {
                    switch (choice) {
                        case 0:
                            return races;
                        case 1:
                            races.sort(Race::compareTo);
                            return races;
                        case 2: {
                            Map<Integer, List<Race>> groupedRaces = RaceManager.groupRacesByPoints(races);
                            return groupedRaces.entrySet().stream()
                                    .flatMap(entry -> entry.getValue().stream()
                                            .map(race -> "Points: " + entry.getKey() + " - " + race.getName()))
                                    .collect(Collectors.toList());
                        }
                        default:
                            throw new IllegalStateException("Unexpected choice: " + choice);
                    }
                });
    }

    // -------------------- CIRCUITS DIALOG --------------------
    private void showCircuitsDialog() {
        List<Circuit> circuits = CircuitManager.loadCircuitsFromDatabase();
        if (circuits == null || circuits.isEmpty()) {
            showAlert("No circuits found.");
            return;
        }
        showDataWithOptions("Circuits", circuits,
                List.of("Show All", "Sort by Length", "Show Dizziness Level"),
                choice -> {
                    switch (choice) {
                        case 0:
                            return circuits;
                        case 1:
                            CircuitManager.sortCircuitsByLength(true, circuits);
                            return circuits;
                        case 2:
                            return circuits.stream()
                                    .map(circuit -> circuit.getName() + ": Dizziness Level = " + circuit.getDizzinessLevel())
                                    .collect(Collectors.toList());
                        default:
                            throw new IllegalStateException("Unexpected choice: " + choice);
                    }
                });
    }

    // -------------------- SHARED DATA HANDLING --------------------
    private void showDataWithOptions(String title, List<?> data, List<String> options, DataHandler handler) {
        Stage dialog = new Stage();
        dialog.setTitle(title);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        ComboBox<String> optionsComboBox = new ComboBox<>();
        optionsComboBox.getItems().addAll(options);
        optionsComboBox.setValue(options.get(0));

        ListView<String> listView = new ListView<>();
        updateListView(listView, data);

        Button applyButton = new Button("Apply");
        applyButton.setOnAction(e -> {
            int selectedIndex = optionsComboBox.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                List<?> updatedData = handler.handle(selectedIndex);
                updateListView(listView, updatedData);
            }
        });

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> dialog.close());

        layout.getChildren().addAll(titleLabel, optionsComboBox, applyButton, listView, closeButton);

        Scene dialogScene = new Scene(layout, 400, 500);
        dialog.setScene(dialogScene);
        dialog.showAndWait();
    }

    private void updateListView(ListView<String> listView, List<?> data) {
        listView.getItems().clear();
        if (data == null || data.isEmpty()) {
            listView.getItems().add("No data available.");
        } else {
            data.forEach(item -> listView.getItems().add(item.toString()));
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.showAndWait();
    }

    private String getUserInput(String prompt) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText(prompt);
        return dialog.showAndWait().orElse(null);
    }

    // -------------------- FUZZY SEARCH HELPERS --------------------
    private Driver findClosestMatch(String searchName, List<Driver> drivers) {
        Driver closestMatch = null;
        int minDistance = Integer.MAX_VALUE;

        for (Driver driver : drivers) {
            int distance = levenshteinDistance(searchName.toLowerCase(), driver.getName().toLowerCase());
            if (distance < minDistance) {
                minDistance = distance;
                closestMatch = driver;
            }
        }
        return closestMatch;
    }

    private int levenshteinDistance(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = Math.min(
                            Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                            dp[i - 1][j - 1] + (s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1)
                    );
                }
            }
        }
        return dp[s1.length()][s2.length()];
    }

    // -------------------- FUNCTIONAL INTERFACE --------------------
    @FunctionalInterface
    interface DataHandler {
        List<?> handle(int choice);
    }
}
