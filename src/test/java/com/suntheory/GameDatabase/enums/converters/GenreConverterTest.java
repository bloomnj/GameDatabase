package com.suntheory.GameDatabase.enums.converters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.Test;

import com.suntheory.GameDatabase.enums.Genres;
import com.suntheory.GameDatabase.util.GameRepositoryErrors;

class GenreConverterTest {

    private final GenreConverter converter = new GenreConverter();

    @Test
    void convertToDatabaseColumn_whenGenreIsNull_returnsNull() {
        assertThat(converter.convertToDatabaseColumn(null)).isNull();
    }

    @Test
    void convertToDatabaseColumn_returnsDisplayName() {
        assertThat(converter.convertToDatabaseColumn(Genres.ACTION_RPG)).isEqualTo("Action RPG");
    }

    @Test
    void convertToEntityAttribute_whenDatabaseValueIsNull_returnsNull() {
        assertThat(converter.convertToEntityAttribute(null)).isNull();
    }

    @Test
    void convertToEntityAttribute_matchesDisplayNameIgnoringCase() {
        assertThat(converter.convertToEntityAttribute("action rpg")).isEqualTo(Genres.ACTION_RPG);
    }

    @Test
    void convertToEntityAttribute_whenValueIsInvalid_throwsException() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> converter.convertToEntityAttribute("Shooter"))
                .withMessage(GameRepositoryErrors.INVALID_GENRE_VALUE + "Shooter");
    }
}
