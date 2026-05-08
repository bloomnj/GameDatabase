package com.suntheory.GameDatabase.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.suntheory.GameDatabase.entities.Game;
import com.suntheory.GameDatabase.enums.Genres;
import com.suntheory.GameDatabase.enums.Platforms;
import com.suntheory.GameDatabase.repositories.GameRepository;
import com.suntheory.GameDatabase.util.GameRepositoryErrors;

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
        existingGame.setGenre(Genres.ACTION_RPG);
        existingGame.setPlatform(Platforms.PC);
        existingGame.setReleaseYear("2024");
    }

    @Test
    void getAllGames_returnsAllGames() {
        List<Game> expectedGames = List.of(existingGame);
        when(gameRepository.findAll()).thenReturn(expectedGames);

        Iterable<Game> games = gameController.getAllGames();

        assertThat(games).isEqualTo(expectedGames);
        verify(gameRepository).findAll();
    }

    @Test
    void getGameById_returnsGame() {
        when(gameRepository.findById(1L)).thenReturn(Optional.of(existingGame));

        Game result = gameController.getGameById(1L);

        assertThat(result).isEqualTo(existingGame);
    }

    @Test
    void getGameById_whenGameNotFound_throwsBadRequest() {
        when(gameRepository.findById(2L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> gameController.getGameById(2L));

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exception.getReason()).isEqualTo(GameRepositoryErrors.GAME_NOT_FOUND);
    }

    @Test
    void createNewGame_savesAndReturnsGame() {
        Game newGame = new Game();
        newGame.setTitle("New Game");
        newGame.setGenre(Genres.ACTION_RPG);
        newGame.setPlatform(Platforms.NINTENDO_SWITCH);
        newGame.setReleaseYear("2025");

        when(gameRepository.save(newGame)).thenReturn(newGame);

        Game result = gameController.createNewGame(newGame);

        assertThat(result).isEqualTo(newGame);
        verify(gameRepository).save(newGame);
    }

    @Test
    void createNewGame_whenMissingTitle_throwsBadRequest() {
        Game newGame = new Game();
        newGame.setTitle(null);
        newGame.setGenre(Genres.ACTION_RPG);
        newGame.setPlatform(Platforms.NINTENDO_SWITCH);
        newGame.setReleaseYear("2025");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> gameController.createNewGame(newGame));

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exception.getReason()).isEqualTo(GameRepositoryErrors.ALL_FIELDS_REQUIRED);
    }

    @Test
    void createNewGame_whenMissingGenre_throwsBadRequest() {
        Game newGame = new Game();
        newGame.setTitle("New Game");
        newGame.setGenre(null);
        newGame.setPlatform(Platforms.NINTENDO_SWITCH);
        newGame.setReleaseYear("2025");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> gameController.createNewGame(newGame));

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exception.getReason()).isEqualTo(GameRepositoryErrors.ALL_FIELDS_REQUIRED);
    }

    @Test
    void createNewGame_whenMissingPlatform_throwsBadRequest() {
        Game newGame = new Game();
        newGame.setTitle("New Game");
        newGame.setGenre(Genres.ACTION_RPG);
        newGame.setPlatform(null);
        newGame.setReleaseYear("2025");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> gameController.createNewGame(newGame));

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exception.getReason()).isEqualTo(GameRepositoryErrors.ALL_FIELDS_REQUIRED);
    }

    @Test
    void createNewGame_whenMissingReleaseYear_throwsBadRequest() {
        Game newGame = new Game();
        newGame.setTitle("New Game");
        newGame.setGenre(Genres.ACTION_RPG);
        newGame.setPlatform(Platforms.NINTENDO_SWITCH);
        newGame.setReleaseYear(null);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> gameController.createNewGame(newGame));

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exception.getReason()).isEqualTo(GameRepositoryErrors.ALL_FIELDS_REQUIRED);
    }

    @Test
    void createNewGame_whenInvalidYearFormat_throwsBadRequest() {
        Game newGame = new Game();
        newGame.setTitle("New Game");
        newGame.setGenre(Genres.ACTION_RPG);
        newGame.setPlatform(Platforms.NINTENDO_SWITCH);
        newGame.setReleaseYear("20-25");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> gameController.createNewGame(newGame));

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exception.getReason()).isEqualTo(GameRepositoryErrors.INVALID_YEAR_FORMAT);
    }

    @Test
    void updateGame_updatesNonNullFields() {
        Game updatedGame = new Game();
        updatedGame.setTitle("Updated Title");
        updatedGame.setGenre(Genres.PUZZLE);
        updatedGame.setPlatform(Platforms.PLAYSTATION_5);
        updatedGame.setReleaseYear("2026");

        when(gameRepository.findById(1L)).thenReturn(Optional.of(existingGame));
        when(gameRepository.save(existingGame)).thenReturn(existingGame);

        Game result = gameController.updateGame(1L, updatedGame);

        assertThat(result.getTitle()).isEqualTo("Updated Title");
        assertThat(result.getGenre()).isEqualTo(Genres.PUZZLE);
        assertThat(result.getPlatform()).isEqualTo(Platforms.PLAYSTATION_5);
        assertThat(result.getReleaseYear()).isEqualTo("2026");

        ArgumentCaptor<Game> captor = ArgumentCaptor.forClass(Game.class);
        verify(gameRepository).save(captor.capture());
        assertThat(result).isEqualTo(captor.getValue());
    }

    @Test
    void updateGame_whenFieldsAreNull_keepsExistingValues() {
        Game updatedGame = new Game();

        when(gameRepository.findById(1L)).thenReturn(Optional.of(existingGame));
        when(gameRepository.save(existingGame)).thenReturn(existingGame);

        Game result = gameController.updateGame(1L, updatedGame);

        assertThat(result.getTitle()).isEqualTo("Original Title");
        assertThat(result.getGenre()).isEqualTo(Genres.ACTION_RPG);
        assertThat(result.getPlatform()).isEqualTo(Platforms.PC);
        assertThat(result.getReleaseYear()).isEqualTo("2024");
        verify(gameRepository).save(existingGame);
    }

    @Test
    void updateGame_whenMissingGame_throwsBadRequest() {
        Game updatedGame = new Game();
        updatedGame.setTitle("Updated Title");

        when(gameRepository.findById(2L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> gameController.updateGame(2L, updatedGame));

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exception.getReason()).isEqualTo(GameRepositoryErrors.GAME_NOT_FOUND);
    }

    @Test
    void updateGame_whenInvalidYearFormat_throwsBadRequest() {
        Game updatedGame = new Game();
        updatedGame.setTitle("Updated Title");
        updatedGame.setReleaseYear("2026-01");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> gameController.updateGame(1L, updatedGame));

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exception.getReason()).isEqualTo(GameRepositoryErrors.INVALID_YEAR_FORMAT);
        verify(gameRepository, never()).findById(1L);
        verify(gameRepository, never()).save(existingGame);
    }

    @Test
    void deleteGame_deletesAndReturnsGame() {
        when(gameRepository.findById(1L)).thenReturn(Optional.of(existingGame));

        Game result = gameController.deleteGame(1L);

        assertThat(result).isEqualTo(existingGame);
        verify(gameRepository).delete(existingGame);
    }

    @Test
    void deleteGame_whenMissingGame_throwsBadRequest() {
        when(gameRepository.findById(2L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> gameController.deleteGame(2L));

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exception.getReason()).isEqualTo(GameRepositoryErrors.GAME_NOT_FOUND);
    }
}
