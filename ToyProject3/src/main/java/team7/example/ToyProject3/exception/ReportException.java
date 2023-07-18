package team7.example.ToyProject3.exception;

import lombok.Getter;

@Getter
public class ReportException extends RuntimeException {

    private final ErrorCode errorCode;

    public ReportException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
