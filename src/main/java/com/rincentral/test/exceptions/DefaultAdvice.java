package com.rincentral.test.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultAdvice {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<String> handleBindException(BindException bindException) {
        return ResponseEntity
                .status(400)
                .body("Wrong given parameters");
    }

    @ExceptionHandler(RequestParametersException.class)
    public ResponseEntity<String> handleRequestParametersException(RequestParametersException requestParametersException) {
        return ResponseEntity
                .status(400)
                .body(requestParametersException.getMessage());
    }
}
