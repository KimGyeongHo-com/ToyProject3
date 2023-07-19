package team7.example.ToyProject3.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.example.ToyProject3.domain.Reply;
import team7.example.ToyProject3.domain.board.Board;
import team7.example.ToyProject3.domain.board.BoardStatus;
import team7.example.ToyProject3.domain.board.BoardType;
import team7.example.ToyProject3.domain.user.User;
import team7.example.ToyProject3.dto.board.BoardRequest;
import team7.example.ToyProject3.dto.board.BoardResponse;
import team7.example.ToyProject3.exception.BoardException;
import team7.example.ToyProject3.exception.ErrorCode;
import team7.example.ToyProject3.repository.BoardRepository;
import team7.example.ToyProject3.repository.ReplyRepository;
import team7.example.ToyProject3.repository.UserRepository;
import team7.example.ToyProject3.service.BoardService;
import team7.example.ToyProject3.util.BoardContentParseUtil;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final ReplyRepository replyRepository;

    @Override
    @Transactional
    public void savaBoard(BoardRequest.saveBoardDTO saveBoardDTO, User user) {
        BoardRequest.saveBoardDTO parseBoard = BoardContentParseUtil.parse(saveBoardDTO);
        Board board = parseBoard.toEntity(user);
        user.plusBoard();
        userRepository.save(user);
        boardRepository.save(board);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BoardResponse.BoardListDTO> findAll(String boardType, String search, Pageable pageable) {
        BoardType type = BoardType.valueOf(BoardType.class, boardType.toUpperCase());

        Page<Board> boardList = boardRepository.findAll(search, type, pageable, BoardStatus.ENABLED);

        return boardList.map(board -> BoardResponse.BoardListDTO.builder()
                .boardId(board.getId())
                .thumbnailContent(board.getThumbnailContent())
                .title(board.getTitle())
                .thumbnail(board.getThumbnail())
                .nickName(board.getUser().getNickname())
                .build());
    }

    @Override
    @Transactional(readOnly = true)
    public BoardResponse.BoardDetailDTO getDetailBoard(Long boardId) {
        Board board = validByBoardId(boardId);
        return BoardResponse.BoardDetailDTO.toDTO(board);
    }

    @Override
    @Transactional
    public void updateBoard(BoardRequest.updateBoardDTO updateBoardDTO, Long boardId) {
        Board board = validByBoardId(boardId);
        board.updateBoard(updateBoardDTO);
    }

    @Override
    @Transactional
    public void deleteBoard(Long boardId, User user) {
        Board board = validByBoardIdAndUserId(boardId, user);
        List<Reply> comments = replyRepository.findByBoardId(boardId);
        replyRepository.deleteAll(comments);
        boardRepository.deleteById(board.getId());
    }

    @Override
    @Transactional
    public BoardResponse.BoardUpdateDTO updateBoardForm(Long boardId, User user) {
        Board board = validByBoardIdAndUserId(boardId, user);
        return BoardResponse.BoardUpdateDTO.toDTO(board);
    }

    private Board validByBoardIdAndUserId(Long boardId, User user) {
        return boardRepository.findBoardByIdAndUserIdAndBoardStatus(boardId, user.getId(), BoardStatus.ENABLED)
                .orElseThrow(() -> new BoardException(ErrorCode.ERROR_BOARD_ENTITY_NOT_FOUND));
    }

    private Board validByBoardId(Long boardId) {
        return boardRepository.findBoardByIdAndBoardStatus(boardId, BoardStatus.ENABLED)
                .orElseThrow(() -> new BoardException(ErrorCode.ERROR_BOARD_ENTITY_NOT_FOUND));
    }

}
