package com.project.joe.execption;

import com.project.joe.contact.ContactException;
import com.project.joe.contact.ContactValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RestControllerAdvice
public class ExceptionController {


    @ExceptionHandler
    public ResponseEntity<ErrorMessage> invalidContact(ContactException ex) {
        return error(HttpStatus.NOT_ACCEPTABLE, "Invalid contact", ex.getMessage());
    }

    @RequestMapping("/error")
    public ResponseEntity<ErrorMessage> containerError(HttpServletRequest request) {
        Integer code = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Object msg = request.getAttribute("javax.servlet.error.message");
        return error(
                HttpStatus.valueOf(code),
                "Error",
                msg
        );

    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(new ContactValidator());
    }

    public ResponseEntity<ErrorMessage> error(HttpStatus httpStatus, String error, Object message) {
        return ResponseEntity
                .status(httpStatus)
                .body(new ErrorMessage(httpStatus, error, message));
    }
}
