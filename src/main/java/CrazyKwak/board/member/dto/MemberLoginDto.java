package CrazyKwak.board.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberLoginDto {

    @NotBlank
    private String userId;

    @NotBlank
    private String password;


}
