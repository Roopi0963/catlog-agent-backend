package com.krish.voicecatlogagent.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
//
//        // Check if it's our specific email error
//        if ("Email already in use".equals(ex.getMessage())) {
//            // Return HTTP 409 (Conflict) for duplicates
//            Map<String, String> response = new HashMap<>();
//            response.put("error", "Email already in use");
//            response.put("message", "The email address you entered is already registered.");
//            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
//        }
//
//        // Handle other random runtime errors
//        Map<String, String> response = new HashMap<>();
//        response.put("error", "Internal Server Error");
//        response.put("message", ex.getMessage());
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
}