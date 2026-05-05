package com.suntheory.GameDatabase.enums.converters;

import com.suntheory.GameDatabase.enums.Platforms;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class PlatformConverter implements AttributeConverter<Platforms, String> {
  @Override
  public String convertToDatabaseColumn(Platforms attribute) {
    if (attribute == null) {
      return null;
    }
    return attribute.getDisplayName();
  }

  @Override
  public Platforms convertToEntityAttribute(String dbData) {
    if (dbData == null) {
      return null;
    }

    return Stream.of(Platforms.values())
        .filter(p -> p.getDisplayName().equalsIgnoreCase(dbData))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Invalid platform value: " + dbData));
  } 
  
}
