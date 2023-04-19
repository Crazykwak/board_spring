package CrazyKwak.board.freeboard.repository;

import CrazyKwak.board.freeboard.entity.FreeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long> {
    Page<FreeBoard> findFreeBoardsJByIsViewTrueOrderByCreatedAtDesc(Pageable pageable);
}
