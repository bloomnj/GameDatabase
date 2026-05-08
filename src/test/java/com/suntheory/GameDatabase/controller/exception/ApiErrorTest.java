package com.suntheory.GameDatabase.controller.exception;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class ApiErrorTest {

    @Test
    void constructorWithSingleErrorWrapsErrorInList() {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Invalid request", "Missing title");

        assertThat(apiError.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(apiError.getMessage()).isEqualTo("Invalid request");
        assertThat(apiError.getErrors()).containsExactly("Missing title");
    }

    @Test
    void allArgsConstructorStoresErrorList() {
        List<String> errors = List.of("Missing title", "Invalid year");

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Invalid request", errors);

        assertThat(apiError.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(apiError.getMessage()).isEqualTo("Invalid request");
        assertThat(apiError.getErrors()).isEqualTo(errors);
    }
}
