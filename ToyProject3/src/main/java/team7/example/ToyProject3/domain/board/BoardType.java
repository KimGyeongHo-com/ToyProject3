package team7.example.ToyProject3.domain.board;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum BoardType {
    SPROUT("새싹게시글"),
    GREAT("우수게시글"),
    NONE("게시글")
    ;

    private final String description;
}
