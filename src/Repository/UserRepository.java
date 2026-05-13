package Repository;

import Model.User;
import Until.Encryption;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserRepository {

    private static final String STORAGE_DIR  = "";
    private static final String STORAGE_FILE = "users.dat";
    private static final String KEY_FILE     = "users.key";

    private final Map<String, User> users = new HashMap<>();
    private final Encryption encryption;

    public UserRepository() {
        createStorageDirectoryIfNeeded();
        this.encryption = loadOrCreateKey();
        loadFromFile();
    }

    public boolean registerUser(String username, String plainPassword) {
        if (username == null || username.trim().isEmpty()) return false;
        if (plainPassword == null || plainPassword.isEmpty()) return false;
        if (users.containsKey(username.trim().toLowerCase())) return false;

        String encrypted = encryption.encrypt(plainPassword);
        User newUser = new User(username.trim(), encrypted);

        users.put(username.trim().toLowerCase(), newUser);
        saveToFile();
        return true;
    }

    public User loginUser(String username, String plainPassword) {
        if (username == null || plainPassword == null) return null;

        User storedUser = users.get(username.trim().toLowerCase());
        if (storedUser == null) return null;

        String decrypted = encryption.decrypt(storedUser.getMasterPassword());
        if (decrypted.equals(plainPassword)) {
            return storedUser;
        }
        return null;
    }

    public boolean usernameExists(String username) {
        if (username == null) return false;
        return users.containsKey(username.trim().toLowerCase());
    }

    public void updateUserDetails(User updatedUser) {
        users.put(updatedUser.getUsername().toLowerCase(), updatedUser);
        saveToFile();
    }

    private Encryption loadOrCreateKey() {
        File keyFile = new File(KEY_FILE);

        if (keyFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(keyFile))) {
                String savedKey = reader.readLine();
                if (savedKey != null && !savedKey.trim().isEmpty()) {
                    return new Encryption(savedKey.trim());
                }
            } catch (IOException e) {
                System.err.println("Could not read key file: " + e.getMessage());
            }
        }

        Encryption newEncryption = new Encryption();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(keyFile))) {
            writer.write(newEncryption.exportKey());
        } catch (IOException e) {
            System.err.println("Could not save key file: " + e.getMessage());
        }
        return newEncryption;
    }

    private void loadFromFile() {
        File file = new File(STORAGE_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(":", -1);
                if (parts.length < 2) continue;

                String username = parts[0].trim();
                String encryptedPass = parts[1].trim();

                User u = new User(username, encryptedPass);

                if (parts.length >= 5) {
                    u.setEmail(encryption.decrypt(parts[2].trim()));
                    u.setDob(encryption.decrypt(parts[3].trim()));
                    u.setPhone(encryption.decrypt(parts[4].trim()));
                }

                users.put(username.toLowerCase(), u);
            }
        } catch (IOException e) {
            System.err.println("Failed to load users: " + e.getMessage());
        }
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STORAGE_FILE, false))) {
            for (User u : users.values()) {
                String line = u.getUsername() + ":" + u.getMasterPassword() + ":" +
                        encryption.encrypt(u.getEmail()) + ":" +
                        encryption.encrypt(u.getDob()) + ":" +
                        encryption.encrypt(u.getPhone());
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Failed to save users: " + e.getMessage());
        }
    }

    private void createStorageDirectoryIfNeeded() {
        File dir = new File(STORAGE_DIR);
        if (!dir.exists() && !STORAGE_DIR.isEmpty()) dir.mkdirs();
    }
}