package kata.imad.numbertostring.exposition.rest;

import kata.imad.numbertostring.domain.model.NumberTransformation;
import kata.imad.numbertostring.application.service.NumberTransformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transform")
public class NumberTransformationController {

    private final NumberTransformationService service;

    @Autowired
    public NumberTransformationController(NumberTransformationService service) {
        this.service = service;
    }

    @GetMapping("/{number}")
    public ResponseEntity<String> transformNumber(@PathVariable int number) {
        try {
            NumberTransformation transformation = service.transform(number);
            return ResponseEntity.ok(transformation.getTransformedValue());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

