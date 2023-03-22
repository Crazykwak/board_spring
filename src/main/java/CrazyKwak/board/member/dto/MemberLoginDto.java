package CrazyKwak.board.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberLoginDto {

    @NotBlank
    private String encryptIdPassword;

    @Null
    private String userId;

    @Null
    private String password;


}
