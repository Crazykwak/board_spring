package CrazyKwak.board.member.controller;

import CrazyKwak.board.member.dto.MemberJoinDto;
import CrazyKwak.board.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity joinMember(@Validated @RequestBody MemberJoinDto memberJoinDto) {

        log.info(String.valueOf(memberJoinDto));

        memberService.joinMember(memberJoinDto);

        return new ResponseEntity(HttpStatus.CREATED);
    }


}
