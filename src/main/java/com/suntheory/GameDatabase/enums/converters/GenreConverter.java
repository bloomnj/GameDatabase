package com.suntheory.GameDatabase.enums.converters;

import com.suntheory.GameDatabase.enums.Genres;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class GenreConverter implements AttributeConverter<Genres, String> {
  @Override
  public String convertToDatabaseColumn(Genres attribute) {
    if (attribute == null) {
      return null;
    }
    return attribute.getDisplayName();
  }

  @Override
  public Genres convertToEntityAttribute(String dbData) {
    if (dbData == null) {
      return null;
    }

    return Stream.of(Genres.values())
        .filter(g -> g.getDisplayName().equalsIgnoreCase(dbData))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Invalid genre value: " + dbData));
  } 
}