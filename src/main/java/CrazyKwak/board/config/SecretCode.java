package CrazyKwak.board.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@Getter
public class SecretCode {

    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.accessTokenValidationSecond}")
    private long accessTokenValidationSecond;
    @Value("${jwt.refreshTokenValidationSecond}")
    private long refreshTokenValidationSecond;
    @Value("${decrypt.privateKey}")
    private  String RSA_PRIVATE_KEY;

}
