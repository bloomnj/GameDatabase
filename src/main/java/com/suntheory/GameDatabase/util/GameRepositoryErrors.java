package com.suntheory.GameDatabase.util;

public final class GameRepositoryErrors {
  public static final String ALL_FIELDS_REQUIRED = "All fields (title, genre, platform, releaseYear) are required.";
  public static final String GAME_NOT_FOUND = "Game id not found in database.";
  public static final String HTTP_NOT_READABLE = "HTTP message was not readable.";
  public static final String INVALID_GENRE_VALUE = "Invalid genre value: ";
  public static final String INVALID_PLATFORM_VALUE = "Invalid platform value: ";
  public static final String INVALID_REQUEST_BODY = "Request body is not valid.";
  public static final String INVALID_YEAR_FORMAT = "Release year must be a valid four-digit year.";
  public static final String METHOD_NOT_SUPPORTED = " method is not supported for this request.  Supported methods are ";
}