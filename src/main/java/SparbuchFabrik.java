/**
 * Fabrik zum Erstellen eines Sparbuchs
 */
public class SparbuchFabrik extends KontoFabrik{
    @Override
    public Konto kontoErstellen(Kunde inhaber, long kontonummer) {
        return new Sparbuch(inhaber, kontonummer);
    }
}
