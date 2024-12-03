package kata.imad.numbertostring.application.rules;


import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class DivisibilityRule implements TransformationRule {
    @Override
    public String apply(int number) {
        StringBuilder result = new StringBuilder();
        if (number % 3 == 0) result.append("FOO");
        if (number % 5 == 0) result.append("BAR");
        return !result.isEmpty() ? result.toString() : null;
    }
}

