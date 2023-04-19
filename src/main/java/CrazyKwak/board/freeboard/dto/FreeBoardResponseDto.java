package CrazyKwak.board.freeboard.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class FreeBoardResponseDto {

    private Long id;
    private String title;
    private String body;
    private LocalDateTime createdAt;

    @Builder
    public FreeBoardResponseDto(Long id, String title, String body, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.createdAt = createdAt;
    }
}
