package team7.example.ToyProject3.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team7.example.ToyProject3.util.ErrorScript;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> bindException(BindException e) {
        List<ObjectError> errors = e.getAllErrors();
        String response = ErrorScript.alertBuild(errors.get(0).getDefaultMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(BoardException.class)
    public ResponseEntity<?> boardException(BoardException e) {
        String response = ErrorScript.alertBuild(e.getErrorCode().getDescription());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(response);
    }
}
