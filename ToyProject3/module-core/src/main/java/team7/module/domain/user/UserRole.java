package team7.module.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
    NORMAL("NORMAL"),
    VIP("VIP"),
    BLACK("BLACK");

    private String value;
}
