package team7.example.ToyProject3.dto.board;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import team7.example.ToyProject3.domain.Board;
import team7.example.ToyProject3.domain.BoardType;
import team7.example.ToyProject3.domain.Member;

import javax.validation.constraints.NotEmpty;

public class BoardRequest {

    private static BoardType byMemberType(Member member) {
        return false ? BoardType.SPROUT : BoardType.GREAT;
    }


    @ToString
    @Setter
    @Getter
    public static class saveBoardDTO {

        @NotEmpty
        private String title;
        @NotEmpty
        private String content;
        private String thumbnail;

        public Board toEntity(Member member) {
            return Board.builder()
                    .content(content)
                    .title(title)
                    .thumbnail(thumbnail)
                    .boardType(byMemberType(member))
                    .member(member)
                    .build();
        }


    }

    @Setter
    @Getter
    public static class updateBoardDTO {
        @NotEmpty
        private String title;
        @NotEmpty
        private String content;
        private String thumbnail;

        public Board toEntity(Member member) {
            return Board.builder()
                    .content(content)
                    .title(title)
                    .thumbnail(thumbnail)
                    .boardType(byMemberType(member))
                    .member(member)
                    .build();
        }
    }
}
