package team7.example.ToyProject3.service;

import java.util.List;
import team7.example.ToyProject3.domain.Reply;
import team7.example.ToyProject3.domain.User;
import team7.example.ToyProject3.dto.reply.ReplyRequestDto;
import team7.example.ToyProject3.dto.reply.ReplyResponseDto;

public interface ReplyService {

	void addReply(ReplyRequestDto.ReplyDto saveReplyDto, User user);
	void deleteReply(Long boardId, Long replyId, User user);

	Reply addNestedReply(ReplyRequestDto.NestedReplyDto saveNestedReplyDto, User user);

	void deleteNestedReply(Long boardId, Long replyId,  User user);

	List<ReplyResponseDto> getAllReplyByBoard(Long boardId);
}

