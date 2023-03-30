package CrazyKwak.board.exception;

import lombok.Getter;

public enum ExceptionCode {

    MEMBER_EXISTS(409, "이미 존재하는 회원입니다."),
    MEMBER_NOT_EXISTS(404, "회원을 찾을 수 없습니다."),
    PASSWORD_NOT_MATCH(401, "비밀번호가 맞지 않습니다."),
    TOKEN_EXPIRED(401, "토큰 시간 만료"),
    TOKEN_NOT_EXISTS(404, "유효하지 않은 토큰입니다."),
    UNAUTHORIZED(401, "인증 실패!"),
    I_DONT_KNOW(500, "서버에서 뭔 일 났는디?");

    @Getter
    private int status;
    @Getter
    private String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

}
