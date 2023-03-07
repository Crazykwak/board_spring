package CrazyKwak.board.utils;

import CrazyKwak.board.freeboard.entity.FreeBoard;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class PageAndContents <T> {
    private List<T> content;
    private long totalElements;
    private int totalPages;
    public PageAndContents(List<T> content, long totalElements, int totalPages) {
        this.content = content;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }
}
