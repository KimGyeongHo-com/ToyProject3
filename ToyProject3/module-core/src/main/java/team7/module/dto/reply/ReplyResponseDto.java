package team7.module.dto.reply;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team7.module.domain.Reply;
import team7.module.domain.board.Board;
import team7.module.domain.user.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyResponseDto {
	private Long id;
	private String content;
	private Board board;
	private User user;
	private Long parentId;
	private List<ReplyResponseDto> children;
	private Timestamp createdAt;

	public static ReplyResponseDto fromReply(Reply reply) {
		return ReplyResponseDto.builder()
			.id(reply.getId())
			.content(reply.getContent())
			.board(reply.getBoard())
			.user(reply.getUser())
			.parentId(reply.getParentReply() != null ? reply.getParentReply().getId() : null)
			.children(new ArrayList<>())
			.createdAt(reply.getCreatedAt())
			.build();
	}
}