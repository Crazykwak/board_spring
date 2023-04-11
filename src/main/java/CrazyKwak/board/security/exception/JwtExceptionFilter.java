package CrazyKwak.board.security.exception;

import CrazyKwak.board.exception.BusinessException;
import CrazyKwak.board.exception.ExceptionCode;
import CrazyKwak.board.exception.ExceptionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtExceptionFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        ObjectMapper om = new ObjectMapper();

        try {
            filterChain.doFilter(request, response);
            log.info("토큰 예외 필터 진입");
        } catch (ExpiredJwtException e) {

            log.info("액세스 토큰 만료", e);
            response.setStatus(401);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");

            int status = ExceptionCode.TOKEN_EXPIRED.getStatus();
            String message = ExceptionCode.TOKEN_EXPIRED.getMessage();
            ExceptionResponse exceptionResponse = new ExceptionResponse(message, status);
            om.writeValue(response.getWriter(), new ResponseEntity<>(exceptionResponse, HttpStatusCode.valueOf(status)));

        } catch (BusinessException e) {

            log.info("기타 비즈니스 예외", e);
            response.setStatus(e.getExceptionCode().getStatus());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            om.writeValue(response.getWriter(), new ResponseEntity<>(e, HttpStatusCode.valueOf(e.getExceptionCode().getStatus())));

        } catch (Exception e) {

            log.info("예외 터짐", e);
            BusinessException exception = new BusinessException(ExceptionCode.I_DONT_KNOW);
            om.writeValue(response.getWriter(), new ResponseEntity<>(exception, HttpStatusCode.valueOf(exception.getExceptionCode().getStatus())));

        }
    }

}
