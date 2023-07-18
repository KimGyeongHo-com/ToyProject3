package team7.example.ToyProject3.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team7.example.ToyProject3.domain.Reply;
import team7.example.ToyProject3.domain.board.Board;
import team7.example.ToyProject3.domain.user.User;
import team7.example.ToyProject3.dto.reply.ReplyRequestDto;
import team7.example.ToyProject3.dto.reply.ReplyResponseDto;
import team7.example.ToyProject3.exception.ErrorCode;
import team7.example.ToyProject3.exception.ReplyException;
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
	public void addReply(ReplyRequestDto.ReplyDto saveReplyDto, User user) {
		Board board = boardRepository.findById(saveReplyDto.getBoardId())
			.orElseThrow(() -> new ReplyException(ErrorCode.ENTITY_NOT_FOUND));

		Reply reply = saveReplyDto.toEntity(board, user);

		replyRepository.save(reply);
	}

	@Override
	@Transactional
	public void deleteReply(Long replyId, Long boardId, User user) {
		Reply reply = replyRepository.findByIdAndBoardIdAndUserId(replyId, boardId, user.getId())
			.orElseThrow(() -> new ReplyException(ErrorCode.ENTITY_NOT_FOUND));
		replyRepository.delete(reply);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ReplyResponseDto> getAllReplyByBoard(Long boardId) {

		List<Reply> sortedBoardReplies = replyRepository.findAllByBoardIdOrderByParentAndCreatedAt(boardId);

		List<ReplyResponseDto> replyResponseDtoList = new ArrayList<>();
		Map<Long, ReplyResponseDto> replyResponseDtoMap = new HashMap<>();

		for (Reply reply : sortedBoardReplies) {
			ReplyResponseDto replyResponseDto = ReplyResponseDto.fromReply(reply);

			replyResponseDtoMap.put(replyResponseDto.getId(), replyResponseDto);

			if (reply.getParentReply() != null) {
				replyResponseDtoMap.get(reply.getParentReply().getId()).getChildren().add(replyResponseDto);
			} else {
				replyResponseDtoList.add(replyResponseDto);
			}
		}
		return replyResponseDtoList;
	}

	@Override
	@Transactional
	public Reply addNestedReply(ReplyRequestDto.NestedReplyDto saveNestedReplyDto, User user) {
		Board board = boardRepository.findById(saveNestedReplyDto.getBoardId())
			.orElseThrow(() -> new ReplyException(ErrorCode.ENTITY_NOT_FOUND));

		Reply parentReply = null;

		if (saveNestedReplyDto.getParentReplyId() != null) {
			parentReply = replyRepository.findById(saveNestedReplyDto.getParentReplyId())
				.orElseThrow(() -> new ReplyException(ErrorCode.ENTITY_NOT_FOUND));
		}

		Reply reply = saveNestedReplyDto.toEntity(board, parentReply, user);

		if (parentReply != null) {
			parentReply.addChildReply(reply);
		}

		return replyRepository.save(reply);
	}

	@Override
	@Transactional
	public void deleteNestedReply(Long boardId, Long replyId, User user) {
		Reply reply = replyRepository.findByIdAndBoardIdAndUserId(replyId, boardId, user.getId())
			.orElseThrow(() -> new ReplyException(ErrorCode.ENTITY_NOT_FOUND));

		Reply parentReply = reply.getParentReply();

		if (parentReply != null) {
			parentReply.getChildReplies().remove(reply);
		}
		replyRepository.delete(reply);
	}
}

