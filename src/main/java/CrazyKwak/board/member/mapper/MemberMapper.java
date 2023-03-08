package CrazyKwak.board.member.mapper;

import CrazyKwak.board.member.dto.MemberJoinDto;
import CrazyKwak.board.member.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public Member memberJoinDtoToMember(MemberJoinDto memberJoinDto) {

        return Member.builder()
                .userId(memberJoinDto.getUserId())
                .email(memberJoinDto.getEmail())
                .password(memberJoinDto.getPassword())
                .build();
    }


}
