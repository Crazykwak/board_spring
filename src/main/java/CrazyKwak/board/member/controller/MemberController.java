package CrazyKwak.board.member.controller;

import CrazyKwak.board.member.dto.MemberJoinDto;
import CrazyKwak.board.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity joinMember(@Validated @RequestBody MemberJoinDto memberJoinDto) {

        memberService.joinMember(memberJoinDto);

        return new ResponseEntity(HttpStatus.CREATED);
    }


}
