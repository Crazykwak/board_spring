package CrazyKwak.board.freeboard.repository;

import CrazyKwak.board.freeboard.entity.FreeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long> {
    Page<FreeBoard> findAll(Pageable pageable);
}
