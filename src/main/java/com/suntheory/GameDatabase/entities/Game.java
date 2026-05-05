package com.suntheory.GameDatabase.entities;

import com.suntheory.GameDatabase.enums.Genres;
import com.suntheory.GameDatabase.enums.Platforms;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "games")
public class Game {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "title")
  private String title;

  @Column(name = "genre")
  private Genres genre;

  @Column(name = "platform")
  private Platforms platform;

  @Column(name = "release_year")
  private String releaseYear;
}
