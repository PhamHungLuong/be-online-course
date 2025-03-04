package be_course.be_online_course.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import be_course.be_online_course.DTO.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<Void>> handleBaseException(BaseException ex) {
        ApiResponse<Void> response = new ApiResponse<>(ex.getStatus().value(), ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(response);
    }
}
