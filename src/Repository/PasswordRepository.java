package Repository;

import java.util.ArrayList;
import java.util.List;
import Model.SecureNote;
public class PasswordRepository {
    private List<SecureNote> secureNotes = new ArrayList<>();

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