package team7.example.ToyProject3.dto.board;

import lombok.Getter;
import lombok.Setter;
import team7.example.ToyProject3.domain.Board;
import team7.example.ToyProject3.domain.BoardType;
import team7.example.ToyProject3.domain.User;

import javax.validation.constraints.NotEmpty;

public class BoardRequest {

    private static BoardType byMemberType(User user) {
        return false ? BoardType.SPROUT : BoardType.GREAT;
    }

    @Getter
    @Setter
    public static class saveBoardDTO {
        @NotEmpty
        private String title;
        @NotEmpty
        private String content;
        private String thumbnail;

        public Board toEntity(User user) {
            return Board.builder()
                    .content(content)
                    .title(title)
                    .thumbnail(thumbnail)
                    .boardType(byMemberType(user))
                    .user(user)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class updateBoardDTO {
        @NotEmpty
        private String title;
        @NotEmpty
        private String content;
        private String thumbnail;

        public Board toEntity(User user) {
            return Board.builder()
                    .content(content)
                    .title(title)
                    .thumbnail(thumbnail)
                    .boardType(byMemberType(user))
                    .user(user)
                    .build();
        }
    }
}
