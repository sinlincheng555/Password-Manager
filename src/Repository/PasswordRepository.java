package Repository;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import Model.Password;
import Model.SecureNote;
import Until.Encryption;

public class PasswordRepository {

    private static final String KEY_FILE  = "vault.key";
    private static final String DATA_FILE = "vault.csv";

    private List<SecureNote> secureNotes = new ArrayList<>();
    private Encryption cryptoEngine;

    public PasswordRepository() {
        // If a saved key exists, load it — otherwise generate a new one and save it
        if (Files.exists(Paths.get(KEY_FILE))) {
            try {
                String savedKey = Files.readString(Paths.get(KEY_FILE)).trim();
                this.cryptoEngine = new Encryption(savedKey);
            } catch (IOException e) {
                throw new RuntimeException("Failed to load vault key", e);
            }
        } else {
            this.cryptoEngine = new Encryption();
            saveKey();
        }
    }

    // ── Key persistence ───────────────────────────────────────────────
    private void saveKey() {
        try {
            Files.writeString(Paths.get(KEY_FILE), cryptoEngine.exportKey());
        } catch (IOException e) {
            throw new RuntimeException("Failed to save vault key", e);
        }
    }

    // ── Give DashboardView access to the same Encryption instance ─────
    public Encryption getEncryption() {
        return cryptoEngine;
    }

    // ── Password persistence ──────────────────────────────────────────
    public void saveAllPasswords(List<Password> passwords) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            for (Password p : passwords) {
                // Format: site|username|encryptedPassword|dateSaved
                writer.write(
                        escape(p.getSiteName()) + "|" +
                                escape(p.getUsername()) + "|" +
                                escape(p.getPassword()) + "|" +
                                escape(p.getDateSaved())
                );
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save passwords", e);
        }
    }

    public List<Password> loadAllPasswords() {
        List<Password> passwords = new ArrayList<>();
        if (!Files.exists(Paths.get(DATA_FILE))) return passwords;

        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", -1);
                if (parts.length < 3) continue;
                String site      = unescape(parts[0]);
                String username  = unescape(parts[1]);
                String encrypted = unescape(parts[2]);
                // Password constructor sets dateSaved to now — we restore it manually
                Password p = new Password(site, username, encrypted);
                if (parts.length >= 4) {
                    p.setDateSaved(unescape(parts[3]));
                }
                passwords.add(p);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load passwords", e);
        }
        return passwords;
    }

    // ── Pipe character escaping (in case site/username contains |) ────
    private String escape(String value) {
        return value == null ? "" : value.replace("|", "\\pipe");
    }

    private String unescape(String value) {
        return value == null ? "" : value.replace("\\pipe", "|");
    }

    // ── Secure Notes (in-memory only for now) ─────────────────────────
    public void addSecureNote(SecureNote note) {
        secureNotes.add(note);
    }

    public List<SecureNote> getAllSecureNotes() {
        return secureNotes;
    }

    public void deleteSecureNote(SecureNote note) {
        secureNotes.remove(note);
    }
}