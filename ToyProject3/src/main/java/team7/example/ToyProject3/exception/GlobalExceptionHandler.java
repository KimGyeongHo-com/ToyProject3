package team7.example.ToyProject3.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import team7.example.ToyProject3.util.ErrorScript;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BindException.class)
	public ResponseEntity<?> bindException(BindException e) {
		List<ObjectError> errors = e.getAllErrors();
		String response = ErrorScript.alertBuild(errors.get(0).getDefaultMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(ReplyException.class)
	public ResponseEntity<?> replyException(ReplyException e) {
		String response = ErrorScript.alertBuild(e.getErrorCode().getDescription());
		return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(response);
	}
}