package CrazyKwak.board.exception;

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
}
