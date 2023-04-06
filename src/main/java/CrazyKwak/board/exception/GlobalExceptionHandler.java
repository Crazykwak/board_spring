package CrazyKwak.board.exception;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity businessExceptionHandle(BusinessException businessException) {

        log.error("비즈니스 예외 발생! = {}", businessException);

        return new ResponseEntity(businessException.getExceptionCode().getMessage(), HttpStatusCode.valueOf(businessException.getExceptionCode().getStatus()));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity expiredJwtExceptionHandle(ExpiredJwtException expiredJwtException) {
        log.error("리프레시 토큰 시간 만료");
        BusinessException exception = new BusinessException(ExceptionCode.TOKEN_EXPIRED);
        return new ResponseEntity(new ExceptionResponse(exception.getMessage()), HttpStatusCode.valueOf(exception.getExceptionCode().getStatus()));
    }
}
