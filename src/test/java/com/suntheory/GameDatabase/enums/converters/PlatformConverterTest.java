package com.suntheory.GameDatabase.enums.converters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.Test;

import com.suntheory.GameDatabase.enums.Platforms;
import com.suntheory.GameDatabase.util.GameRepositoryErrors;

class PlatformConverterTest {

    private final PlatformConverter converter = new PlatformConverter();

    @Test
    void convertToDatabaseColumn_whenPlatformIsNull_returnsNull() {
        assertThat(converter.convertToDatabaseColumn(null)).isNull();
    }

    @Test
    void convertToDatabaseColumn_returnsDisplayName() {
        assertThat(converter.convertToDatabaseColumn(Platforms.NINTENDO_SWITCH)).isEqualTo("Nintendo Switch");
    }

    @Test
    void convertToEntityAttribute_whenDatabaseValueIsNull_returnsNull() {
        assertThat(converter.convertToEntityAttribute(null)).isNull();
    }

    @Test
    void convertToEntityAttribute_matchesDisplayNameIgnoringCase() {
        assertThat(converter.convertToEntityAttribute("playstation 5")).isEqualTo(Platforms.PLAYSTATION_5);
    }

    @Test
    void convertToEntityAttribute_whenValueIsInvalid_throwsException() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> converter.convertToEntityAttribute("Xbox"))
                .withMessage(GameRepositoryErrors.INVALID_PLATFORM_VALUE + "Xbox");
    }
}
