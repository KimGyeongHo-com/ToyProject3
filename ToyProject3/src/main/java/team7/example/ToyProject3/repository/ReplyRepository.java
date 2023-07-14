package team7.example.ToyProject3.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import team7.example.ToyProject3.domain.Board;
import team7.example.ToyProject3.domain.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

	Optional<Reply> findById(Long id);
	Optional<Reply> findByIdAndBoardIdAndUserId(Long ReplyId, Long boardId, Long userId);
	List<Reply> findAllByBoard(Board board);


}
