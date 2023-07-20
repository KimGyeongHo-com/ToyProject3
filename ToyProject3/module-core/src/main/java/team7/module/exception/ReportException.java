package team7.module.exception;

import lombok.Getter;

@Getter
public class ReportException extends RuntimeException {

    private final ErrorCode errorCode;

    public ReportException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
