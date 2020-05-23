package edache.joe.execption;

import edache.joe.execption.AppException;
import org.springframework.http.HttpStatus;

public class AuthenticationException extends AppException {
    static final String MESSAGE = "INVALID CREDENTIALS";
    public AuthenticationException() {
        super(MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
