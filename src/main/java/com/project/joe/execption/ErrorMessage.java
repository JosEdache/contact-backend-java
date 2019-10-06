package com.project.joe.execption;

import org.springframework.http.HttpStatus;

@lombok.Value
class ErrorMessage {

    HttpStatus status;
    Integer code;
    Object error, message;

    ErrorMessage(HttpStatus status, Object error, Object message) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.code = status.value();
    }
}
