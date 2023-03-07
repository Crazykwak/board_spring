package CrazyKwak.board.freeboard.entity;


import CrazyKwak.board.member.entity.Member;
import CrazyKwak.board.utils.BaseTime;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class FreeBoard extends BaseTime {
    @Id @GeneratedValue
    private Long id;
    private String title;
    private String body;
    private boolean isView;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder
    public FreeBoard(String title, String body, LocalDateTime createdAt, LocalDateTime modifiedAt, boolean isView, Member member) {
        this.title = title;
        this.body = body;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.isView = isView;
        this.member = member;
    }
}
