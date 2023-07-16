package team7.example.ToyProject3.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AdminBoardDto {
    private Long id;
    private String nickname;
    private String title;
    private Timestamp createdAt;
}
