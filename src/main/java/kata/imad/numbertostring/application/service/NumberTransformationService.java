package kata.imad.numbertostring.application.service;


import kata.imad.numbertostring.domain.model.NumberTransformation;
import kata.imad.numbertostring.application.rules.TransformationRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NumberTransformationService {
    private final List<TransformationRule> rules;

    @Autowired
    public NumberTransformationService(List<TransformationRule> rules) {
        this.rules = rules;
    }

    public NumberTransformation transform(int number) {
        StringBuilder result = new StringBuilder();

        for (TransformationRule rule : rules) {
            String ruleResult = rule.apply(number);
            if (ruleResult != null) {
                result.append(ruleResult);
            }
        }

        String transformedValue = !result.isEmpty() ?
                result.toString() :
                String.valueOf(number);

        return new NumberTransformation(number, transformedValue);
    }
}