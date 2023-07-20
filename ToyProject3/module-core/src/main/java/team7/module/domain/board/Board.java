package team7.module.domain.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team7.module.domain.user.User;
import team7.module.dto.board.BoardRequest;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

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

    @Column(length = 300)
    private String thumbnailContent;

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