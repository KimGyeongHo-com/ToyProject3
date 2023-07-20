package team7.module.exception;

import lombok.Getter;

@Getter
public class BoardException extends RuntimeException {

    private final ErrorCode errorCode;

    public BoardException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
