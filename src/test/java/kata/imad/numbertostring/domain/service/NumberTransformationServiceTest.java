package kata.imad.numbertostring.domain.service;

import kata.imad.numbertostring.application.service.NumberTransformationService;
import kata.imad.numbertostring.domain.model.NumberTransformation;
import kata.imad.numbertostring.application.rules.DivisibilityRule;
import kata.imad.numbertostring.application.rules.DigitRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class NumberTransformationServiceTest {
    private NumberTransformationService service;

    @BeforeEach
    void setUp() {
        service = new NumberTransformationService(List.of(
                new DivisibilityRule(),
                new DigitRule()
        ));
    }

    @Test
    void testDivisibleByThree() {
        NumberTransformation result = service.transform(3);
        assertEquals("FOOFOO", result.getTransformedValue());
    }

    @Test
    void testContainsDigitFive() {
        NumberTransformation result = service.transform(51);
        assertTrue(result.getTransformedValue().contains("BAR"));
    }

    @Test
    void testNoTransformation() {
        NumberTransformation result = service.transform(11);
        assertEquals("11", result.getTransformedValue());
    }
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
    void testNumberTransformation(int input, String expected) {
        NumberTransformation result = service.transform(input);
        assertEquals(expected, result.getTransformedValue());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1,0,100, 101, 1000})
    void testInvalidNumbers(int input) {
        assertThrows(IllegalArgumentException.class,
                () -> service.transform(input));
    }

    @Test
    void testServiceNotNull() {
        assertNotNull(service);
    }
}