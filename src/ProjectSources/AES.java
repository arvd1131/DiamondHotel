package ProjectSources;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {

    private static final String KEY = "YourSecretKey123"; // 16 bute 32,64,128
    private static final byte[] IV = new byte[16];

    public static String encrypt(String plainText) { //123
        try {
            IvParameterSpec ivSpec = new IvParameterSpec(IV);
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

            //123
            byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));

            //nmbbJkkjjBKJBKJbkjkjBkjb
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            System.err.println(e);
            return "ENCRYPT_ERR";
        }
    }

    public static String decrypt(String cipherText) { //nmbbJkkjjBKJBKJbkjkjBkjb
        try {

            IvParameterSpec icSpec = new IvParameterSpec(IV);
            SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, icSpec);

            byte[] decodedBytes = Base64.getDecoder().decode(cipherText);

            byte[] original = cipher.doFinal(decodedBytes);
            return new String(original, "UTF-8");
        } catch (Exception e) {
            System.err.println(e);
            return "DECRYPT_ERR";
        }
    }
}
