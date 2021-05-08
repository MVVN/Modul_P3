/**
 * Währungen und Umrechnungskurs in Euro zur Verwendung für Konten etc.
 */
public enum Waehrung {

    /**
     * Euro
     */
    EUR(1),
    /**
     * Bulgarische Leva
     */
    BGN(1.95583),
    /**
     * Litauische Litas
     */
    LTL(3.4528),
    /**
     * Konvertible Mark
     */
    KM(1.95583);

    /**
     * Umrechnungskurs zum Euro
     */
    private double euroKurs;

    private Waehrung(double euroKurs) {
        this.euroKurs = euroKurs;
    }

    /**
     * used for testing
     * @return Umrechnungskurs des Euros in die Währung
     */
    public double getEuroKurs () {
        return this.euroKurs;
    }

    /**
     * rechnet Euro in passende Währung um
     * @param betrag betrag in Euro der umzurechnen ist
     * @return neuer Wert des Geldes in anderer Währung
     */
    public double euroInWaehrungUmrechnen(double betrag) {
        return betrag * euroKurs;
    }

    /**
     * Rechnet Währung in  Euro um
     * @param betrag betrag in Währung X der in Euro umzurechnen ist
     * @return neuer Wert in Euro
     */
    public double waehrungInEuroUmrechnen(double betrag) {
        return betrag / euroKurs;
    }

    /**
     * wandelt Waehrung direkt in andere Waehrung (!= EUR) um
     * @param betrag betrag der alten waehrung
//     * @param alt alte waehrung
     * @param neu neue waehrung in die umgewandelt werden soll
     * @return neuer betrag der neuen waehrung
     */
    public double waehrungInWaehrungUmrechnen(double betrag, /*Waehrung alt,*/ Waehrung neu) {
//       Euro in neue Währung
        return neu.euroInWaehrungUmrechnen
//                derzeitige Währung in Euro umrechnen
                (this.waehrungInEuroUmrechnen(betrag));
    }

}
