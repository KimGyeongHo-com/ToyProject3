package team7.example.ToyProject3.dto.board;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import team7.example.ToyProject3.domain.board.Board;
import team7.example.ToyProject3.domain.board.BoardStatus;

public class BoardResponse {

    @Getter
    @Builder
    @ToString
    public static class BoardListDTO {
        private Long boardId;
        private String title;
        private String nickName;
        private String thumbnail;
        private String thumbnailContent;
        private BoardStatus boardStatus;
    }

    @Getter
    @Builder
    public static class BoardDetailDTO {
        private Long boardId;
        private String title;
        private String content;
        private String nickName;
        private String userEmail;
        // TODO 댓글 추가 필요

        public static BoardDetailDTO toDTO(Board board) {
            return BoardDetailDTO.builder()
                    .boardId(board.getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .nickName(board.getUser().getNickname())
                    .userEmail(board.getUser().getEmail())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class BoardUpdateDTO {
        private Long boardId;
        private String title;
        private String content;

        public static BoardUpdateDTO toDTO(Board board) {
            return BoardUpdateDTO.builder()
                    .boardId(board.getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .build();
        }
    }
}
