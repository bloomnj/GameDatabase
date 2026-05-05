package com.suntheory.GameDatabase.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class EnumSerializationTest {

    @Test
    void genresReturnDisplayName() {
        assertEquals("Action RPG", Genres.ACTION_RPG.getDisplayName());
    }

    @Test
    void platformsReturnDisplayName() {
        assertEquals("Nintendo Switch", Platforms.NINTENDO_SWITCH.getDisplayName());
    }
}
