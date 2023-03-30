package CrazyKwak.board.token;

import CrazyKwak.board.config.SecretCode;
import CrazyKwak.board.exception.BusinessException;
import CrazyKwak.board.exception.ExceptionCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;


@Service
@RequiredArgsConstructor
public class TokenService {

    private final SecretCode secretCode;

    public String getAccessToken(String userId) {
        Claims claims = Jwts.claims();
        claims.put("userId", userId);

        return Jwts.builder()
                .setSubject("AccessToken")
                .setIssuedAt(new Date())
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + (secretCode.getAccessTokenValidationSecond() * 1000)))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getRefreshToken(String userId) {
        Claims claims = Jwts.claims();
        claims.put("userId", userId);

        String refreshToken = Jwts.builder()
                .setSubject("RefreshToken")
                .setIssuedAt(new Date())
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + (secretCode.getRefreshTokenValidationSecond() * 1000)))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

        return refreshToken;
    }

    public Claims getClaims(String accessToken) {

        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
    }

    /**
     * 토큰 시간이 만료 됐으면 예외를 뱉는다. (HttpStatus 401)
     * @param expiration
     * @param now
     */
    public void isExpire(Date expiration, Date now) {
        if (expiration.before(now)) {
            throw new BusinessException(ExceptionCode.TOKEN_EXPIRED);
        }
    }

    public Key getKey() {
        byte[] keyBytes = secretCode.getSecretKey().getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
