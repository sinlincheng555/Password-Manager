package Until;

public class Validator {
    public boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public boolean isStrongPassword(String password) {
        if (password == null || password.length() < 8) return false;
        boolean upper = password.matches(".*[A-Z].*");
        boolean lower = password.matches(".*[a-z].*");
        boolean digit = password.matches(".*\\d.*");
        boolean special = password.matches(".*[^A-Za-z0-9].*");
        return upper && lower && digit && special;
    }
}
