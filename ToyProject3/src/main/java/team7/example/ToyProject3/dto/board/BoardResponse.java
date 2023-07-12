package team7.example.ToyProject3.dto.board;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class BoardResponse {

    @Getter
    @Builder
    @ToString
    public static class BoardListDTO {
        private Long boardId;
        private String title;
        private String content;
        private String nickName;
        private String thumbnail;
    }

    @Getter
    @Builder
    public static class BoardDetailDTO {
        private Long boardId;
        private String title;
        private String content;
        private String nickName;
        // TODO 댓글 추가 필요
    }

    @Getter
    @Builder
    public static class BoardUpdateDTO {
        private Long boardId;
        private String title;
        private String content;
    }
}
