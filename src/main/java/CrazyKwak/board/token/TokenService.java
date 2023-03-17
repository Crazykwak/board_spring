package CrazyKwak.board.token;

import CrazyKwak.board.exception.BusinessException;
import CrazyKwak.board.exception.ExceptionCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

import static CrazyKwak.board.utils.SecretCode.*;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

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

        insertRefreshToken(userId, refreshToken);

        return refreshToken;
    }

    @Transactional
    private void insertRefreshToken(String userId, String refreshToken) {

        RefreshToken token = RefreshToken.builder()
                .token(refreshToken)
                .userId(userId)
                .build();

        tokenRepository.save(token);
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

    public String verifyRefreshTokenForAccessTokenAndGetUserId(String refreshToken) {

        Claims claims = getClaims(refreshToken);
        isExpire(claims.getExpiration(), new Date(System.currentTimeMillis()));
        String userId = claims.get("userId", String.class);

        tokenRepository.findRefreshTokenByUserIdAndToken(userId, refreshToken).orElseThrow(
                () -> new BusinessException(ExceptionCode.TOKEN_NOT_EXISTS)
        );

        return userId;
    }
}
