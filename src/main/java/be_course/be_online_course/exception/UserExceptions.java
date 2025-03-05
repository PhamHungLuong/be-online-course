package be_course.be_online_course.exception;

import org.springframework.http.HttpStatus;

public class UserExceptions {
    public static class UserNotFoundException extends BaseException {
        public UserNotFoundException(String message) {
            super(message, HttpStatus.NOT_FOUND);
        }
    }

    public static class UserOrEmailAlreadyExistsException extends BaseException {
        public UserOrEmailAlreadyExistsException(String message) {
            super(message, HttpStatus.CONFLICT);
        }
    }

    public static class CheckValidPassword extends BaseException {
        public CheckValidPassword(String message) {
            super(message, HttpStatus.UNAUTHORIZED);
        }
    }
}
