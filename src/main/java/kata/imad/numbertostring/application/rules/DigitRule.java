package kata.imad.numbertostring.application.rules;


import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class DigitRule implements TransformationRule
{
    @Override
    public String apply(int number) {
        String numberStr = String.valueOf(number);
        StringBuilder result = new StringBuilder();

        for (char digit : numberStr.toCharArray()) {
            switch (digit) {
                case '3' -> result.append("FOO");
                case '5' -> result.append("BAR");
                case '7' -> result.append("QUIX");
            }
        }

        return !result.isEmpty() ? result.toString() : null;
    }
}