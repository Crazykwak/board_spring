package CrazyKwak.board.member.service;

import CrazyKwak.board.exception.BusinessException;
import CrazyKwak.board.exception.ExceptionCode;
import CrazyKwak.board.member.dto.MemberJoinDto;
import CrazyKwak.board.member.entity.Member;
import CrazyKwak.board.member.mapper.MemberMapper;
import CrazyKwak.board.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long joinMember(MemberJoinDto memberJoinDto) {

        verifyMember(memberJoinDto);
        memberJoinDto.setPassword(bCryptPasswordEncoder.encode(memberJoinDto.getPassword()));
        Member member = memberMapper.memberJoinDtoToMember(memberJoinDto);
        memberRepository.save(member);

        return member.getId();
    }

    private void verifyMember(MemberJoinDto memberJoinDto) {
        Optional<Member> findMember = memberRepository.findByUserId(memberJoinDto.getUserId());
        findMember.ifPresent(el -> {
            throw new BusinessException(ExceptionCode.MEMBER_EXISTS);
        });
    }
}
