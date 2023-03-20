package CrazyKwak.board.utils;

import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Component
public class SecretCode {

    public static String secretKey = "시크릿키를굉장히길게적으라는소리인지잘모르겠어서일단길게적어본다";
    public static long accessTokenValidationSecond = 1_000L;
    public static long refreshTokenValidationSecond = 3_000_000L;

    public static Key key = getKey();
    public static Key getKey() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
