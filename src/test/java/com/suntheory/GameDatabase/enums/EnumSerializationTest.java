package com.suntheory.GameDatabase.enums;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class EnumSerializationTest {

    @ParameterizedTest
    @CsvSource({
            "ACTION_RPG, Action RPG",
            "ADVENTURE, Adventure",
            "METROIDVANIA, Metroidvania",
            "PLATFORMER, Platformer",
            "ROGUELIKE, Roguelike",
            "SIMULATION, Simulation",
            "SANDBOX, Sandbox",
            "PUZZLE, Puzzle"
    })
    void genresReturnDisplayName(String enumName, String displayName) {
        assertThat(Genres.valueOf(enumName).getDisplayName()).isEqualTo(displayName);
    }

    @ParameterizedTest
    @CsvSource({
            "PC, PC",
            "PLAYSTATION_5, PlayStation 5",
            "NINTENDO_SWITCH, Nintendo Switch",
            "MULTI_PLATFORM, Multi-Platform"
    })
    void platformsReturnDisplayName(String enumName, String displayName) {
        assertThat(Platforms.valueOf(enumName).getDisplayName()).isEqualTo(displayName);
    }
}
