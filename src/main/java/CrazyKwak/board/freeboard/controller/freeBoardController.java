package CrazyKwak.board.freeboard.controller;

import CrazyKwak.board.freeboard.dto.FreeBoardDto;
import CrazyKwak.board.freeboard.dto.FreeBoardResponseDto;
import CrazyKwak.board.freeboard.entity.FreeBoard;
import CrazyKwak.board.freeboard.service.FreeBoardService;
import CrazyKwak.board.security.principal.PrincipalDetails;
import CrazyKwak.board.utils.PageAndContents;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/free-board")
public class freeBoardController {

    private final FreeBoardService freeBoardService;

    /**
     * 자유게시판 글 목록을 뿌려주는 api
     */
    @GetMapping
    public ResponseEntity getFreeBoardList(@RequestParam int page,
                                           @RequestParam int size) {

        PageAndContents<FreeBoard> result = freeBoardService.getList(page, size);

        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/detail")
    public ResponseEntity getFreeBoardList(@RequestParam long id) {

        FreeBoardResponseDto freeBoardResponseDto = freeBoardService.getBoardOne(id);

        return new ResponseEntity(freeBoardResponseDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveFreeBoardPost(@RequestBody FreeBoardDto freeBoardDto,
                                            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        freeBoardService.saveFreeBoardPost(freeBoardDto, principalDetails);

        return new ResponseEntity(HttpStatus.CREATED);
    }



}
