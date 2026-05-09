import Model.User;
import Repository.PasswordRepository;
import Controller.PasswordController;
import Until.Validator;
import Until.Encryption;
import View.LoginView;

import javafx.application.Application;

public class Main {
    public static void main(String[] args) {

        // --- Model ---
        User user = new User();

        // --- Util ---
        Encryption encryption = new Encryption();
        Validator  validator  = new Validator();

        // --- Repository ---
        PasswordRepository passwordRepository = new PasswordRepository();

        // --- Controller ---
        PasswordController passwordController = new PasswordController(
                user,
                passwordRepository,
                encryption,
                validator
        );

        // --- View (JavaFX launch) ---
        LoginView.setController(passwordController);
        Application.launch(LoginView.class, args);
    }
}