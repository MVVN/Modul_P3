/**
 * Abstrakte Fabrik für Konten
 */
public abstract class KontoFabrik {
    /**
     * zum Erstellen eines neuen Kontos
     * @param inhaber Inhaber des Kontos
     * @param kontonummer einzigartige Nummer des Kontos
     * @return das neu erstellte Konto
     */
    public abstract Konto kontoErstellen(Kunde inhaber, long kontonummer);
}
