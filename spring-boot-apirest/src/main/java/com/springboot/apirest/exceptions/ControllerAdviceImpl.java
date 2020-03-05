package com.springboot.apirest.exceptions;

import com.springboot.apirest.dto.Message;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Order(HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ControllerAdviceImpl {

    @ExceptionHandler({BillNotFoundException.class, ClientNotFoundException.class})
    public ResponseEntity handleNotFound(Exception e){

        return buildResponse(NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(ObjectAlreadyExistsException.class)
    public ResponseEntity handleConflict(Exception e){

        return buildResponse(CONFLICT, e.getMessage());
    }

    private ResponseEntity<Object> buildResponse(HttpStatus status, String payload) {

        return ResponseEntity.status(status).body(buildMessage(payload));
    }

    private Message buildMessage(String message) {

        return Message.builder().message(message).build();
    }
}
