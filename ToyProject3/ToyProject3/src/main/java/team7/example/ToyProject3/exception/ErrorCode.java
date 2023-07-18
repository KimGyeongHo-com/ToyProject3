package team7.example.ToyProject3.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    ERROR_BIND_EXCEPTION(HttpStatus.BAD_REQUEST, ""),

    ;

    final HttpStatus httpStatus;
    final String description;
}