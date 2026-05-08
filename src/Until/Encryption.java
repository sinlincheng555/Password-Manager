package Until;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Encryption {
    public String encrypt(String text) {
        if (text == null) return "";
        return Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
    }

    public String decrypt(String encryptedText) {
        if (encryptedText == null || encryptedText.isEmpty()) return "";
        return new String(Base64.getDecoder().decode(encryptedText), StandardCharsets.UTF_8);
    }
}
