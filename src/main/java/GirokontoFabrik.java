/**
 * Fabrik zum Erstellen eines Girokontos
 */
public class GirokontoFabrik extends KontoFabrik{
    @Override
    public Konto kontoErstellen(Kunde inhaber, long kontonummer) {
        return new Girokonto(inhaber, kontonummer, 500);
    }
}
