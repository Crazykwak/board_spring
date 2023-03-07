package CrazyKwak.board.freeboard.controller;

import CrazyKwak.board.freeboard.entity.FreeBoard;
import CrazyKwak.board.freeboard.service.FreeBoardService;
import CrazyKwak.board.utils.PageAndContents;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

        PageAndContents<FreeBoard> body = freeBoardService.getList(page, size);

        return new ResponseEntity(body, HttpStatus.OK);
    }



}
