package team7.example.ToyProject3.service;

import java.util.List;

import org.springframework.stereotype.Service;

import team7.example.ToyProject3.domain.Reply;
import team7.example.ToyProject3.dto.reply.ReplyDto;

public interface ReplyService {

	void addReply(ReplyDto.ReplyRequestDto replyRequestDto);
	void deleteReply(Long boardId, Long replyId);

	Reply addNestedReplies(Long boardId, Long parentReplyId, String content);

	void deleteNestedReplies(Long replyId);

	List<Reply> getChildReplies(Long boardId);

}

