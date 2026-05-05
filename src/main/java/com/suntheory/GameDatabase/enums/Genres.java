package com.suntheory.GameDatabase.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Genres { 
  ACTION_RPG ("Action RPG"),
  ADVENTURE ("Adventure"),
  METROIDVANIA ("Metroidvania"),
  PLATFORMER ("Platformer"),
  ROGUELIKE ("Roguelike"),
  SIMULATION ("Simulation"),
  SANDBOX ("Sandbox"),
  PUZZLE ("Puzzle");

  private String displayName;

  Genres(String displayName) {
    this.displayName = displayName;
  }

  @JsonValue
  public String getDisplayName() {
    return displayName;
  }
}
