package com.suntheory.GameDatabase.controller.util;

public final class GameRepositoryErrors {
  public static final String GAME_NOT_FOUND = "Game id not found in database.";
  public static final String ALL_FIELDS_REQUIRED = "All fields (title, genre, platform, releaseYear) are required.";
  public static final String INVALID_YEAR_FORMAT = "Release year must be a valid four-digit year.";
  public static final String INVALID_PLATFORM_VALUE = "Platform must be one of: PC, Console, Handheld, Mobile.";
  public static final String INVALID_GENRE_VALUE = "Genre must be one of: Action, Adventure, RPG, Strategy, Simulation, Sports, Puzzle.";
}