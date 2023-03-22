package CrazyKwak.board.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Slf4j
@Component
public class DecryptService {

    private Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

    public DecryptService() throws NoSuchPaddingException, NoSuchAlgorithmException {
    }

    public String decryptLoginData(String encryptIdAndPassword) throws InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        SecretKeySpec keySpec = new SecretKeySpec("asdfafdsafsdashidofhasidofhaosid".getBytes(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec("aaaaaaaaaaaaaaaa".getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);

        byte[] decodeBytes = Base64.getDecoder().decode(encryptIdAndPassword);
        byte[] decrypted = cipher.doFinal(decodeBytes);
        return new String(decrypted, "UTF-8");
    }
}
