package Until;

import Model.Password;
import java.util.List;

public class Validator {

    public boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public boolean isStrongPassword(String password) {
        if (password == null) {
            return false;
        }

        boolean lengthOk = password.length() >= 8;
        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasLower = password.matches(".*[a-z].*");
        boolean hasNumber = password.matches(".*[0-9].*");
        boolean hasSymbol = password.matches(".*[^A-Za-z0-9].*");

        return lengthOk && hasUpper && hasLower && hasNumber && hasSymbol;
    }

    public String checkPasswordStrength(String password) {
        if (password == null || password.isEmpty()) {
            return "Empty";
        }

        int points = 0;

        if (password.length() >= 8) points++;
        if (password.matches(".*[A-Z].*")) points++;
        if (password.matches(".*[a-z].*")) points++;
        if (password.matches(".*[0-9].*")) points++;
        if (password.matches(".*[^A-Za-z0-9].*")) points++;

        if (points <= 2) {
            return "Weak";
        }

        if (points <= 4) {
            return "Medium";
        }

        return "Strong";
    }

    public boolean isDuplicateLogin(List<Password> passwords, String siteName, String username) {
        if (passwords == null || siteName == null || username == null) {
            return false;
        }

        for (Password item : passwords) {
            boolean sameSite = item.getSiteName().equalsIgnoreCase(siteName.trim());
            boolean sameUsername = item.getUsername().equalsIgnoreCase(username.trim());

            if (sameSite || sameUsername) {
                return true;
            }
        }

        return false;
    }
}