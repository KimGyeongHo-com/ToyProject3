package team7.example.ToyProject3.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team7.example.ToyProject3.domain.Board;
import team7.example.ToyProject3.domain.BoardType;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("select b from Board b where b.title like %:search% and b.boardType = :boardType order by b.id desc")
    Page<Board> findAll(String search, BoardType boardType, Pageable pageable);

    Optional<Board> findBoardByIdAndUserId(Long BoardId, Long UserId);
}