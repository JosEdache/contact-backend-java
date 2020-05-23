package edache.joe.execption;

import org.springframework.http.HttpStatus;

public class AppException extends RuntimeException {
    String message;
    Integer code ;

    public AppException(String message, HttpStatus status) {
        this(message, status, null);
    }

    public  AppException(String message, HttpStatus status, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.code = status.value();
    }
}
