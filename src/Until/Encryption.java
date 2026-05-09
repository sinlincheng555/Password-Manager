package Until;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.util.Base64;

public class Encryption {

    private SecretKey key;
    private final int KEY_SIZE = 256;
    private final int T_LEN = 128;

    public Encryption() {
        try {
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            generator.init(KEY_SIZE);
            this.key = generator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialise AES key", e);
        }
    }

    public Encryption(String savedKeyBase64) {
        try {
            byte[] decodedKey = Base64.getDecoder().decode(savedKeyBase64);
            this.key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        } catch (Exception e) {
            throw new RuntimeException("Failed to load existing AES key", e);
        }
    }

    public String exportKey() {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public String encrypt(String message) {
        if (message == null || message.isEmpty()) return "";
        try {
            byte[] messageInBytes = message.getBytes();
            Cipher encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
            encryptionCipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] iv = encryptionCipher.getIV();
            byte[] encryptedBytes = encryptionCipher.doFinal(messageInBytes);

            ByteBuffer byteBuffer = ByteBuffer.allocate(iv.length + encryptedBytes.length);
            byteBuffer.put(iv);
            byteBuffer.put(encryptedBytes);
            byte[] combinedBytes = byteBuffer.array();

            return Base64.getEncoder().encodeToString(combinedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed", e);
        }
    }

    public String decrypt(String encryptedMessage) {
        if (encryptedMessage == null || encryptedMessage.isEmpty()) return "";
        try {
            byte[] combinedBytes = Base64.getDecoder().decode(encryptedMessage);

            byte[] iv = new byte[12];
            byte[] encryptedBytes = new byte[combinedBytes.length - 12];

            System.arraycopy(combinedBytes, 0, iv, 0, 12);
            System.arraycopy(combinedBytes, 12, encryptedBytes, 0, encryptedBytes.length);

            Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec spec = new GCMParameterSpec(T_LEN, iv);
            decryptionCipher.init(Cipher.DECRYPT_MODE, key, spec);

            byte[] decryptedBytes = decryptionCipher.doFinal(encryptedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Decryption failed", e);
        }
    }
}