package kata.imad.numbertostring.exposition;


import kata.imad.numbertostring.domain.model.NumberTransformation;
import kata.imad.numbertostring.application.service.NumberTransformationService;
import kata.imad.numbertostring.exposition.rest.NumberTransformationController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class NumberTransformationControllerTest {
    @Mock
    private NumberTransformationService numberTransformationService;

    @InjectMocks
    private NumberTransformationController numberTransformationController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(numberTransformationController).build();
       // numberTransformationController = new NumberTransformationController(numberTransformationService);
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
    void testNumberToString_Success(int input, String expected)  {

       when(numberTransformationService.transform(input)).thenReturn(new NumberTransformation(input,expected));

        ResponseEntity<String> response = numberTransformationController.transformNumber(input);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(expected, response.getBody());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1,0,100, 101, 1000})
    void testNumberToString_Failure() {

        int number = -1;
        String expectedMessage = "le nombre doit etre entre 0 et 100";
        when(numberTransformationService.transform(number)).thenThrow(new IllegalArgumentException(expectedMessage));

        ResponseEntity<String> response = numberTransformationController.transformNumber(number);

        assertEquals(400, response.getStatusCode().value());
        assertEquals(expectedMessage, response.getBody());
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
    void testNumberToString_ValidNumber(int number, String expectedResult) throws Exception {

        when(numberTransformationService.transform(number)).thenReturn(new NumberTransformation(number,expectedResult));

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/transform/{number}", number))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content().string(expectedResult));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1,0,100, 101, 1000})
    void testNumberToString_InvalidNumber(int number) throws Exception {

        String expectedMessage = "le nombre doit etre entre 0 et 100";
        when(numberTransformationService.transform(number)).thenThrow(new IllegalArgumentException(expectedMessage));

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/transform/{number}", number))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isBadRequest())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content().string(expectedMessage));
    }

}