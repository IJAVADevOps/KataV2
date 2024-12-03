package kata.imad.numbertostring.application.rules;

public interface TransformationRule {
    /**
     * Applique une transformation sur un nombre.
     *
     * @param number Le nombre à transformer
     * @return Une chaîne transformée ou null si aucune transformation n'est applicable
     */
    String apply(int number);
}