package kata.imad.numbertostring.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class NumberTransformationTest {
    @ParameterizedTest
    @CsvSource({
            "3, FOOFOO",
            "5, BARBAR",
            "15, FOOBARBAR",
            "53, BARFOO",
            "33, FOOFOOFOO",
            "7, QUIX",
            "27, FOOQUIX",
            "57, FOOBARQUIX",
            "1, 1",
            "2, 2"
    })
    void testValidNumberCreation(int input, String expected) {
        NumberTransformation transformation = new NumberTransformation(input, expected);
        assertEquals(input, transformation.getNumber());
        assertEquals(expected, transformation.getTransformedValue());
    }

    @Test
    void testInvalidNumberThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                new NumberTransformation(0, "")
        );
        assertThrows(IllegalArgumentException.class, () ->
                new NumberTransformation(100, "")
        );
    }
}