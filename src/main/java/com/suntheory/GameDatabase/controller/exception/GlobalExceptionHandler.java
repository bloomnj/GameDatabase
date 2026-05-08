package com.suntheory.GameDatabase.controller.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.suntheory.GameDatabase.util.GameRepositoryErrors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
  @Override
  protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    StringBuilder builder = new StringBuilder();

    builder.append(ex.getMethod());
    builder.append(GameRepositoryErrors.METHOD_NOT_SUPPORTED);

    ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

    ApiError apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED, ex.getLocalizedMessage(), builder.toString());

    return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), GameRepositoryErrors.HTTP_NOT_READABLE);

    return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
  }

  @ExceptionHandler({ResponseStatusException.class})
  public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex, WebRequest request) {
    ApiError apiError = new ApiError(ex.getStatusCode(), ex.getReason(), GameRepositoryErrors.INVALID_REQUEST_BODY);

    return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
  }
}
