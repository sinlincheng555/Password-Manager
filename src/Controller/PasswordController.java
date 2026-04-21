package Controller;

import Model.User;
import Repository.PasswordRepository;
import Until.Encryption;
import Until.Validator;
import java.util.List;
import Model.SecureNote;

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

    public void addSecureNote(String title, String content) {
        SecureNote note = new SecureNote(title, content);
        passwordRepository.addSecureNote(note);
    }

    public List<SecureNote> getSecureNotes() {
        return passwordRepository.getAllSecureNotes();
    }

    public void deleteSecureNote(SecureNote note) {
        passwordRepository.deleteSecureNote(note);
    }
}