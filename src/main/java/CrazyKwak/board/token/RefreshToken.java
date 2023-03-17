package CrazyKwak.board.token;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class RefreshToken {
    @Id
    private Long id;

    private String token;
    private String userId;

    @Builder
    public RefreshToken(String token, String userId) {
        this.token = token;
        this.userId = userId;
    }

}
