package team7.example.ToyProject3.dto.reply;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;

	@Getter
	@Builder
	public class ReplyRequestDto {

		private Long boardId;

		@NotBlank(message = "댓글은 필수 입력입니다.")
		private String content;
	}


