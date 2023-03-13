package CrazyKwak.board.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class TokenService {

    private final long accessTokenValidationSecond = 3000;
    private final String secretKey = "시크릿키를굉장히길게적으라는소리인지잘모르겠어서일단길게적어본다";

    private Key getKey() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getAccessToken(String userId) {
        Claims claims = Jwts.claims();
        claims.put("userId", userId);

        return Jwts.builder()
                .setSubject("AccessToken")
                .setIssuedAt(new Date())
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + (accessTokenValidationSecond * 1000)))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
