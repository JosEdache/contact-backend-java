package edache.joe.execption;

import edache.joe.contact.ContactException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestController
@RestControllerAdvice
public class ExceptionController {

    public static final String ERROR_URL = "/error";

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> invalidContact(ContactException ex) {
        return error(HttpStatus.NOT_ACCEPTABLE, "Invalid contact", ex.getMessage());
    }

    @RequestMapping(ERROR_URL)
    public ResponseEntity<ErrorMessage> containerError(HttpServletRequest request) {
        Integer code = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Object msg = request.getAttribute("javax.servlet.error.message");
        return error(
                HttpStatus.valueOf(code),
                "Error",
                msg
        );

    }

    public ResponseEntity<ErrorMessage> error(HttpStatus httpStatus, String error, Object message) {
        return ResponseEntity
                .status(httpStatus)
                .body(new ErrorMessage(httpStatus, error, message));
    }
}
