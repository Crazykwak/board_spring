package CrazyKwak.board.exception;

import lombok.Getter;

public enum ExceptionCode {

    MEMBER_EXISTS(409, "이미 존재하는 회원입니다.");

    @Getter
    private int status;
    @Getter
    private String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

}
