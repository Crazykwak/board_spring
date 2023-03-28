package CrazyKwak.board.utils;

import CrazyKwak.board.config.SecretCode;
import CrazyKwak.board.member.dto.MemberLoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Slf4j
@RequiredArgsConstructor
@Service
public class DecryptService {

    private final SecretCode secretCode;

    private Cipher cipher;

    {
        try {
            cipher = Cipher.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
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

        byte[] decryptForBase64 = Base64.getDecoder().decode(encryptIdAndPassword);

        String stringKey = secretCode.RSA_PRIVATE_KEY;

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
