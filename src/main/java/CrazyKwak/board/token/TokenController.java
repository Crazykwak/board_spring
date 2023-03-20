package CrazyKwak.board.token;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/refresh_authorization")
    public ResponseEntity refreshAuthorization(@RequestBody String refreshToken,
                                               HttpServletResponse response) {

        String userId = tokenService.getClaims(refreshToken).get("userId", String.class);
        String accessToken = tokenService.getAccessToken(userId);
        response.addHeader("Authorization", "Bearer " + accessToken);

        return new ResponseEntity(HttpStatus.CREATED);
    }

}
