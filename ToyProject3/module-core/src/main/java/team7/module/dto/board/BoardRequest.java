package team7.module.dto.board;

import lombok.Getter;
import lombok.Setter;
import team7.module.domain.board.Board;
import team7.module.domain.board.BoardStatus;
import team7.module.domain.board.BoardType;
import team7.module.domain.user.User;
import team7.module.domain.user.UserRole;


import javax.validation.constraints.NotBlank;

public class BoardRequest {

    private static BoardType byMemberType(User user) {
        if (user.getUserRole() == UserRole.NORMAL) {
            return BoardType.SPROUT;
        }
        if (user.getUserRole() == UserRole.VIP) {
            return BoardType.GREAT;
        }
        return BoardType.NONE;
    }

    @Getter
    @Setter
    public static class saveBoardDTO {
        @NotBlank(message = "제목이 공백일 수 없습니다")
        private String title;
        @NotBlank(message = "내용이 공백일 수 없습니다")
        private String content;
        private String thumbnail;
        private String thumbnailContent;

        public Board toEntity(User user) {
            return Board.builder()
                    .content(content)
                    .title(title)
                    .thumbnail(thumbnail)
                    .thumbnailContent(thumbnailContent)
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
