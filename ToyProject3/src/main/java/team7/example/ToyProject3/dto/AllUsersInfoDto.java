package team7.example.ToyProject3.dto;

import lombok.*;
import team7.example.ToyProject3.domain.Role;

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
    private Role role;
    private Integer qtyOfBoard;
}
