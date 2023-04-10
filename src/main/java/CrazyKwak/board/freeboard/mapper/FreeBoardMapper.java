package CrazyKwak.board.freeboard.mapper;

import CrazyKwak.board.freeboard.dto.FreeBoardDto;
import CrazyKwak.board.freeboard.entity.FreeBoard;
import org.springframework.stereotype.Component;

@Component
public class FreeBoardMapper {


    public FreeBoard freeBoardDtoToFreeBoardWithMember(FreeBoardDto freeBoardDto, String userId) {

        return FreeBoard.builder()
                .title(freeBoardDto.getTitle())
                .body(freeBoardDto.getBody())
                .userId(userId)
                .build();
    }
}
