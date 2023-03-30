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
    private String encryptIdPassword;

    @Builder
    public MemberJoinDto(String userId, String email, String password, String encryptIdPassword) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.encryptIdPassword = encryptIdPassword;
    }
}
