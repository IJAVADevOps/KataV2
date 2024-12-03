package kata.imad.numbertostring.domain.model;



public class NumberTransformation {
    private final int number;
    private final String transformedValue;

    public NumberTransformation(int number, String transformedValue) {
        validateNumber(number);
        this.number = number;
        this.transformedValue = transformedValue;
    }

    private void validateNumber(int number) {
        if (number <= 0 || number >= 100) {
            throw new IllegalArgumentException("le nombre doit etre entre 0 et 100");
        }
    }

    public int getNumber() {
        return number;
    }

    public String getTransformedValue() {
        return transformedValue;
    }


}