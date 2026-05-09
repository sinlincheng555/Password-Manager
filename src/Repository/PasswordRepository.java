package Repository;

import java.util.ArrayList;
import java.util.List;
import Model.SecureNote;
import Until.Encryption;

public class PasswordRepository {
    private List<SecureNote> secureNotes = new ArrayList<>();
    private Encryption cryptoEngine;


    public PasswordRepository() {
        this.cryptoEngine = new Encryption();
    }

    public void saveNewPassword(String plainTextPassword) {
        String secureData = cryptoEngine.encrypt(plainTextPassword);
        System.out.println("Success! Encrypted data to save: " + secureData);
    }

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