package com.suntheory.GameDatabase.repositories;

import org.springframework.data.repository.CrudRepository;

import com.suntheory.GameDatabase.entities.Game;

public interface GameRepository extends CrudRepository<Game, Long>  {  
  
}
