package Repository;

import java.util.ArrayList;
import java.util.List;
import Model.SecureNote;
import Until.Encryption;

public class PasswordRepository {
    private List<SecureNote> secureNotes = new ArrayList<>();
    private Encryption cryptoEngine;


    public PasswordRepository() {
        /*
        if this is the first run, use new encryption
        if loading from a saved key, use new encryption (savedkey string)
         */
        this.cryptoEngine = new Encryption();
    }
    //your method that hanles UI data
    public void saveNewPassword(String plainTextPassword) {
        //encrypt plain text data
        String secureData = cryptoEngine.encrypt(plainTextPassword);
        //pass securedata to your file saving logic here if needed
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