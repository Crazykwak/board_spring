package CrazyKwak.board.security.exception;

import CrazyKwak.board.exception.BusinessException;
import CrazyKwak.board.exception.ExceptionCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ObjectMapper om = new ObjectMapper();

        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            BusinessException exception = new BusinessException(ExceptionCode.TOKEN_EXPIRED);
            response.setStatus(401);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            om.writeValue(response.getWriter(), new ResponseEntity<>(exception.getExceptionCode().getMessage(), HttpStatusCode.valueOf(exception.getExceptionCode().getStatus())));

        } catch (BusinessException e) {
            response.setStatus(e.getExceptionCode().getStatus());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            om.writeValue(response.getWriter(), new ResponseEntity<>(e.getExceptionCode().getMessage(), HttpStatusCode.valueOf(e.getExceptionCode().getStatus())));
        }

    }
}
