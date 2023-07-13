package team7.example.ToyProject3.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team7.example.ToyProject3.domain.Board;
import team7.example.ToyProject3.domain.Reply;
import team7.example.ToyProject3.dto.reply.ReplyDto;
import team7.example.ToyProject3.repository.BoardRepository;
import team7.example.ToyProject3.repository.ReplyRepository;
import team7.example.ToyProject3.service.ReplyService;

@RequiredArgsConstructor
@Service
public class ReplyServiceImpl implements ReplyService {

	private final ReplyRepository replyRepository;
	private final BoardRepository boardRepository;

	@Override
	@Transactional
	public void addReply(ReplyDto.ReplyRequestDto replyRequestDto) {
		Board board = boardRepository.findById(replyRequestDto.getBoardId())
			.orElseThrow(() -> new IllegalArgumentException("게시글 id를 찾을 수 없습니다."));

		replyRepository.save(Reply.builder()
			.board(board)
			.content(replyRequestDto.getContent())
			.build());
	}

	@Override
	@Transactional
	public void deleteReply(Long replyId, Long boardId) {
		Reply reply = replyRepository.findByIdAndBoardId(replyId, boardId)
			.orElseThrow(() -> new IllegalArgumentException("게시글 id와 댓글 id를 찾을 수 없습니다."));
		replyRepository.delete(reply);
	}

	@Override
	@Transactional
	public Reply addNestedReplies(Long boardId, Long parentReplyId, String content) {
		Board board = boardRepository.findById(boardId)
			.orElseThrow(() -> new IllegalArgumentException("게시물 id를 찾을 수 없습니다."));

		Reply parentReply = replyRepository.findById(parentReplyId)
			.orElseThrow(() -> new IllegalArgumentException("부모 id를 찾을 수 없습니다."));

		Reply reply = Reply.builder()
			.board(board)
			.parentReply(parentReply)
			.content(content)
			.build();

		parentReply.getChildReplies().add(reply);

		return replyRepository.save(reply);
	}

	public void deleteNestedReplies(Long replyId) {
		Reply reply = replyRepository.findById(replyId)
			.orElseThrow(() -> new IllegalArgumentException("댓글 id을 찾을 수 없습니다."));

		Reply parentReply = reply.getParentReply();

		if (parentReply != null) {
			parentReply.getChildReplies().remove(reply);
		}

		replyRepository.delete(reply);
	}

	@Override
	public List<Reply> getChildReplies(Long boardId) {
		return replyRepository.findChildRepliesByBoardId(boardId);
	}

}

