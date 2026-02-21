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

    public static PasswordController controller;
    public static void setController(PasswordController c) {
        controller = c;
    }
    @Override
    public void start(Stage stage) {
        // Title
        Label titleLabel = new Label("Login");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        // Username
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter username");

        // Password
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");

        // Buttons
        Button loginButton = new Button("Login");
        loginButton.setPrefWidth(200);

        Button registerButton = new Button("Register");
        registerButton.setPrefWidth(200);

        // Layout grid
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(12);
        grid.setPadding(new Insets(30));

        grid.add(titleLabel,    0, 0, 2, 1);
        grid.add(usernameLabel, 0, 1);
        grid.add(usernameField, 1, 1);
        grid.add(passwordLabel, 0, 2);
        grid.add(passwordField, 1, 2);
        grid.add(loginButton,   0, 3, 2, 1);
        grid.add(registerButton,0, 4, 2, 1);

        GridPane.setHalignment(titleLabel,    javafx.geometry.HPos.CENTER);
        GridPane.setHalignment(loginButton,   javafx.geometry.HPos.CENTER);
        GridPane.setHalignment(registerButton,javafx.geometry.HPos.CENTER);

        // Scene
        Scene scene = new Scene(grid, 350, 260);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}