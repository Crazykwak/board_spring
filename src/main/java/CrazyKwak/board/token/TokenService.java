package CrazyKwak.board.token;

import CrazyKwak.board.exception.BusinessException;
import CrazyKwak.board.exception.ExceptionCode;
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

    public String getRefreshToken(String userId) {
        Claims claims = Jwts.claims();
        claims.put("userId", userId);

        String refreshToken = Jwts.builder()
                .setSubject("RefreshToken")
                .setIssuedAt(new Date())
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + (refreshTokenValidationSecond * 1000)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return refreshToken;
    }

    public Claims getClaims(String accessToken) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
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
        if (!expiration.before(now)) {
            throw new BusinessException(ExceptionCode.TOKEN_EXPIRED);
        }
    }

}
