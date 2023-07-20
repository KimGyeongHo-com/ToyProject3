package team7.module.dto;

import lombok.*;
import team7.module.domain.user.Role;
import team7.module.domain.user.User;
import team7.module.domain.user.UserRole;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;


@Data
@ToString
public class UserDto {
    private Long id;


    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{8,20}",
            message = "영문과 숫자가 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;

    private Role role;

    private UserRole userRole;


    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    private String nickname;



    private Timestamp createdAt;

    private Timestamp updatedAt;


    public User toEntity(UserDto userDto){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        User user = new User();
        user.setName(userDto.getName());
        user.setNickname(userDto.getNickname());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setCreatedAt(timestamp);
        user.setUpdatedAt(timestamp);
        user.setRole(userDto.getRole());
        user.setUserRole(UserRole.NORMAL);

        return user;
    }


}
