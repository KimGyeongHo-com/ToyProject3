package team7.module.exception;
import lombok.Getter;

@Getter
public class ReplyException extends RuntimeException {

	private final ErrorCode errorCode;

	public ReplyException (ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}