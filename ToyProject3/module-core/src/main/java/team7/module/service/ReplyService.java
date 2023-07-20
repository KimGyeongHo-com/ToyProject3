package team7.module.service;


import team7.module.domain.Reply;
import team7.module.domain.user.User;
import team7.module.dto.reply.ReplyRequestDto;
import team7.module.dto.reply.ReplyResponseDto;

import java.util.List;

public interface ReplyService {

	void addReply(ReplyRequestDto.ReplyDto saveReplyDto, User user);
	void deleteReply(Long boardId, Long replyId, User user);

	Reply addNestedReply(ReplyRequestDto.NestedReplyDto saveNestedReplyDto, User user);

	void deleteNestedReply(Long boardId, Long replyId,  User user);

	List<ReplyResponseDto> getAllReplyByBoard(Long boardId);
}

