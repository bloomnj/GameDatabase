package com.suntheory.GameDatabase.entities;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.suntheory.GameDatabase.enums.Genres;
import com.suntheory.GameDatabase.enums.Platforms;

class GameTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void testGameIdGetterAndSetter() {
        Long testId = 1L;
        game.setId(testId);
        assertEquals(testId, game.getId());
    }

    @Test
    void testGameTitleGetterAndSetter() {
        String testTitle = "The Legend of Zelda";
        game.setTitle(testTitle);
        assertEquals(testTitle, game.getTitle());
    }

    @Test
    void testGameGenreGetterAndSetter() {
        Genres testGenre = Genres.ADVENTURE;
        game.setGenre(testGenre);
        assertEquals(testGenre, game.getGenre());
    }

    @Test
    void testGamePlatformGetterAndSetter() {
        Platforms testPlatform = Platforms.NINTENDO_SWITCH;
        game.setPlatform(testPlatform);
        assertEquals(testPlatform, game.getPlatform());
    }

    @Test
    void testGameReleaseYearGetterAndSetter() {
        String testReleaseYear = "2017";
        game.setReleaseYear(testReleaseYear);
        assertEquals(testReleaseYear, game.getReleaseYear());
    }
}