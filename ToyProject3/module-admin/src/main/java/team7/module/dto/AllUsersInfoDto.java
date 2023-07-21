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
public class AllUsersInfoDto {
    private Integer id;
    private String name;
    private String nickname;
    private String email;
    @Enumerated(value = EnumType.STRING)
    private UserRole userrole;
    private Integer qtyOfBoard;
    private Integer qtyOfReply;
}
