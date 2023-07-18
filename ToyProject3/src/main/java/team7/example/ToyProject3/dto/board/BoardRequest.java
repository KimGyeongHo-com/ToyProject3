package team7.example.ToyProject3.dto.board;

import lombok.Getter;
import lombok.Setter;
import team7.example.ToyProject3.domain.User;
import team7.example.ToyProject3.domain.board.Board;
import team7.example.ToyProject3.domain.board.BoardStatus;
import team7.example.ToyProject3.domain.board.BoardType;

import javax.validation.constraints.NotBlank;

public class BoardRequest {

    // TODO user type 을 통해 sprout , great 예정
    private static BoardType byMemberType(User user) {
        return false ? BoardType.SPROUT : BoardType.GREAT;
    }

    @Getter
    @Setter
    public static class saveBoardDTO {
        @NotBlank(message = "제목이 공백일 수 없습니다")
        private String title;
        @NotBlank(message = "내용이 공백일 수 없습니다")
        private String content;
        private String thumbnail;

        public Board toEntity(User user) {
            return Board.builder()
                    .content(content)
                    .title(title)
                    .thumbnail(thumbnail)
                    .boardType(byMemberType(user))
                    .user(user)
                    .boardStatus(BoardStatus.ENABLED)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class updateBoardDTO {
        @NotBlank(message = "제목이 공백일 수 없습니다")
        private String title;
        @NotBlank(message = "내용이 공백일 수 없습니다")
        private String content;
        private String thumbnail;
    }
}
