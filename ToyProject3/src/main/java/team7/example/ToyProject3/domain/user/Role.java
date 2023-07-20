package team7.example.ToyProject3.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    BLACKLIST("ROLE_BLACKLIST");

    private String value;
}