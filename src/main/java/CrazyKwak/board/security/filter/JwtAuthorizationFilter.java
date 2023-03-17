package CrazyKwak.board.security.filter;

import CrazyKwak.board.member.entity.Member;
import CrazyKwak.board.member.service.MemberService;
import CrazyKwak.board.security.principal.PrincipalDetails;
import CrazyKwak.board.token.TokenService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Date;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final MemberService memberService;
    private final TokenService tokenService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberService memberService, TokenService tokenService) {
        super(authenticationManager);
        this.memberService = memberService;
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        log.info("권한 필터 작동! 토큰 확인");
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer")) {
            log.error("토큰이 안들어왔습니다.");
            chain.doFilter(request, response);
            return;
        }

        String accessToken = header.replace("Bearer ", "");

        Claims claims = tokenService.getClaims(accessToken);
        tokenService.isExpire(claims.getExpiration(), new Date(System.currentTimeMillis())); // 토큰 만료면 예외 뱉음

        String userId = claims.get("userId", String.class);

        if (userId != null) {
            Member member = memberService.verifyMemberNotExists(userId); // 회원 없으면 예외 뱉음
            PrincipalDetails principalDetails = new PrincipalDetails(member);
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);

    }
}
