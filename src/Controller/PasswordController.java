package Controller;

import Model.User;
import Repository.PasswordRepository;
import Until.Encryption;
import Until.Validator;

public class PasswordController {
    private User user;
    private PasswordRepository passwordRepository;
    private Encryption encryption;
    private Validator validator;

    public PasswordController(User user, PasswordRepository passwordRepository,
                              Encryption encryption, Validator validator) {
        this.user = user;
        this.passwordRepository = passwordRepository;
        this.encryption = encryption;
        this.validator = validator;
    }
}