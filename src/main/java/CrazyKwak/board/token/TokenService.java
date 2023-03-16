package CrazyKwak.board.token;

import CrazyKwak.board.utils.SecretCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

import static CrazyKwak.board.utils.SecretCode.*;

@Service
public class TokenService {

    public String getAccessToken(String userId) {
        Claims claims = Jwts.claims();
        claims.put("userId", userId);

        return Jwts.builder()
                .setSubject("AccessToken")
                .setIssuedAt(new Date())
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + (accessTokenValidationSecond * 1000)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims getClaims(String accessToken) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
    }

    public boolean isExpire(Date expiration, Date now) {
        return expiration.before(now);
    }
}
