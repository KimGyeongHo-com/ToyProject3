package team7.example.ToyProject3.dto;

import lombok.*;
import team7.example.ToyProject3.domain.Role;
import team7.example.ToyProject3.domain.User;
import java.sql.Timestamp;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String password;
    private Role role;
    private String nickname;
    private String email;

    private Timestamp createdAt;

    private Timestamp updatedAt;


    public User toEntity(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        User user = new User();
        user.setName(name);
        user.setNickname(nickname);
        user.setPassword(password);
        user.setEmail(email);
        user.setCreatedAt(timestamp);
        user.setUpdatedAt(timestamp);

        if(email == "admin@example.com")
            user.setRole(Role.ADMIN);
        else
            user.setRole(Role.USER);


        return user;
    }


}
