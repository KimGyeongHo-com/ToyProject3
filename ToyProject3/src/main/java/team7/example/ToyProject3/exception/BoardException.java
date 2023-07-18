package team7.example.ToyProject3.exception;

import lombok.Getter;

@Getter
public class BoardException extends RuntimeException {

    private final ErrorCode errorCode;

    public BoardException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
