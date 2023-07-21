package team7.module.dto;

import lombok.*;
import team7.module.domain.user.UserRole;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AdminReportDto {
    private Integer id;
    private String title;
    private Integer userId;
    private String nickname;
    private String description;
    private String filePath;
    @Enumerated(value = EnumType.STRING)
    private UserRole userrole;
}
