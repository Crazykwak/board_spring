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
    public String decryptLoginData(String encryptIdAndPassword) throws InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        SecretKeySpec keySpec = new SecretKeySpec("asdfafdsafsdashidofhasidofhaosid".getBytes(), "AES");

        String[] split = encryptIdAndPassword.split(":");
        String IVEncrypt = split[0];
        String encrypt = split[1];
        byte[] IV = Base64.getDecoder().decode(IVEncrypt);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(IV);

        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);

        byte[] decodeBytes = Base64.getDecoder().decode(encrypt);
        byte[] decrypted = cipher.doFinal(decodeBytes);
        return new String(decrypted, "UTF-8");
    }

    public void splitLoginData(MemberLoginDto memberLoginDto, String userIdAndPassword) {
        String[] split = userIdAndPassword.split("\\|");
        memberLoginDto.setUserId(split[0]);
        memberLoginDto.setPassword(split[1]);

    }
}
