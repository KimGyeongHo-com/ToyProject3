package team7.example.ToyProject3.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import team7.example.ToyProject3.dto.reply.ReplyDto;
import team7.example.ToyProject3.service.ReplyService;

@RequestMapping("/board")
@RestController
@RequiredArgsConstructor
public class ReplyRestController {

	private final ReplyService replyService;
	@PostMapping("/{boardId}/reply")
	public ResponseEntity<?> addReply(@RequestBody ReplyDto.ReplyRequestDto replyRequestDto) {
		replyService.addReply(replyRequestDto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@DeleteMapping("/{boardId}/reply/{replyId}")
	public ResponseEntity<?> deleteReply(@PathVariable Long boardId, @PathVariable Long replyId) {
		replyService.deleteReply(replyId, boardId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}


	@PostMapping("/{parentReplyId}")
	public ResponseEntity<?> addNestedReplies (
		@PathVariable Long boardId,
		@PathVariable Long parentReplyId,
		@RequestBody ReplyDto.ReplyRequestDto replyRequestDto) {
		replyService.addNestedReplies(boardId, parentReplyId, replyRequestDto.getContent());
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@DeleteMapping("/{replyId}")
	public ResponseEntity<?> deleteNestedReplies(
		@PathVariable Long boardId,
		@PathVariable Long replyId) {
		replyService.deleteNestedReplies(replyId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}



}
