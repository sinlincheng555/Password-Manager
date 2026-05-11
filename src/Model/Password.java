package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Password {

    private String siteName;
    private String username;
    private String password;
    private String dateSaved;

    public Password(String siteName, String username, String password) {
        this.siteName = siteName;
        this.username = username;
        this.password = password;
        this.dateSaved = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public String getSiteName() {
        return siteName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDateSaved() {
        return dateSaved;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDateSaved(String dateSaved) {
        this.dateSaved = dateSaved;
    }

}