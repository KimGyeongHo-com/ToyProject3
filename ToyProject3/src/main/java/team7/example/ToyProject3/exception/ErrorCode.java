package team7.example.ToyProject3.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    ERROR_BIND_EXCEPTION(HttpStatus.BAD_REQUEST, ""),
    ERROR_DUPLICATED_EMAIL_EXCEPTION(HttpStatus.BAD_REQUEST,"이미 존재하는 이메일 입니다."),
    ERROR_DUPLICATED_NICKNAME_EXCEPTION(HttpStatus.BAD_REQUEST,"이미 존재하는 닉네임 입니다."),

    ;

    final HttpStatus httpStatus;
    final String description;
}