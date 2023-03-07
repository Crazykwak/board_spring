package CrazyKwak.board.member.entity;

import CrazyKwak.board.utils.BaseTime;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseTime {
    @Id
    @GeneratedValue
    private Long id;
    private String userId;
    private String email;
    private String password;

    @Builder
    public Member(String userId, String email, String password) {
        this.userId = userId;
        this.email = email;
        this.password = password;
    }
}
