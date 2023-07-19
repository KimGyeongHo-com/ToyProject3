package team7.example.ToyProject3.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import team7.example.ToyProject3.domain.user.User;
import team7.example.ToyProject3.dto.board.BoardRequest;
import team7.example.ToyProject3.dto.board.BoardResponse;

public interface BoardService {

    void savaBoard(BoardRequest.saveBoardDTO saveBoardDTO, User user);

    Page<BoardResponse.BoardListDTO> findAll(String boardType, String search, Pageable pageable);

    BoardResponse.BoardDetailDTO getDetailBoard(Long boardId);

    void updateBoard(BoardRequest.updateBoardDTO updateBoardDTO, Long boardId);

    void deleteBoard(Long boardId, User user);

    BoardResponse.BoardUpdateDTO updateBoardForm(Long boardId, User user);
}
