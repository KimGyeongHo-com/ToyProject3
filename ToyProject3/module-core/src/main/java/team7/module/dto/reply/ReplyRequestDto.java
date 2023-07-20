package team7.module.dto.reply;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import team7.module.domain.Reply;
import team7.module.domain.board.Board;
import team7.module.domain.user.User;

public class ReplyRequestDto {

	@Getter
	@Builder
	public static class ReplyDto {
		private Long boardId;

		@NotBlank(message = "댓글은 필수 입력입니다.")
		private String content;

		public Reply toEntity(Board board, User user) {
			return Reply.builder()
				.board(board)
				.user(user)
				.content(content)
				.build();
		}
	}

	@Getter
	@Builder
	public static class NestedReplyDto {
		private Long boardId;

		@NotBlank(message = "댓글은 필수 입력입니다.")
		private String content;

		private Long parentReplyId;

		public Reply toEntity(Board board, Reply parenReply, User user) {
			return Reply.builder()
				.content(content)
				.parentReply(parenReply)
				.board(board)
				.user(user)
				.build();
		}
	}
}


