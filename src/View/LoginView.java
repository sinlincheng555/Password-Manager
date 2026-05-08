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

public class LoginView extends Application {

    //allows whole class to use these variables
    private TextField usernameField;
    private PasswordField passwordField;
    private TextField passwordVisible;
    private PasswordField confirmField;
    private TextField confirmVisible;

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

        // Title - Password Manager at top center
        Label titleLabel = new Label("Password Manager");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        titleLabel.setStyle("-fx-text-fill: white;");
        titleLabel.setAlignment(Pos.CENTER);

        // Form container (no white background)
        VBox formBox = new VBox(20);
        formBox.setAlignment(Pos.CENTER);
        formBox.setPadding(new Insets(40, 50, 40, 50));
        formBox.setMaxWidth(400);
        formBox.setPrefWidth(400);

        // Username field
        Label usernameLabel = new Label("Username");
        usernameLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        usernameLabel.setStyle("-fx-text-fill: white;");
        usernameField = new TextField();
        usernameField.setPromptText("Enter username");
        usernameField.setPrefHeight(40);
        usernameField.setStyle("-fx-font-size: 14; -fx-padding: 8;");


        Label passwordLabel = new Label("Password");
        passwordLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        passwordLabel.setStyle("-fx-text-fill: white;");
        passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");
        passwordField.setPrefHeight(40);
        passwordField.setStyle("-fx-font-size: 14; -fx-padding: 8;");

        passwordVisible = new TextField();
        passwordVisible.setPromptText("Enter password");
        passwordVisible.setPrefHeight(40);
        passwordVisible.setStyle("-fx-font-size: 14; -fx-padding: 8;");
        passwordVisible.setVisible(false);

        passwordField.managedProperty().bind(passwordField.visibleProperty());
        passwordVisible.managedProperty().bind(passwordVisible.visibleProperty());

        Button toggleBtn = new Button("Show");
        toggleBtn.setPrefHeight(40);
        toggleBtn.setStyle("-fx-background-color: #3a3a3a; -fx-text-fill: white; -fx-background-radius: 5;");
        toggleBtn.setOnAction(e -> {
            boolean show = !passwordVisible.isVisible();

            //swaps the text via If/Else
            if (show) {
                passwordVisible.setText(passwordField.getText());
            } else {
                passwordField.setText(passwordVisible.getText());
            }

            //makes show/hide pwd work
            passwordVisible.setVisible(show);
            passwordField.setVisible(!show);


            if (show) {
                toggleBtn.setText("Hide");
            } else {
                toggleBtn.setText("Show");
            }
        });

        HBox passwordRow = new HBox(10, passwordField, passwordVisible, toggleBtn);
        passwordRow.setAlignment(Pos.CENTER);


        Label confirmLabel = new Label("Confirm");
        confirmLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        confirmLabel.setStyle("-fx-text-fill: white;");
        confirmField = new PasswordField();
        confirmField.setPromptText("Confirm password");
        confirmField.setPrefHeight(40);
        confirmField.setStyle("-fx-font-size: 14; -fx-padding: 8;");

        confirmVisible = new TextField();
        confirmVisible.setPromptText("Confirm password");
        confirmVisible.setPrefHeight(40);
        confirmVisible.setStyle("-fx-font-size: 14; -fx-padding: 8;");
        confirmVisible.setVisible(false);

        confirmField.managedProperty().bind(confirmField.visibleProperty());
        confirmVisible.managedProperty().bind(confirmVisible.visibleProperty());

        Button toggleConfirmBtn = new Button("Show");
        toggleConfirmBtn.setPrefHeight(40);
        toggleConfirmBtn.setStyle("-fx-background-color: #3a3a3a; -fx-text-fill: white; -fx-background-radius: 5;");
        toggleConfirmBtn.setOnAction(e -> {
            boolean show = !confirmVisible.isVisible();

            //swaps the text via If/Else
            if (show) {
                confirmVisible.setText(confirmField.getText());
            } else {
                confirmField.setText(confirmVisible.getText());
            }

            //makes show/hide pwd work
            confirmVisible.setVisible(show);
            confirmField.setVisible(!show);

            if (show) {
                toggleConfirmBtn.setText("Hide");
            } else {
                toggleConfirmBtn.setText("Show");
            }
        });

        HBox confirmRow = new HBox(10, confirmField, confirmVisible, toggleConfirmBtn);
        confirmRow.setAlignment(Pos.CENTER);

        // Buttons with #3a3a3a background
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

        // Hover effects for buttons
        loginButton.setOnMouseEntered(e ->
                loginButton.setStyle(
                        "-fx-background-color: #2a2a2a; " +
                                "-fx-text-fill: white; " +
                                "-fx-font-weight: bold; " +
                                "-fx-font-size: 14; " +
                                "-fx-background-radius: 5; " +
                                "-fx-cursor: hand;"
                )
        );
        loginButton.setOnMouseExited(e ->
                loginButton.setStyle(
                        "-fx-background-color: #3a3a3a; " +
                                "-fx-text-fill: white; " +
                                "-fx-font-weight: bold; " +
                                "-fx-font-size: 14; " +
                                "-fx-background-radius: 5; " +
                                "-fx-cursor: hand;"
                )
        );

        registerButton.setOnMouseEntered(e ->
                registerButton.setStyle(
                        "-fx-background-color: #2a2a2a; " +
                                "-fx-text-fill: white; " +
                                "-fx-font-weight: bold; " +
                                "-fx-font-size: 14; " +
                                "-fx-background-radius: 5; " +
                                "-fx-cursor: hand;"
                )
        );
        registerButton.setOnMouseExited(e ->
                registerButton.setStyle(
                        "-fx-background-color: #3a3a3a; " +
                                "-fx-text-fill: white; " +
                                "-fx-font-weight: bold; " +
                                "-fx-font-size: 14; " +
                                "-fx-background-radius: 5; " +
                                "-fx-cursor: hand;"
                )
        );

        // Buttons row

        loginButton.setOnAction(this::handleLoginClick);//triggers for the login button

        HBox buttonBox = new HBox(20, loginButton, registerButton);
        buttonBox.setAlignment(Pos.CENTER);

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

        forgotLabel.setOnMouseClicked(e -> {
            System.out.println("Forgot Username/Password clicked");
        });

        // Add all elements to form box
        formBox.getChildren().addAll(
                usernameLabel, usernameField,
                passwordLabel, passwordRow,
                confirmLabel, confirmRow,
                buttonBox,
                forgotLabel
        );

        // Add spacing between form elements
        VBox.setMargin(forgotLabel, new Insets(10, 0, 0, 0));

        // Add title and form to root with spacing
        root.getChildren().addAll(titleLabel, formBox);
        VBox.setMargin(titleLabel, new Insets(60, 0, 40, 0));
        VBox.setMargin(formBox, new Insets(0, 0, 60, 0));

        // Scene
        Scene scene = new Scene(root, 550, 650);
        stage.setTitle("Password Manager");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


    //helper method for event handler
    private void handleLoginClick(javafx.event.ActionEvent event) {
        //Takes the text
        String user = usernameField.getText();

        String pass;
        if (passwordVisible.isVisible()) {
            pass = passwordVisible.getText();
        } else {
            pass = passwordField.getText();
        }

        //checks the username
        if (user.isEmpty()) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Please enter username");
            a.show();
            return;
        }

        //chekcs password
        if (pass.isEmpty()) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Please enter password");
            a.show();
            return;
        }
        try {
            //boots up the dashboard
            new DashboardView().start(new javafx.stage.Stage());

            //closes the login window
            javafx.stage.Stage currentStage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}