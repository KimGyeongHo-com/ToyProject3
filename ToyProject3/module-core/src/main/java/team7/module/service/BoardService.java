package team7.module.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import team7.module.domain.user.User;
import team7.module.dto.board.BoardRequest;
import team7.module.dto.board.BoardResponse;


public interface BoardService {

    void savaBoard(BoardRequest.saveBoardDTO saveBoardDTO, User user);

    Page<BoardResponse.BoardListDTO> findAll(String boardType, String search, Pageable pageable);

    BoardResponse.BoardDetailDTO getDetailBoard(Long boardId);

    void updateBoard(BoardRequest.updateBoardDTO updateBoardDTO, Long boardId);

    void deleteBoard(Long boardId, User user);

    BoardResponse.BoardUpdateDTO updateBoardForm(Long boardId, User user);
}
