package team7.example.ToyProject3.domain;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "user")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false, name = "name")
    private String name;

    @Column(length = 100, nullable = false, name = "password")
    private String password;

    @Column(length = 30, nullable = false, name = "email")
    private String email;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Enumerated(value = EnumType.STRING)
    private UserRole userrole;

    private String nickname;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public void setAdmain(String email){
        if(this.email == email)
            role = Role.ADMIN;
        else
            role = Role.USER;
    }


}