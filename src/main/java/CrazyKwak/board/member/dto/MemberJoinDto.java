package CrazyKwak.board.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberJoinDto {

    @NotBlank(message = "아이디 필수")
    private String userId;
    @NotBlank(message = "이메일 필수")
    private String email;
    @NotBlank(message = "비번 필수")
    private String password;

    @Builder
    public MemberJoinDto(String userId, String email, String password) {
        this.userId = userId;
        this.email = email;
        this.password = password;
    }
}
