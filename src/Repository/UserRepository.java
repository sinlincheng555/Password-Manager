package Repository;

import Model.User;
import Until.Encryption;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserRepository {

    private static final String STORAGE_DIR  = "";   // same folder as vault.csv / vault.key
    private static final String STORAGE_FILE = "users.dat";
    private static final String KEY_FILE     = "users.key";

    // In-memory map: username (lowercase) -> AES-256 encrypted password
    private final Map<String, String> users = new HashMap<>();

    private final Encryption encryption;

    // ── Constructor ───────────────────────────────────────────────────

    public UserRepository() {
        createStorageDirectoryIfNeeded();
        this.encryption = loadOrCreateKey();
        loadFromFile();
    }

    // ── Public API ────────────────────────────────────────────────────

    //encryt the passowrd into aes256 from plain text
    public boolean registerUser(String username, String plainPassword) {
        if (username == null || username.trim().isEmpty()) return false;
        if (plainPassword == null || plainPassword.isEmpty()) return false;
        if (users.containsKey(username.trim().toLowerCase())) return false;

        String encrypted = encryption.encrypt(plainPassword);
        users.put(username.trim().toLowerCase(), encrypted);
        saveToFile();
        return true;
    }

    //decryt the password to plainpassword
    public User loginUser(String username, String plainPassword) {
        if (username == null || plainPassword == null) return null;

        String encrypted = users.get(username.trim().toLowerCase());
        if (encrypted == null) return null;

        // Decrypt the stored password and compare directly
        String decrypted = encryption.decrypt(encrypted);
        if (decrypted.equals(plainPassword)) {
            return new User(username, encrypted);
        }
        return null;
    }

    //check if the username is taken or not
    public boolean usernameExists(String username) {
        if (username == null) return false;
        return users.containsKey(username.trim().toLowerCase());
    }

    // ── Key management ────────────────────────────────────────────────

    private Encryption loadOrCreateKey() {
        File keyFile = new File(KEY_FILE);

        if (keyFile.exists()) {
            // Load existing key
            try (BufferedReader reader = new BufferedReader(new FileReader(keyFile))) {
                String savedKey = reader.readLine();
                if (savedKey != null && !savedKey.trim().isEmpty()) {
                    System.out.println("[UserRepository] Loaded existing AES key.");
                    return new Encryption(savedKey.trim());
                }
            } catch (IOException e) {
                System.err.println("[UserRepository] Could not read key file: " + e.getMessage());
            }
        }

        // First run — generate a new key and save it
        Encryption newEncryption = new Encryption();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(keyFile))) {
            writer.write(newEncryption.exportKey());
            System.out.println("[UserRepository] Generated and saved new AES key.");
        } catch (IOException e) {
            System.err.println("[UserRepository] Could not save key file: " + e.getMessage());
        }

        return newEncryption;
    }


    // ── File I/O ──────────────────────────────────────────────────────

    private void loadFromFile() {
        File file = new File(STORAGE_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                int colonIndex = line.indexOf(':');
                if (colonIndex == -1) continue;

                String username  = line.substring(0, colonIndex).trim();
                String encrypted = line.substring(colonIndex + 1).trim();

                if (!username.isEmpty() && !encrypted.isEmpty()) {
                    users.put(username, encrypted);
                    System.out.println("[UserRepository] Loaded user: " + username);
                }
            }
        } catch (IOException e) {
            System.err.println("[UserRepository] Failed to load users: " + e.getMessage());
        }
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STORAGE_FILE, false))) {
            for (Map.Entry<String, String> entry : users.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
            System.out.println("[UserRepository] Saved " + users.size() + " user(s).");
        } catch (IOException e) {
            System.err.println("[UserRepository] Failed to save users: " + e.getMessage());
        }
    }

    private void createStorageDirectoryIfNeeded() {
        File dir = new File(STORAGE_DIR);
        if (!dir.exists()) dir.mkdirs();
    }
}