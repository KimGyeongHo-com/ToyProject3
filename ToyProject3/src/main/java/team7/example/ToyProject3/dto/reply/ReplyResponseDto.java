package team7.example.ToyProject3.dto.reply;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyResponseDto {
	private Long id;
	private String content;
	private Long parentId;
	private List<ReplyResponseDto> children;
}