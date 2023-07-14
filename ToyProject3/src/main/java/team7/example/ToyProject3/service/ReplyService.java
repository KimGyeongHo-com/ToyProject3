package team7.example.ToyProject3.service;

import java.util.List;
import team7.example.ToyProject3.domain.Reply;
import team7.example.ToyProject3.domain.User;
import team7.example.ToyProject3.dto.reply.ReplyRequestDto;
import team7.example.ToyProject3.dto.reply.ReplyResponseDto;

public interface ReplyService {

	void addReply(ReplyRequestDto replyRequestDto, User user);
	void deleteReply(Long boardId, Long replyId, User user);

	Reply addNestedReplies(Long boardId, Long parentReplyId, String content, User user);

	void deleteNestedReplies(Long boardId, Long replyId, User user);

	List<ReplyResponseDto> getAllReplyByBoard(Long boardId);
}

