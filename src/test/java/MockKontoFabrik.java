import org.mockito.Mockito;

/**
 * Fabrik zum Erstellen eines MockKontos
 */
public class MockKontoFabrik extends KontoFabrik {
    Konto konto;
    Kontoart kontoArt = Kontoart.GIROKONTO;

    public void setKontoArt(Kontoart kontoArt) {
        this.kontoArt = kontoArt;
    }

    /**
     * Erstellt MockKonto (automatisch Girokonto solange kontoArt nicht verändert)
     * @param inhaber Inhaber des Kontos
     * @param kontonummer einzigartige Nummer des Kontos
     * @return neues Mock Konto
     */
    @Override
    public Konto kontoErstellen(Kunde inhaber, long kontonummer) {
        switch (kontoArt) {
            case GIROKONTO:
                konto = Mockito.mock(Girokonto.class);
                break;
            case SPARBUCH:
                konto = Mockito.mock(Sparbuch.class);
                break;
            default:
                throw new IllegalArgumentException();
        }
        Mockito.when(konto.getInhaber()).thenReturn(inhaber);
        Mockito.when(konto.getKontonummer()).thenReturn(kontonummer);

        return konto;
    }

    /**
     * Erstellt MockKonto mit Möglichkeit die Art des Mockkontos zu bestimmen
     * @param inhaber
     * @param kontonummer
     * @param kontoArt
     * @return
     */
    public Konto kontoErstellen(Kunde inhaber, long kontonummer, Kontoart kontoArt) {
        this.kontoArt = kontoArt;
        return this.kontoErstellen(inhaber, kontonummer);
    }

}
