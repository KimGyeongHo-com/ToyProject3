package team7.example.ToyProject3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.example.ToyProject3.domain.User;
import team7.example.ToyProject3.domain.board.Board;
import team7.example.ToyProject3.domain.board.BoardStatus;
import team7.example.ToyProject3.domain.board.BoardType;
import team7.example.ToyProject3.dto.board.BoardRequest;
import team7.example.ToyProject3.dto.board.BoardResponse;
import team7.example.ToyProject3.exception.BoardException;
import team7.example.ToyProject3.exception.ErrorCode;
import team7.example.ToyProject3.repository.BoardRepository;
import team7.example.ToyProject3.util.BoardContentParseUtil;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    // TODO 왜 예외 처리를 안해도 되는지 체크
    //  1. user 의 정보는 security 를 통해 확실 user 를 가져온다.
    //  2. savaBoardDTO 는 valid 체크를 한다.
    @Transactional
    public void savaBoard(BoardRequest.saveBoardDTO saveBoardDTO, User user) {
        BoardRequest.saveBoardDTO parseBoard = BoardContentParseUtil.parse(saveBoardDTO);
        Board board = parseBoard.toEntity(user);
        boardRepository.save(board);
    }

    // TODO 여기는 확인 해볼 것
    @Transactional(readOnly = true)
    public Page<BoardResponse.BoardListDTO> findAll(String boardType, String search, Pageable pageable) {
        BoardType type = BoardType.valueOf(BoardType.class, boardType.toUpperCase());

        Page<Board> boardList = boardRepository.findAll(search, type, pageable, BoardStatus.ENABLED);

        return boardList.map(board -> BoardResponse.BoardListDTO.builder()
                .boardId(board.getId())
                .thumbnail(board.getThumbnail())
                .title(board.getTitle())
                .content(board.getContent())
                .nickName(board.getUser().getNickname())
                .build());
    }

    @Transactional(readOnly = true)
    public BoardResponse.BoardDetailDTO getDetailBoard(Long boardId) {
        Board board = boardRepository.findBoardByIdAndBoardStatus(boardId, BoardStatus.ENABLED)
                .orElseThrow(() -> new BoardException(ErrorCode.ERROR_BOARD_ENTITY_NOT_FOUND));

        return BoardResponse.BoardDetailDTO.toDTO(board);
    }

    // TODO boardId 는 있지 않는 값을 가져올 수 있다.
    //  1. 컨트롤러 확인?
    //  2. user 는 믿을 수 있지만 user 와 boardId 가 일치해야한다!
    //  3. fetch join 을 통해 가져와서 isEmpty 로 확인해보자
    @Transactional
    public void deleteBoard(Long boardId, User user) {
        Board board = boardRepository.findBoardByIdAndUserId(boardId, user.getId())
                .orElseThrow(() -> new BoardException(ErrorCode.ERROR_BOARD_ENTITY_NOT_FOUND));
        boardRepository.deleteById(board.getId());
    }

    // TODO updateBoard 에서 boardId 를 확인한다
    @Transactional
    public void updateBoard(BoardRequest.updateBoardDTO updateBoardDTO, Long boardId) {
        Board board = boardRepository.findBoardByIdAndBoardStatus(boardId, BoardStatus.ENABLED)
                .orElseThrow(() -> new BoardException(ErrorCode.ERROR_BOARD_ENTITY_NOT_FOUND));
        board.updateBoard(updateBoardDTO);
    }

    // TODO updateBoardForm 을 가져올 때 검사를 할 필요가 굳이 있는 것인가?
    @Transactional
    public BoardResponse.BoardUpdateDTO updateBoardForm(Long boardId, User user) {
        Board board = boardRepository.findBoardByIdAndUserId(boardId, user.getId())
                .orElseThrow(() -> new BoardException(ErrorCode.ERROR_BOARD_ENTITY_NOT_FOUND));

        return BoardResponse.BoardUpdateDTO.toDTO(board);
    }

}
