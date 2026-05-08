package com.suntheory.GameDatabase.controller.exception;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import com.suntheory.GameDatabase.util.GameRepositoryErrors;

class GlobalExceptionHandlerTest {

    private final TestableGlobalExceptionHandler handler = new TestableGlobalExceptionHandler();

    @Test
    void handleResponseStatusException_returnsApiErrorBody() {
        ResponseStatusException exception = new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                GameRepositoryErrors.GAME_NOT_FOUND);

        ResponseEntity<Object> response = handler.handleResponseStatusException(exception, null);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isInstanceOf(ApiError.class);

        ApiError apiError = (ApiError) response.getBody();
        assertThat(apiError.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(apiError.getMessage()).isEqualTo(GameRepositoryErrors.GAME_NOT_FOUND);
        assertThat(apiError.getErrors()).containsExactly(GameRepositoryErrors.INVALID_REQUEST_BODY);
    }

    @Test
    void handleHttpRequestMethodNotSupported_returnsMethodNotAllowedApiError() {
        HttpRequestMethodNotSupportedException exception =
                new HttpRequestMethodNotSupportedException("PATCH", List.of("GET", "POST"));

        ResponseEntity<Object> response = handler.handleMethodNotSupported(
                exception,
                new HttpHeaders(),
                HttpStatus.METHOD_NOT_ALLOWED,
                null);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
        assertThat(response.getBody()).isInstanceOf(ApiError.class);

        ApiError apiError = (ApiError) response.getBody();
        assertThat(apiError.getStatus()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
        assertThat(apiError.getMessage()).contains("PATCH");
        assertThat(apiError.getErrors())
                .singleElement()
                .satisfies(error -> {
                    assertThat(error).startsWith("PATCH" + GameRepositoryErrors.METHOD_NOT_SUPPORTED);
                    assertThat(error).contains("GET");
                    assertThat(error).contains("POST");
                });
    }

    @Test
    void handleHttpMessageNotReadable_returnsBadRequestApiError() {
        HttpMessageNotReadableException exception =
                new HttpMessageNotReadableException("Malformed JSON", new EmptyHttpInputMessage());

        ResponseEntity<Object> response = handler.handleMessageNotReadable(
                exception,
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                null);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isInstanceOf(ApiError.class);

        ApiError apiError = (ApiError) response.getBody();
        assertThat(apiError.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(apiError.getMessage()).contains("Malformed JSON");
        assertThat(apiError.getErrors()).containsExactly(GameRepositoryErrors.HTTP_NOT_READABLE);
    }

    private static class TestableGlobalExceptionHandler extends GlobalExceptionHandler {
        ResponseEntity<Object> handleMethodNotSupported(
                HttpRequestMethodNotSupportedException ex,
                HttpHeaders headers,
                HttpStatusCode status,
                WebRequest request) {
            return handleHttpRequestMethodNotSupported(ex, headers, status, request);
        }

        ResponseEntity<Object> handleMessageNotReadable(
                HttpMessageNotReadableException ex,
                HttpHeaders headers,
                HttpStatusCode status,
                WebRequest request) {
            return handleHttpMessageNotReadable(ex, headers, status, request);
        }
    }

    private static class EmptyHttpInputMessage implements HttpInputMessage {
        @Override
        public InputStream getBody() {
            return new ByteArrayInputStream(new byte[0]);
        }

        @Override
        public HttpHeaders getHeaders() {
            return new HttpHeaders();
        }
    }
}
