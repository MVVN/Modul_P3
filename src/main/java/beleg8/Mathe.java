package beleg8;

import java.util.function.Function;

public class Mathe {

    /**
     * Funktion zur Nullstellenberechnung einer Funktion in gegebenem Intervall
     *
     * @param unterGrenze Anfangswert des Intervalls
     * @param oberGrenze  Endwert des Intervalls
     * @param function    die Funktion, von welcher die Nullstelle gefunden werden soll
     * @return die Nullstelle als double
     * @throws KeinVorzeichenwechselException im Falle, dass im Intervall kein Vorzeichenwechsel stattfindet
     */
    public static double nullstelleSchaetzen(double unterGrenze,
                                             double oberGrenze,
                                             Function<Double, Double> function) throws KeinVorzeichenwechselException {
        if (function.apply(unterGrenze) * function.apply(oberGrenze) > 0) {
            throw new KeinVorzeichenwechselException();
        }
        if (function.apply(unterGrenze) == 0) {
            return unterGrenze;
        } else if (function.apply(oberGrenze) == 0) {
            return oberGrenze;
        }
        double mitte = (oberGrenze + unterGrenze) / 2;

        if (Math.abs(oberGrenze - unterGrenze) <= 0.1) {
            return mitte;
        } else if (function.apply(mitte) == 0) {
            return mitte;
        } else if (function.apply(unterGrenze) * function.apply(mitte) < 0) {
            return nullstelleSchaetzen(unterGrenze, mitte, function);
        } else {
            return nullstelleSchaetzen(mitte, oberGrenze, function);
        }
    }

}
