package team7.example.ToyProject3.domain.board;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum BoardStatus {
    ENABLED("활성화"),
    DISABLED("비활성화")
    ;

    private final String description;
}
