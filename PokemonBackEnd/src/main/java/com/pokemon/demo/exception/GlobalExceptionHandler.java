package com.pokemon.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    /* Catches when a game listing is not found */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException e, WebRequest request
    ) {
        ApiErrorResponse response = new ApiErrorResponse("Not found", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /* Catches a bug if we somehow pass a bad argument to the GameController method class */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgumentException (
            IllegalArgumentException e, WebRequest request
    ) {
        ApiErrorResponse response = new ApiErrorResponse("Bad Request", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /* If Game is not found need to throw exception  */
    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> gameNotFound(GameNotFoundException exception) {

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse("Not Found", exception.getMessage());
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.NOT_FOUND);

    }

}
