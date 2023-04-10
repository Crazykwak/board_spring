package CrazyKwak.board.freeboard.service;

import CrazyKwak.board.freeboard.dto.FreeBoardDto;
import CrazyKwak.board.freeboard.entity.FreeBoard;
import CrazyKwak.board.freeboard.mapper.FreeBoardMapper;
import CrazyKwak.board.freeboard.repository.FreeBoardRepository;
import CrazyKwak.board.member.entity.Member;
import CrazyKwak.board.member.service.MemberService;
import CrazyKwak.board.security.principal.PrincipalDetails;
import CrazyKwak.board.utils.PageAndContents;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FreeBoardService {

    private final FreeBoardRepository freeBoardRepository;
    private final FreeBoardMapper mapper;
    private final MemberService memberService;

    public PageAndContents getList(int page, int size) {

        Page<FreeBoard> list = freeBoardRepository.findFreeBoardsJByIsViewTrueOrderByCreatedAtDesc(PageRequest.of(page - 1, size));
        PageAndContents<FreeBoard> result = new PageAndContents(list.getContent(), list.getTotalElements(), list.getTotalPages());

        return result;
    }

    public void saveFreeBoardPost(FreeBoardDto freeBoardDto, PrincipalDetails principalDetails) {

        Member member = memberService.verifyMemberNotExists(principalDetails.getUsername());
        FreeBoard freeBoard = mapper.freeBoardDtoToFreeBoardWithMember(freeBoardDto, member.getUserId());
        freeBoardRepository.save(freeBoard);

    }
}
