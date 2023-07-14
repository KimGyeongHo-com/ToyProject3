package team7.example.ToyProject3.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team7.example.ToyProject3.domain.Board;
import team7.example.ToyProject3.domain.Reply;
import team7.example.ToyProject3.domain.User;
import team7.example.ToyProject3.dto.reply.ReplyRequestDto;
import team7.example.ToyProject3.dto.reply.ReplyResponseDto;
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
	public void addReply(ReplyRequestDto replyRequestDto, User user) {
		Board board = boardRepository.findById(replyRequestDto.getBoardId())
			.orElseThrow(() -> new IllegalArgumentException("게시글 id를 찾을 수 없습니다."));

		replyRepository.save(Reply.builder()
			.board(board)
			.user(user)
			.content(replyRequestDto.getContent())
			.build());
	}

	@Override
	@Transactional
	public void deleteReply(Long replyId, Long boardId, User user) {
		Reply reply = replyRepository.findByIdAndBoardIdAndUserId(replyId, boardId, user.getId())
			.orElseThrow(() -> new IllegalArgumentException("게시글 id와 댓글 id를 찾을 수 없습니다."));
		replyRepository.delete(reply);
	}

	@Override
	@Transactional
	public Reply addNestedReplies(Long boardId, Long parentReplyId, String content, User user) {
		Board board = boardRepository.findById(boardId)
			.orElseThrow(() -> new IllegalArgumentException("게시물 id를 찾을 수 없습니다."));

		Reply parentReply = null;

		if (parentReplyId != null) {
			parentReply = replyRepository.findById(parentReplyId)
				.orElseThrow(() -> new IllegalArgumentException("부모 id를 찾을 수 없습니다."));
		}

		Reply reply = Reply.builder()
			.board(board)
			.user(user)
			.parentReply(parentReply)
			.content(content)
			.build();

		if (parentReply != null) {
			parentReply.addChildReply(reply);
		}

		return replyRepository.save(reply);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ReplyResponseDto> getAllReplyByBoard(Long boardId) {
		Board board = boardRepository.findById(boardId)
			.orElseThrow(() -> new IllegalArgumentException("게시물 id를 찾을 수 없습니다."));

		List<Reply> replies = replyRepository.findAllByBoard(board);

		List<ReplyResponseDto> replyResponseDtoList = new ArrayList<>();
		Map<Long, ReplyResponseDto> replyResponseDtoMap = new HashMap<>();

		for (Reply reply : replies) {
			ReplyResponseDto replyResponseDto = ReplyResponseDto.builder()
				.id(reply.getId())
				.content(reply.getContent())
				.parentId(reply.getParentReply() != null ? reply.getParentReply().getId() : null)
				.children(new ArrayList<>())
				.build();

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
	public void deleteNestedReplies(Long boardId, Long replyId, User user) {
		Reply reply = replyRepository.findByIdAndBoardIdAndUserId(replyId, boardId, user.getId())
			.orElseThrow(() -> new IllegalArgumentException("게시글 id와 댓글 id를 찾을 수 없습니다."));

		Reply parentReply = reply.getParentReply();

		if (parentReply != null) {
			parentReply.getChildReplies().remove(reply);
		}

		replyRepository.delete(reply);
	}




}

