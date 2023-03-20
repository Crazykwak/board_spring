package CrazyKwak.board.token;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/refresh_authorization")
    public ResponseEntity refreshAuthorization(@RequestBody RefreshToken refreshToken,
                                               HttpServletResponse response) {

        log.info("리프레시토큰 요청 들어옴");
        log.info(refreshToken.getRefreshToken());
        String token = refreshToken.getRefreshToken();

        String userId = tokenService.getClaims(token.replace("Bearer ", "")).get("userId", String.class);
        String accessToken = tokenService.getAccessToken(userId);
        response.addHeader("Authorization", "Bearer " + accessToken);

        return new ResponseEntity(HttpStatus.CREATED);
    }

}
