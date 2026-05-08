package com.suntheory.GameDatabase.controller.exception;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatusCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiError {
  private HttpStatusCode status;
  private String message;
  private List<String> errors;

  public ApiError(HttpStatusCode status, String message, String error) {
    this.status = status;
    this.message = message;
    this.errors = Arrays.asList(error);
  }
}
