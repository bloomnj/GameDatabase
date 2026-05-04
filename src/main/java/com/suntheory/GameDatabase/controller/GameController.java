package com.suntheory.GameDatabase.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.suntheory.GameDatabase.controller.util.GameRepositoryErrors;
import com.suntheory.GameDatabase.entities.Game;
import com.suntheory.GameDatabase.repositories.GameRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/games")
public class GameController {
  @Autowired
  private GameRepository gameRepository;

  @GetMapping
  public Iterable<Game> getAllGames() {
    return gameRepository.findAll();
  }

  @GetMapping("/{id}")
  public Game getGameById(@PathVariable Long id) throws ResponseStatusException {
    return findGameInDatabase(id);
  }

  @PostMapping
  public Game createNewGame(@RequestBody Game game) throws ResponseStatusException {
    validateAllFieldsPresent(game);
    validateGameFields(game);

    return this.gameRepository.save(game);
  }

  @PutMapping("/{id}")
  public Game updateGame(@PathVariable Long id, @RequestBody Game updatedGame) throws ResponseStatusException {
    validateGameFields(updatedGame);

    Game gameToUpdate = updateGameFields(id, updatedGame);

    this.gameRepository.save(gameToUpdate);

    return gameToUpdate;
  }

  @DeleteMapping("/{id}")
  public Game deleteGame(@PathVariable Long id) throws ResponseStatusException {
    Game gameToDelete = findGameInDatabase(id);

    this.gameRepository.delete(gameToDelete);

    return gameToDelete;
  }

  private Game findGameInDatabase(Long id) throws ResponseStatusException{
    Optional<Game> gameOptional = this.gameRepository.findById(id);

    if (!gameOptional.isPresent()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, GameRepositoryErrors.GAME_NOT_FOUND);
    }

    return gameOptional.get();
  }

  private void validateAllFieldsPresent(Game game) throws ResponseStatusException {
    if (game.getTitle() == null || game.getGenre() == null || game.getPlatform() == null || game.getReleaseYear() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, GameRepositoryErrors.ALL_FIELDS_REQUIRED);
    }
  }

  private void validateGameFields(Game game) throws ResponseStatusException {
    if(game.getReleaseYear() != null) {
       validateYearFormat(game.getReleaseYear());
    }

    if(game.getPlatform() != null) {
      validatePlatformValue(game.getPlatform());
    }

    if(game.getGenre() != null) {
      validateGenreValue(game.getGenre());
    }
  }

  private void validateYearFormat(String year) throws ResponseStatusException {
    if (!year.matches("\\d{4}")) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, GameRepositoryErrors.INVALID_YEAR_FORMAT);
    }
  }

  private void validatePlatformValue(String platform) throws ResponseStatusException {
    if (!platform.equalsIgnoreCase("PC") && !platform.equalsIgnoreCase("Console") && !platform.equalsIgnoreCase("Handheld") && !platform.equalsIgnoreCase("Mobile")) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, GameRepositoryErrors.INVALID_PLATFORM_VALUE);
    }
  }

  private void validateGenreValue(String genre) throws ResponseStatusException {
    if (!genre.equalsIgnoreCase("Action") && !genre.equalsIgnoreCase("Adventure") && !genre.equalsIgnoreCase("RPG") && !genre.equalsIgnoreCase("Strategy") && !genre.equalsIgnoreCase("Sports")) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, GameRepositoryErrors.INVALID_GENRE_VALUE);
    }
  }

  private Game updateGameFields(Long id, Game updatedGame) {
    Game gameToUpdate = findGameInDatabase(id);

    if (updatedGame.getTitle() != null) {
      gameToUpdate.setTitle(updatedGame.getTitle());
    }

    if (updatedGame.getGenre() != null) {
      gameToUpdate.setGenre(updatedGame.getGenre());
    } 

    if (updatedGame.getPlatform() != null) {
      gameToUpdate.setPlatform(updatedGame.getPlatform());
    } 

    if (updatedGame.getReleaseYear() != null) {
      gameToUpdate.setReleaseYear(updatedGame.getReleaseYear());
    }

    return gameToUpdate;
  }  
}
