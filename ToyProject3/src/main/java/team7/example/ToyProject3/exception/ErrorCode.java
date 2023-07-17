package team7.example.ToyProject3.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	ENTITY_NOT_FOUND(HttpStatus.BAD_REQUEST, "요청하신 데이터가 없습니다.");

	final HttpStatus httpStatus;
	final String description;
}