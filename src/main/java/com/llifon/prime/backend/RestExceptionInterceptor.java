package com.llifon.prime.backend;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * A response exception handler which automatically intercepts some uncaught application exceptions in-order
 * to return a structured error message to the caller.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionInterceptor extends ResponseEntityExceptionHandler {

    // Malformed.
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new RestApiErrorResponse(HttpStatus.BAD_REQUEST, error, ex));
    }

    /**
     * Wraps the api error in a response.
     *
     * @param apiError The API error to wrap.
     * @return The
     */
    private ResponseEntity<Object> buildResponseEntity(RestApiErrorResponse apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    /**
     * Handles illegal argument exception issues.
     *
     * @param ex The exception.
     * @return A formatted response.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleEntityNotFound(
            IllegalArgumentException ex) {
        RestApiErrorResponse apiError = new RestApiErrorResponse(HttpStatus.BAD_REQUEST);
        apiError.setMessage(ex.getMessage());

        return buildResponseEntity(apiError);
    }
}
