package View;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import Controller.PasswordController;
import View.DashboardView;

public class LoginView extends Application {

    public static PasswordController controller;

    public static void setController(PasswordController c) {
        controller = c;
    }

    @Override
    public void start(Stage stage) {
        // Main root with gradient background
        VBox root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #e07b39, #c0392b);");
        root.setPadding(new Insets(50));

        // Title
        Label titleLabel = new Label("Password Manager");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        titleLabel.setStyle("-fx-text-fill: white;");
        titleLabel.setAlignment(Pos.CENTER);

        // Form container
        VBox formBox = new VBox(20);
        formBox.setAlignment(Pos.CENTER);
        formBox.setPadding(new Insets(40, 50, 40, 50));
        formBox.setMaxWidth(400);
        formBox.setPrefWidth(400);

        // Username field
        Label usernameLabel = new Label("Username");
        usernameLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        usernameLabel.setStyle("-fx-text-fill: white;");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter username");
        usernameField.setPrefHeight(40);
        usernameField.setStyle("-fx-font-size: 14; -fx-padding: 8;");

        // Password field
        Label passwordLabel = new Label("Password");
        passwordLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        passwordLabel.setStyle("-fx-text-fill: white;");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");
        passwordField.setPrefHeight(40);
        passwordField.setStyle("-fx-font-size: 14; -fx-padding: 8;");

        // Confirm field
        Label confirmLabel = new Label("Confirm");
        confirmLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        confirmLabel.setStyle("-fx-text-fill: white;");
        PasswordField confirmField = new PasswordField();
        confirmField.setPromptText("Confirm password");
        confirmField.setPrefHeight(40);
        confirmField.setStyle("-fx-font-size: 14; -fx-padding: 8;");

        // Status label for feedback
        Label statusLabel = new Label("");
        statusLabel.setStyle("-fx-text-fill: white; -fx-font-size: 12;");
        statusLabel.setAlignment(Pos.CENTER);

        // Buttons
        Button loginButton = new Button("Login");
        loginButton.setPrefWidth(180);
        loginButton.setPrefHeight(40);
        loginButton.setStyle(
                "-fx-background-color: #3a3a3a; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-size: 14; " +
                        "-fx-background-radius: 5; " +
                        "-fx-cursor: hand;"
        );

        Button registerButton = new Button("Register");
        registerButton.setPrefWidth(180);
        registerButton.setPrefHeight(40);
        registerButton.setStyle(
                "-fx-background-color: #3a3a3a; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-size: 14; " +
                        "-fx-background-radius: 5; " +
                        "-fx-cursor: hand;"
        );

        // Hover effects
        loginButton.setOnMouseEntered(e ->
                loginButton.setStyle(
                        "-fx-background-color: #2a2a2a; -fx-text-fill: white; " +
                                "-fx-font-weight: bold; -fx-font-size: 14; " +
                                "-fx-background-radius: 5; -fx-cursor: hand;"
                )
        );
        loginButton.setOnMouseExited(e ->
                loginButton.setStyle(
                        "-fx-background-color: #3a3a3a; -fx-text-fill: white; " +
                                "-fx-font-weight: bold; -fx-font-size: 14; " +
                                "-fx-background-radius: 5; -fx-cursor: hand;"
                )
        );

        registerButton.setOnMouseEntered(e ->
                registerButton.setStyle(
                        "-fx-background-color: #2a2a2a; -fx-text-fill: white; " +
                                "-fx-font-weight: bold; -fx-font-size: 14; " +
                                "-fx-background-radius: 5; -fx-cursor: hand;"
                )
        );
        registerButton.setOnMouseExited(e ->
                registerButton.setStyle(
                        "-fx-background-color: #3a3a3a; -fx-text-fill: white; " +
                                "-fx-font-weight: bold; -fx-font-size: 14; " +
                                "-fx-background-radius: 5; -fx-cursor: hand;"
                )
        );

        // ── Login action: validate then open Dashboard ──────────────────
        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                statusLabel.setText("Please enter username and password.");
                return;
            }

            // Open dashboard — pass controller directly via constructor
            Stage dashStage = new Stage();
            DashboardView dashboard = new DashboardView(controller);
            try {
                dashboard.start(dashStage);
                stage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                statusLabel.setText("Failed to open dashboard.");
            }
        });

        // ── Register action: basic validation ──────────────────────────
        registerButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText();
            String confirm  = confirmField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                statusLabel.setText("Username and password cannot be empty.");
                return;
            }
            if (!password.equals(confirm)) {
                statusLabel.setText("Passwords do not match.");
                return;
            }
            statusLabel.setText("Registered successfully! You can now log in.");
        });

        // Forgot password link
        Label forgotLabel = new Label("Forgot Username / Password?");
        forgotLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        forgotLabel.setStyle("-fx-text-fill: rgba(255, 255, 255, 0.7);");
        forgotLabel.setAlignment(Pos.CENTER);
        forgotLabel.setCursor(javafx.scene.Cursor.HAND);

        forgotLabel.setOnMouseEntered(e ->
                forgotLabel.setStyle("-fx-text-fill: white; -fx-underline: true;")
        );
        forgotLabel.setOnMouseExited(e ->
                forgotLabel.setStyle("-fx-text-fill: rgba(255, 255, 255, 0.7);")
        );
        forgotLabel.setOnMouseClicked(e ->
                statusLabel.setText("Please contact support to reset your credentials.")
        );

        // Buttons row
        HBox buttonBox = new HBox(20, loginButton, registerButton);
        buttonBox.setAlignment(Pos.CENTER);

        // Assemble form
        formBox.getChildren().addAll(
                usernameLabel, usernameField,
                passwordLabel, passwordField,
                confirmLabel, confirmField,
                buttonBox,
                statusLabel,
                forgotLabel
        );

        VBox.setMargin(forgotLabel, new Insets(10, 0, 0, 0));

        root.getChildren().addAll(titleLabel, formBox);
        VBox.setMargin(titleLabel, new Insets(60, 0, 40, 0));
        VBox.setMargin(formBox, new Insets(0, 0, 60, 0));

        Scene scene = new Scene(root, 550, 650);
        stage.setTitle("Password Manager");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}