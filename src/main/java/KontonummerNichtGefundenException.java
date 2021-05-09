public class KontonummerNichtGefundenException extends Exception {
    /**
     * Exception, wenn versucht wird auf nicht vorhande Kontonummer zuzugreifen
     *
     * @param kontonummer die Nummer des nicht vorhandenen Kontos, auf das zugegriffen wurde
     */
    public KontonummerNichtGefundenException(long kontonummer) {
        super("Folgende Kontonummer nicht gefunden: " + kontonummer);
    }

}
