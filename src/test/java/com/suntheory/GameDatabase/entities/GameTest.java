package com.suntheory.GameDatabase.entities;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        String testGenre = "Adventure";
        game.setGenre(testGenre);
        assertEquals(testGenre, game.getGenre());
    }

    @Test
    void testGamePlatformGetterAndSetter() {
        String testPlatform = "Nintendo Switch";
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