package com.suntheory.GameDatabase.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.suntheory.GameDatabase.controller.util.GameRepositoryConstants;
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
  public Game createNewGame(@RequestBody Game game) {
    return this.gameRepository.save(game);
  }

  @PutMapping("/{id}")
  public Game updateGame(@PathVariable Long id, @RequestBody Game updatedGame) throws ResponseStatusException {
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
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, GameRepositoryConstants.GAME_NOT_FOUND);
    }

    return gameOptional.get();
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
