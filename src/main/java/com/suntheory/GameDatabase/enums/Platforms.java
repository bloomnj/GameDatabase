package com.suntheory.GameDatabase.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Platforms {
  PC ("PC"),
  PLAYSTATION_5 ("PlayStation 5"),
  NINTENDO_SWITCH ("Nintendo Switch"),
  MULTI_PLATFORM ("Multi-Platform");

  private String displayName;

  Platforms(String displayName) {
    this.displayName = displayName;
  }

  @JsonValue
  public String getDisplayName() {
    return displayName;
  }
}
