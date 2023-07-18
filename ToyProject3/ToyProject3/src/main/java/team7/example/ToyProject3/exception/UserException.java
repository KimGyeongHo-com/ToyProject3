package team7.example.ToyProject3.exception;


import lombok.Getter;


@Getter
public class UserException extends RuntimeException {

    private final ErrorCode errorCode;

    public UserException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}