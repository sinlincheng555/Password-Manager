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
import Until.Encryption;

public class LoginView extends Application {

    private TextField     usernameField;
    private PasswordField passwordField;
    private TextField     passwordVisible;
    private PasswordField confirmField;
    private TextField     confirmVisible;

    public static PasswordController controller;
    private static Encryption         encryption;

    public static void setController(PasswordController c) {
        controller = c;
    }

    @Override
    public void start(Stage stage) {

        if (encryption     == null) encryption     = new Encryption();

        // ── Root ──────────────────────────────────────────────────────
        VBox root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #e07b39, #c0392b);");
        root.setPadding(new Insets(50));

        Label titleLabel = new Label("Password Manager");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        titleLabel.setStyle("-fx-text-fill: white;");

        // ── Form ──────────────────────────────────────────────────────
        VBox formBox = new VBox(20);
        formBox.setAlignment(Pos.CENTER);
        formBox.setPadding(new Insets(40, 50, 40, 50));
        formBox.setMaxWidth(400);
        formBox.setPrefWidth(400);

        // Username
        Label usernameLabel = styledLabel("Username");
        usernameField = new TextField();
        usernameField.setPromptText("Enter username");
        usernameField.setPrefHeight(40);
        usernameField.setStyle("-fx-font-size: 14; -fx-padding: 8;");

        // Password
        Label passwordLabel = styledLabel("Password");
        passwordField   = new PasswordField();
        passwordVisible = new TextField();
        passwordField.setPromptText("Enter password");
        passwordVisible.setPromptText("Enter password");
        passwordField.setPrefHeight(40);
        passwordVisible.setPrefHeight(40);
        passwordField.setStyle("-fx-font-size: 14; -fx-padding: 8;");
        passwordVisible.setStyle("-fx-font-size: 14; -fx-padding: 8;");
        passwordVisible.setVisible(false);
        passwordField.managedProperty().bind(passwordField.visibleProperty());
        passwordVisible.managedProperty().bind(passwordVisible.visibleProperty());

        Button togglePassBtn = toggleButton();
        togglePassBtn.setOnAction(e -> toggleVisibility(passwordField, passwordVisible, togglePassBtn));
        HBox passwordRow = new HBox(10, passwordField, passwordVisible, togglePassBtn);
        passwordRow.setAlignment(Pos.CENTER);

        // Confirm Password
        Label confirmLabel = styledLabel("Confirm Password");
        confirmField   = new PasswordField();
        confirmVisible = new TextField();
        confirmField.setPromptText("Confirm password");
        confirmVisible.setPromptText("Confirm password");
        confirmField.setPrefHeight(40);
        confirmVisible.setPrefHeight(40);
        confirmField.setStyle("-fx-font-size: 14; -fx-padding: 8;");
        confirmVisible.setStyle("-fx-font-size: 14; -fx-padding: 8;");
        confirmVisible.setVisible(false);
        confirmField.managedProperty().bind(confirmField.visibleProperty());
        confirmVisible.managedProperty().bind(confirmVisible.visibleProperty());

        Button toggleConfirmBtn = toggleButton();
        toggleConfirmBtn.setOnAction(e -> toggleVisibility(confirmField, confirmVisible, toggleConfirmBtn));
        HBox confirmRow = new HBox(10, confirmField, confirmVisible, toggleConfirmBtn);
        confirmRow.setAlignment(Pos.CENTER);

        // ── Buttons ───────────────────────────────────────────────────
        Button loginButton    = actionButton("Login");
        Button registerButton = actionButton("Register");
        loginButton.setOnAction(e    -> handleLogin(stage));
        registerButton.setOnAction(e -> handleRegister());

        HBox buttonBox = new HBox(20, loginButton, registerButton);
        buttonBox.setAlignment(Pos.CENTER);

        // Forgot password link
        Label forgotLabel = new Label("Forgot Username / Password?");
        forgotLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        forgotLabel.setStyle("-fx-text-fill: rgba(255,255,255,0.7);");
        forgotLabel.setCursor(javafx.scene.Cursor.HAND);
        forgotLabel.setOnMouseEntered(e -> forgotLabel.setStyle("-fx-text-fill: white; -fx-underline: true;"));
        forgotLabel.setOnMouseExited(e  -> forgotLabel.setStyle("-fx-text-fill: rgba(255,255,255,0.7);"));

        formBox.getChildren().addAll(
                usernameLabel, usernameField,
                passwordLabel, passwordRow,
                confirmLabel,  confirmRow,
                buttonBox,     forgotLabel
        );
        VBox.setMargin(forgotLabel, new Insets(10, 0, 0, 0));

        root.getChildren().addAll(titleLabel, formBox);
        VBox.setMargin(titleLabel, new Insets(60, 0, 40, 0));
        VBox.setMargin(formBox,    new Insets(0, 0, 60, 0));

        Scene scene = new Scene(root, 550, 650);
        stage.setTitle("Password Manager");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    // ── Login: pass plain password — UserRepository hashes internally ─
    private void handleLogin(Stage stage) {
        String username = usernameField.getText().trim();
        String password = getActivePassword(passwordField, passwordVisible);

        if (username.isEmpty()) { warn("Please enter your username."); return; }
        if (password.isEmpty()) { warn("Please enter your password."); return; }

        try {
            DashboardView dashboard = new DashboardView(controller);
            dashboard.start(new Stage());
            stage.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            warn("Failed to open dashboard: " + ex.getMessage());
        }
    }

    // ── Register: pass plain password — UserRepository hashes internally ─
    private void handleRegister() {
        String username = usernameField.getText().trim();
        String password = getActivePassword(passwordField, passwordVisible);
        String confirm  = getActivePassword(confirmField,  confirmVisible);

        if (username.isEmpty())              { warn("Please enter a username.");                    return; }
        if (password.isEmpty())              { warn("Please enter a password.");                    return; }
        if (confirm.isEmpty())               { warn("Please confirm your password.");               return; }
        if (!password.equals(confirm))       { warn("Passwords do not match.");                    return; }
        if (password.length() < 8)           { warn("Password must be at least 8 characters.");   return; }

    }

    // ── Helpers ───────────────────────────────────────────────────────

    private String getActivePassword(PasswordField hidden, TextField visible) {
        return visible.isVisible() ? visible.getText() : hidden.getText();
    }

    private void toggleVisibility(PasswordField hidden, TextField visible, Button btn) {
        boolean show = !visible.isVisible();
        if (show) visible.setText(hidden.getText());
        else      hidden.setText(visible.getText());
        visible.setVisible(show);
        hidden.setVisible(!show);
        btn.setText(show ? "Hide" : "Show");
    }

    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
        passwordVisible.clear();
        confirmField.clear();
        confirmVisible.clear();
    }

    private void warn(String msg) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle("Warning");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.show();
    }

    private void info(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Success");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.show();
    }

    private Label styledLabel(String text) {
        Label l = new Label(text);
        l.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        l.setStyle("-fx-text-fill: white;");
        return l;
    }

    private Button toggleButton() {
        Button btn = new Button("Show");
        btn.setPrefHeight(40);
        btn.setStyle("-fx-background-color: #3a3a3a; -fx-text-fill: white; -fx-background-radius: 5;");
        return btn;
    }

    private Button actionButton(String text) {
        Button btn = new Button(text);
        btn.setPrefWidth(180);
        btn.setPrefHeight(40);
        String base  = "-fx-background-color: #3a3a3a; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14; -fx-background-radius: 5; -fx-cursor: hand;";
        String hover = "-fx-background-color: #2a2a2a; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14; -fx-background-radius: 5; -fx-cursor: hand;";
        btn.setStyle(base);
        btn.setOnMouseEntered(e -> btn.setStyle(hover));
        btn.setOnMouseExited(e  -> btn.setStyle(base));
        return btn;
    }

    public static void main(String[] args) { launch(args); }
}