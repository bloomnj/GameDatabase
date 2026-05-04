package com.suntheory.GameDatabase.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.suntheory.GameDatabase.controller.util.GameRepositoryErrors;
import com.suntheory.GameDatabase.entities.Game;
import com.suntheory.GameDatabase.repositories.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameController gameController;

    private Game existingGame;

    @BeforeEach
    void setUp() {
        existingGame = new Game();
        existingGame.setId(1L);
        existingGame.setTitle("Original Title");
        existingGame.setGenre("Action");
        existingGame.setPlatform("PC");
        existingGame.setReleaseYear("2024");
    }

    @Test
    void getAllGames_returnsAllGames() {
        Iterable<Game> games = gameController.getAllGames();
        assertTrue(games != null);
    }

    @Test
    void getGameById_returnsOptionalGame() {
        when(gameRepository.findById(1L)).thenReturn(Optional.of(existingGame));

        Game result = gameController.getGameById(1L);

        assertTrue(result != null);
        assertEquals(existingGame, result);
    }

    @Test
    void getGameById_whenGameNotFound_throwsNotFound() {
        when(gameRepository.findById(2L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> gameController.getGameById(2L));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertTrue(exception.getReason().contains(GameRepositoryErrors.GAME_NOT_FOUND));
    }

    @Test
    void createNewGame_savesAndReturnsGame() {
        Game newGame = new Game();
        newGame.setTitle("New Game");
        newGame.setGenre("RPG");
        newGame.setPlatform("Console");
        newGame.setReleaseYear("2025");

        when(gameRepository.save(newGame)).thenReturn(newGame);

        Game result = gameController.createNewGame(newGame);

        assertEquals(newGame, result);
    }

    @Test
    void createNewGame_whenMissingFields_throwsBadRequest() {
        Game newGame = new Game();
        newGame.setTitle(null);
        newGame.setGenre("RPG");
        newGame.setPlatform("Console");
        newGame.setReleaseYear("2025");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> gameController.createNewGame(newGame));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertTrue(exception.getReason().contains(GameRepositoryErrors.ALL_FIELDS_REQUIRED));
    }

    @Test
    void createNewGame_whenInvalidYearFormat_throwsBadRequest() {
        Game newGame = new Game();
        newGame.setTitle("New Game");
        newGame.setGenre("RPG");
        newGame.setPlatform("Console");
        newGame.setReleaseYear("25");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> gameController.createNewGame(newGame));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertTrue(exception.getReason().contains(GameRepositoryErrors.INVALID_YEAR_FORMAT));
    }

    @Test
    void createNewGame_whenInvalidPlatform_throwsBadRequest() {
        Game newGame = new Game();
        newGame.setTitle("New Game");
        newGame.setGenre("RPG");
        newGame.setPlatform("Invalid");
        newGame.setReleaseYear("2025");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> gameController.createNewGame(newGame));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertTrue(exception.getReason().contains(GameRepositoryErrors.INVALID_PLATFORM_VALUE));
    }

    @Test
    void createNewGame_whenInvalidGenre_throwsBadRequest() {
        Game newGame = new Game();
        newGame.setTitle("New Game");
        newGame.setGenre("Invalid");
        newGame.setPlatform("Console");
        newGame.setReleaseYear("2025");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> gameController.createNewGame(newGame));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertTrue(exception.getReason().contains(GameRepositoryErrors.INVALID_GENRE_VALUE));
    }

    @Test
    void updateGame_updatesNonNullFields() {
        Game updatedGame = new Game();
        updatedGame.setTitle("Updated Title");
        updatedGame.setGenre(null);
        updatedGame.setPlatform("Console");
        updatedGame.setReleaseYear("2026");

        when(gameRepository.findById(1L)).thenReturn(Optional.of(existingGame));
        when(gameRepository.save(existingGame)).thenReturn(existingGame);

        Game result = gameController.updateGame(1L, updatedGame);

        assertEquals("Updated Title", result.getTitle());
        assertEquals("Action", result.getGenre());
        assertEquals("Console", result.getPlatform());
        assertEquals("2026", result.getReleaseYear());

        ArgumentCaptor<Game> captor = ArgumentCaptor.forClass(Game.class);
        verify(gameRepository).save(captor.capture());
        assertEquals(result, captor.getValue());
    }

    @Test
    void updateGame_whenMissingGame_throwsNotFound() {
        Game updatedGame = new Game();
        when(gameRepository.findById(2L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> gameController.updateGame(2L, updatedGame));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertTrue(exception.getReason().contains(GameRepositoryErrors.GAME_NOT_FOUND));
    }

    @Test
    void updateGame_whenInvalidYearFormat_throwsBadRequest() {
        Game updatedGame = new Game();
        updatedGame.setTitle("Updated Title");
        updatedGame.setGenre("Action");
        updatedGame.setPlatform("PC");
        updatedGame.setReleaseYear("25");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> gameController.updateGame(1L, updatedGame));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertTrue(exception.getReason().contains(GameRepositoryErrors.INVALID_YEAR_FORMAT));
    }

    @Test
    void updateGame_whenInvalidPlatform_throwsBadRequest() {
        Game updatedGame = new Game();
        updatedGame.setTitle("Updated Title");
        updatedGame.setGenre("Action");
        updatedGame.setPlatform("Invalid");
        updatedGame.setReleaseYear("2024");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> gameController.updateGame(1L, updatedGame));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertTrue(exception.getReason().contains(GameRepositoryErrors.INVALID_PLATFORM_VALUE));
    }

    @Test
    void updateGame_whenInvalidGenre_throwsBadRequest() {
        Game updatedGame = new Game();
        updatedGame.setTitle("Updated Title");
        updatedGame.setGenre("Invalid");
        updatedGame.setPlatform("PC");
        updatedGame.setReleaseYear("2024");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> gameController.updateGame(1L, updatedGame));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertTrue(exception.getReason().contains(GameRepositoryErrors.INVALID_GENRE_VALUE));
    }

    @Test
    void deleteGame_deletesAndReturnsGame() {
        when(gameRepository.findById(1L)).thenReturn(Optional.of(existingGame));

        Game result = gameController.deleteGame(1L);

        assertEquals(existingGame, result);
        verify(gameRepository).delete(existingGame);
    }

    @Test
    void deleteGame_whenMissingGame_throwsNotFound() {
        when(gameRepository.findById(2L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> gameController.deleteGame(2L));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertTrue(exception.getReason().contains(GameRepositoryErrors.GAME_NOT_FOUND));
    }
}
