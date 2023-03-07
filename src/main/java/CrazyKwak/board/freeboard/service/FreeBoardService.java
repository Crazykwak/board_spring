package CrazyKwak.board.freeboard.service;

import CrazyKwak.board.freeboard.entity.FreeBoard;
import CrazyKwak.board.freeboard.repository.FreeBoardRepository;
import CrazyKwak.board.utils.PageAndContents;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FreeBoardService {
    private final FreeBoardRepository freeBoardRepository;

    public PageAndContents getList(int page, int size) {
        Page<FreeBoard> list = freeBoardRepository.findAll(PageRequest.of(page - 1, size));

        PageAndContents<FreeBoard> result = new PageAndContents(list.getContent(), list.getTotalElements(), list.getTotalPages());

        return result;
    }

}
