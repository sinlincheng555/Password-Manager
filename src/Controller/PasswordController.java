package Controller;

import Model.User;
import Model.Password;
import Model.SecureNote;
import Repository.PasswordRepository;
import Repository.UserRepository;
import Until.Encryption;
import Until.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PasswordController {

    private User user;
    private PasswordRepository passwordRepository;
    private UserRepository userRepository;
    private Encryption encryption;
    private Validator validator;

    private List<Password> savedPasswords = new ArrayList<>();

    public PasswordController(User user, PasswordRepository passwordRepository,
                              Encryption encryption, Validator validator) {
        this.user = user;
        this.passwordRepository = passwordRepository;
        this.encryption = encryption;
        this.validator = validator;
    }

    public boolean addPassword(String siteName, String username, String plainPassword) {
        if (!validator.isNotEmpty(siteName) || !validator.isNotEmpty(username) || !validator.isNotEmpty(plainPassword)) {
            return false;
        }

        if (!validator.isStrongPassword(plainPassword)) {
            return false;
        }

        if (validator.isDuplicateLogin(savedPasswords, siteName, username)) {
            return false;
        }

        Password newPassword = new Password(siteName, username, plainPassword);
        savedPasswords.add(newPassword);

        return true;
    }

    public List<Password> getSavedPasswords() {
        return savedPasswords;
    }

    public String generatePassword() {
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String symbols = "!@#$%&*?";
        String allChars = upper + lower + numbers + symbols;

        Random random = new Random();
        StringBuilder password = new StringBuilder();

        password.append(upper.charAt(random.nextInt(upper.length())));
        password.append(lower.charAt(random.nextInt(lower.length())));
        password.append(numbers.charAt(random.nextInt(numbers.length())));
        password.append(symbols.charAt(random.nextInt(symbols.length())));

        while (password.length() < 12) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        return password.toString();
    }

    public String checkStrength(String password) {
        return validator.checkPasswordStrength(password);
    }

    public boolean checkMainPassword(String typedPassword) {
        if (user == null || user.isAccountLocked()) {
            return false;
        }

        if (user.getMasterPassword() != null && user.getMasterPassword().equals(typedPassword)) {
            user.resetAttempts();
            return true;
        }

        user.addFailedAttempt();
        return false;
    }

    public boolean isAccountLocked() {
        return user != null && user.isAccountLocked();
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

    public void setLoggedUser(User loggedInUser) {
        this.user = loggedInUser;
    }

    public User getLoggedUser() {
        return this.user;
    }

    public void setUserRepository(UserRepository repo) {
        this.userRepository = repo;
    }

    public void updateAccountInfo(String email, String dob, String phone) {
        if (user != null && userRepository != null) {
            user.setEmail(email);
            user.setDob(dob);
            user.setPhone(phone);
            userRepository.updateUserDetails(user);
        }
    }

}