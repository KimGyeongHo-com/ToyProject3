package team7.module.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team7.module.domain.Reply;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

	Optional<Reply> findById(Long id);
	Optional<Reply> findByIdAndBoardIdAndUserId(Long ReplyId, Long boardId, Long userId);

	@Query("SELECT r FROM Reply r LEFT JOIN r.parentReply parentReply WHERE r.board.id = :boardId ORDER BY CASE WHEN parentReply IS NULL THEN r.id ELSE parentReply.id END DESC, r.createdAt ASC")
	List<Reply> findAllByBoardIdOrderByParentAndCreatedAt(Long boardId);

	List<Reply> findByBoardId(Long boardId);


}
