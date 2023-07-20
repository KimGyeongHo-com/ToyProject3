package team7.module.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team7.module.domain.board.Board;
import team7.module.domain.board.BoardStatus;
import team7.module.domain.board.BoardType;

import java.util.Optional;


public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT b" +
            " FROM Board b" +
            " JOIN b.user u" +
            " WHERE b.title" +
            " LIKE %:search%" +
            " AND b.boardType = :boardType" +
            " AND b.boardStatus = :status")
    Page<Board> findAll(String search, BoardType boardType, Pageable pageable, BoardStatus status);

    Optional<Board> findBoardByIdAndUserId(Long BoardId, Long UserId);

    Optional<Board> findBoardByIdAndBoardStatus(Long BoardId, BoardStatus status);

    Optional<Board> findBoardByIdAndUserIdAndBoardStatus(Long boardId, Long id, BoardStatus boardStatus);
}
