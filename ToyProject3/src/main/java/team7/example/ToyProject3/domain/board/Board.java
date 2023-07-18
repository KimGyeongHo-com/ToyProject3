package team7.example.ToyProject3.domain.board;

import lombok.*;
import team7.example.ToyProject3.domain.user.User;
import team7.example.ToyProject3.dto.board.BoardRequest;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    private String content;

    @Lob
    private String thumbnail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BoardType boardType;

    @Enumerated(EnumType.STRING)
    private BoardStatus boardStatus;

    private Timestamp createdAt;
    private Timestamp updateAt;

    @PrePersist
    private void onCreate() {
        this.createdAt = Timestamp.valueOf(LocalDateTime.now());
    }

    @PreUpdate
    private void onUpdate() {
        this.updateAt = Timestamp.valueOf(LocalDateTime.now());
    }

    public void updateBoard(BoardRequest.updateBoardDTO boardUpdateDTO) {
        this.title = boardUpdateDTO.getTitle();
        this.content = boardUpdateDTO.getContent();
    }

}