package team7.example.ToyProject3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.example.ToyProject3.domain.Board;
import team7.example.ToyProject3.domain.BoardType;
import team7.example.ToyProject3.domain.User;
import team7.example.ToyProject3.domain.UserAdaptor;
import team7.example.ToyProject3.dto.board.BoardRequest;
import team7.example.ToyProject3.dto.board.BoardResponse;
import team7.example.ToyProject3.repository.BoardRepository;
import team7.example.ToyProject3.util.BoardContentParseUtil;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public void savaBoard(BoardRequest.saveBoardDTO saveBoardDTO, User user) {
        BoardRequest.saveBoardDTO parseBoard = BoardContentParseUtil.parse(saveBoardDTO);
        Board board = parseBoard.toEntity(user);
        boardRepository.save(board);
    }

    public Page<BoardResponse.BoardListDTO> findAll(String boardType, String search, Integer page) {
        BoardType type = BoardType.valueOf(BoardType.class, boardType.toUpperCase());

        Page<Board> boardList = boardRepository.findAll(search, type, PageRequest.of(page, 6));

        return boardList.map(board -> BoardResponse.BoardListDTO.builder()
                .boardId(board.getId())
                .thumbnail(board.getThumbnail())
                .title(board.getTitle())
                .content(board.getContent())
                .nickName(board.getUser().getNickname())
                .build());
    }

    public BoardResponse.BoardDetailDTO getDetailBoard(Long boardId) {
        Optional<Board> boardOP = boardRepository.findById(boardId);

        return boardOP.map(board -> BoardResponse.BoardDetailDTO.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .nickName(board.getUser().getNickname())
                .build()).orElse(null);
    }

    public void deleteBoard(Long boardId, User user) {
        Optional<Board> board = boardRepository.findBoardByIdAndUserId(boardId, user.getId());
        if (board.isEmpty()) {
            // TODO throw
        }
        boardRepository.deleteById(boardId);
    }

    @Transactional
    public void updateBoard(BoardRequest.updateBoardDTO updateBoardDTO, Long boardId) {
        Board board = boardRepository.findById(boardId).get();
        board.updateBoard(updateBoardDTO);
    }

    public BoardResponse.BoardUpdateDTO updateBoardForm(Long boardId, User user) {
        Optional<Board> boardOP = boardRepository.findBoardByIdAndUserId(boardId, user.getId());

        return boardOP.map(board -> BoardResponse.BoardUpdateDTO.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .build()).orElse(null);
    }
}
