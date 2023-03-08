package CrazyKwak.board.member.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberJoinDto {

    private String userId;
    private String email;
    private String password;

    @Builder
    public MemberJoinDto(String userId, String email, String password) {
        this.userId = userId;
        this.email = email;
        this.password = password;
    }
}
