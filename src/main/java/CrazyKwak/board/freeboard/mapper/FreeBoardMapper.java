package CrazyKwak.board.freeboard.mapper;

import CrazyKwak.board.freeboard.dto.FreeBoardDto;
import CrazyKwak.board.freeboard.entity.FreeBoard;
import CrazyKwak.board.member.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class FreeBoardMapper {


    public FreeBoard freeBoardDtoToFreeBoardWithMember(FreeBoardDto freeBoardDto, Member member) {

        return FreeBoard.builder()
                .title(freeBoardDto.getTitle())
                .body(freeBoardDto.getBody())
                .member(member)
                .build();
    }
}
