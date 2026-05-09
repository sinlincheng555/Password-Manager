import Model.User;
import Repository.PasswordRepository;
import Repository.UserRepository;
import Controller.PasswordController;
import Until.Validator;
import Until.Encryption;
import View.LoginView;

import javafx.application.Application;

public class Main {
    public static void main(String[] args) {

        // --- Util ---
        Encryption encryption = new Encryption();
        Validator  validator  = new Validator();

        // --- Repositories ---
        UserRepository     userRepository     = new UserRepository();
        PasswordRepository passwordRepository = new PasswordRepository();
        // --- Controller ---
        PasswordController passwordController = new PasswordController(
                new User(),
                passwordRepository,
                encryption,
                validator
        );

        // --- Launch ---
        LoginView.setController(passwordController);
        LoginView.setDependencies(userRepository, encryption);
        Application.launch(LoginView.class, args);
    }
}