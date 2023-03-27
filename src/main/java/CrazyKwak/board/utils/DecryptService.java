package CrazyKwak.board.utils;

import CrazyKwak.board.member.dto.MemberLoginDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.util.Base64;

@Slf4j
@Component
public class DecryptService {

    private Cipher cipher = Cipher.getInstance("RSA");

    public DecryptService() throws NoSuchPaddingException, NoSuchAlgorithmException {
    }

    /**
     * 아이디 비번 복호화 하는 메서드임
     * 가져온 encryptIdAndPassword를 정해진 문자열로 나눈 후
     * 앞 부분은 IV, 뒷 부분은 id와 비번이 암호화 돼 있음.
     * @param encryptIdAndPassword
     * @return
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws UnsupportedEncodingException
     */
    public String decryptLoginData(String encryptIdAndPassword) throws InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException {
//        SecretKeySpec keySpec = new SecretKeySpec("asdfafdsafsdashidofhasidofhaosid".getBytes(), "AES");
//
//        String[] split = encryptIdAndPassword.split(":");
//        String IVEncrypt = split[0];
//        String encrypt = split[1];
//        byte[] IV = Base64.getDecoder().decode(IVEncrypt);
//        IvParameterSpec ivParameterSpec = new IvParameterSpec(IV);
//
//        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);
//
//        byte[] decodeBytes = Base64.getDecoder().decode(encrypt);
//        byte[] decrypted = cipher.doFinal(decodeBytes);
        byte[] decryptForBase64 = Base64.getDecoder().decode(encryptIdAndPassword);
        String stringKey = "-----BEGIN PRIVATE KEY-----\n" +
                "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGAdTWjqQ7pRfY9vqJq\n" +
                "ZnAICu5iSvV99aHtGxZ5FWke9UH7dm1jQU+W7YFOX6B02fyYXJSy0aauOAH0xclR\n" +
                "nYvHs/zZ3dcBolhIa71SicwxgrLc0/hHVcSc7EfTm7u4DZPV7YTQSW4UwAGj7anw\n" +
                "xlpfVj3dA9/xTlmkBvUlvNQWiv0CAwEAAQKBgBJcq94BSwQ91mB2G0sfA/OKcvWh\n" +
                "TfFcQ8P5mlCDXmk3xAhCjMkfidp9ZmTOw1+fFdNzLCxx+1sk207dAh83VbqjV/Wx\n" +
                "ODEgIbNWXd3J7pvD+NLX46yVYwGCQ5a2dl0V+1t/kn9SkaV9QDTPUNJuyysdl0U9\n" +
                "I4vIrEi8hFilJPaBAkEAuk6/5fpn1TFg2DJP+oaxQ2sDxsnb8aYd7Afy5ITVQuiy\n" +
                "AK3N5YtVuUe+D/ggkDFuhn0O7XnAZGpnOngwJ99XXQJBAKEN5W+fUaSbIe6+1CrF\n" +
                "us40esaVhpBf+zvHDBpFdUv0JvjaID9UoL3bn5fWC45Y2B0MDqtI+rDUJ//32Xug\n" +
                "6CECQGPt+WARf8AKTDeqRNvLgyj2LYBipWZoA4SmftGzre/FgDR9BQMqSUCoCnnm\n" +
                "TREKco4QZgFaXKd/qv7hmHLKiMECQQCOoXfn+vibBqhuf6H2n1Zo+CEjbZjWk6oz\n" +
                "rnwRaCIROhCposOcUp5ohfA4z988GYKLdvomx2L852blUeLRkhcBAkEAg0K5JOjE\n" +
                "lUv1LM+hZ9VtXLJmGgyXADIrUSIr9tdtEZjJTFjJFyqSYEdFrebCmj51enM08Teg\n" +
                "QuJsMG6QcrcvTQ==\n" +
                "-----END PRIVATE KEY-----";

        stringKey = stringKey.replace("-----BEGIN PRIVATE KEY-----", "")
                    .replaceAll("\\n", "")
                    .replace("-----END PRIVATE KEY-----", "");

        byte[] decode = Base64.getDecoder().decode(stringKey);

        PrivateKey privateKey = KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(decode));

        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decrypted = cipher.doFinal(decryptForBase64);

        return new String(decrypted, "UTF-8");
    }

    public void splitLoginData(MemberLoginDto memberLoginDto, String userIdAndPassword) {
        String[] split = userIdAndPassword.split("\\|");
        memberLoginDto.setUserId(split[0]);
        memberLoginDto.setPassword(split[1]);

    }
}
