package Model;

public class User {

    private String username;
    private String masterPassword;

    private int failedAttempts;
    private boolean accountLocked;

    public User() {

    }

    public User(String username, String masterPassword) {
        this.username = username;
        this.masterPassword = masterPassword;
        this.failedAttempts = 0;
        this.accountLocked = false;
    }

    public String getUsername() {
        return username;
    }

    public String getMasterPassword() {
        return masterPassword;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setMasterPassword(String masterPassword) {
        this.masterPassword = masterPassword;
    }

    public void addFailedAttempt() {
        failedAttempts++;

        if (failedAttempts >= 3) {
            accountLocked = true;
        }
    }

    public void resetAttempts() {
        failedAttempts = 0;
    }
}