package CrazyKwak.board.member.repository;

import CrazyKwak.board.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
