package CrazyKwak.board.security.filter;

import CrazyKwak.board.exception.BusinessException;
import CrazyKwak.board.exception.ExceptionCode;
import CrazyKwak.board.member.dto.MemberLoginDto;
import CrazyKwak.board.member.entity.Member;
import CrazyKwak.board.member.repository.MemberRepository;
import CrazyKwak.board.member.service.MemberService;
import CrazyKwak.board.security.principal.PrincipalDetails;
import CrazyKwak.board.token.TokenService;
import CrazyKwak.board.utils.DecryptService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final MemberService memberService;
    private final TokenService tokenService;
    private final DecryptService decryptService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        log.info("로그인 시도");

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            MemberLoginDto memberLoginDto = objectMapper.readValue(request.getInputStream(), MemberLoginDto.class);

            // 복호화 후 dto에 데이터 세팅해 줌.
            String userIdAndPassword = decryptService.decryptLoginData(memberLoginDto.getEncryptIdPassword());
            decryptService.splitLoginData(memberLoginDto, userIdAndPassword);

            Member member = memberService.verifyMemberNotExists(memberLoginDto.getUserId()); // 없으면 예외 터짐
            memberService.verifyPassword(memberLoginDto.getPassword(), member.getPassword()); // 비번 안맞으면 예외 터짐

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberLoginDto.getUserId(), memberLoginDto.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            return authentication;

        } catch (IOException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("JwtAuthenticationFilter에서 IO 예외 발생! = {}", e);
        }

        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        log.info("인증 성공!");

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        String accessToken = tokenService.getAccessToken(principalDetails.getUsername());
        String refreshToken = tokenService.getRefreshToken(principalDetails.getUsername());

        response.addHeader("Authorization", "Bearer " + accessToken);
        response.addHeader("RefreshToken", "Bearer " + refreshToken);

    }
}
