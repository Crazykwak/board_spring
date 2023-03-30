package CrazyKwak.board.member.service;

import CrazyKwak.board.exception.BusinessException;
import CrazyKwak.board.exception.ExceptionCode;
import CrazyKwak.board.member.dto.MemberJoinDto;
import CrazyKwak.board.member.entity.Member;
import CrazyKwak.board.member.mapper.MemberMapper;
import CrazyKwak.board.member.repository.MemberRepository;
import CrazyKwak.board.utils.DecryptService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final DecryptService decryptService;


    public Long joinMember(MemberJoinDto memberJoinDto) {

        decryptInMemberService(memberJoinDto);
        verifyMemberAlreadyExists(memberJoinDto.getUserId());
        memberJoinDto.setPassword(bCryptPasswordEncoder.encode(memberJoinDto.getPassword()));
        Member member = memberMapper.memberJoinDtoToMember(memberJoinDto);
        memberRepository.save(member);

        return member.getId();
    }

    /**
     * userId로 된 회원이 있는지 확인.
     * 있을 경우 MEMBER_EXISTS 예외를 터트린다.
     * @param userId
     */
    public void verifyMemberAlreadyExists(String userId) {
        Optional<Member> findMember = memberRepository.findByUserId(userId);
        findMember.ifPresent(el -> {
            throw new BusinessException(ExceptionCode.MEMBER_EXISTS);
        });
    }

    /**
     * userId로 된 회원이 있는지 확인
     * 없을 경우 MEMBER_NOT_EXISTS 예외를 터트린다.
     * @param userId
     */
    public Member verifyMemberNotExists(String userId) {
        Optional<Member> findMember = memberRepository.findByUserId(userId);
        return findMember.orElseThrow(
                () -> new BusinessException(ExceptionCode.MEMBER_NOT_EXISTS)
        );
    }

    /**
     * 로그인 비밀번호 매치
     * verifyPassword(로그인 시도하는 비번, 실제 비번)
     * @param loginTryPassword
     * @param originalMemberPassword
     */
    public void verifyPassword(String loginTryPassword, String originalMemberPassword) {
        if (!bCryptPasswordEncoder.matches(loginTryPassword, originalMemberPassword)) {
            throw new BusinessException(ExceptionCode.PASSWORD_NOT_MATCH);
        }
    }

    private void decryptInMemberService(MemberJoinDto memberJoinDto) {
        String decrypted;

        try {
            decrypted = decryptService.decryptLoginData(memberJoinDto.getEncryptIdPassword());
        } catch (IOException | InvalidAlgorithmParameterException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

        decryptService.splitJoinData(memberJoinDto, decrypted);
    }
}
