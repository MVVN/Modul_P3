///**
// * Ein Girokonto
// *
// * @author Doro
// */
//public class Girokonto extends Konto implements Ueberweisungsfaehig {
//    /**
//     * Wert, bis zu dem das Konto �berzogen werden darf
//     */
//    private double dispo;
//
//    /**
//     * erzeugt ein leeres, nicht gesperrtes Standard-Girokonto
//     * von Herrn MUSTERMANN
//     */
//    public Girokonto() {
//        super(Kunde.MUSTERMANN, 99887766);
//        this.dispo = 500;
//    }
//
//    /**
//     * erzeugt ein Girokonto mit den angegebenen Werten
//     *
//     * @param inhaber Kontoinhaber
//     * @param nummer  Kontonummer
//     * @param dispo   Dispo
//     * @throws IllegalArgumentException wenn der inhaber null ist oder der angegebene dispo negativ bzw. NaN ist
//     */
//    public Girokonto(Kunde inhaber, long nummer, double dispo) {
//        //nur Kommentare...
//        super(inhaber, nummer);
//        if (dispo < 0 || Double.isNaN(dispo))
//            throw new IllegalArgumentException("Der Dispo ist nicht g�ltig!");
//        this.dispo = dispo;
//    }
//
//    /**
//     * liefert den Dispo
//     *
//     * @return Dispo von this
//     */
//    public double getDispo() {
//        return dispo;
//    }
//
//    /**
//     * setzt den Dispo neu
//     *
//     * @param dispo muss gr��er sein als 0
//     * @throws IllegalArgumentException wenn dispo negativ bzw. NaN ist
//     */
//    public void setDispo(double dispo) {
//        if (dispo < 0 || Double.isNaN(dispo))
//            throw new IllegalArgumentException("Der Dispo ist nicht gültig!");
//        this.dispo = dispo;
//    }
//
//    @Override
//    public boolean ueberweisungAbsenden(double betrag,
//                                        String empfaenger, long nachKontonr,
//                                        long nachBlz, String verwendungszweck)
//            throws GesperrtException {
//        if (this.isGesperrt())
//            throw new GesperrtException(this.getKontonummer());
//        if (betrag < 0 || Double.isNaN(betrag) || empfaenger == null || verwendungszweck == null)
//            throw new IllegalArgumentException("Parameter fehlerhaft");
//        if (getKontostand() - betrag >= -dispo) {
//            setKontostand(getKontostand() - betrag);
//            return true;
//        } else
//            return false;
//    }
//
//    @Override
//    public void ueberweisungEmpfangen(double betrag, String vonName, long vonKontonr, long vonBlz, String verwendungszweck) {
//        if (betrag < 0 || Double.isNaN(betrag) || vonName == null || verwendungszweck == null)
//            throw new IllegalArgumentException("Parameter fehlerhaft");
//        setKontostand(getKontostand() + betrag);
//    }
//
//    @Override
//    public String toString() {
//        String ausgabe = "-- GIROKONTO --" + System.lineSeparator() +
//                super.toString()
//                + "Dispo: " + this.dispo + System.lineSeparator();
//        return ausgabe;
//    }
//
//    @Override
//    public boolean abheben(double betrag) throws GesperrtException {
//        if (betrag < 0 || Double.isNaN(betrag)) {
//            throw new IllegalArgumentException("Betrag ungültig");
//        }
//        if (this.isGesperrt())
//            throw new GesperrtException(this.getKontonummer());
//        if (getKontostand() - betrag >= -dispo) {
//            setKontostand(getKontostand() - betrag);
//            return true;
//        } else
//            return false;
//    }
//
//    /**
//     * ändert Dispo und Kontostand in neue Währung
//     *
//     * @param neu neue Währung in die gewechselt wird
//     */
//    @Override
//    public void waehrungswechsel(Waehrung neu) {
//        if (super.getAktuelleWaehrung() != neu) {
//            dispo = super.getAktuelleWaehrung()
//                    .waehrungInWaehrungUmrechnen(dispo, neu);
//        }
//        super.waehrungswechsel(neu);
//    }
//}


/////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////
// FOR MOCKING


/**
 * Ein Girokonto
 * @author Doro
 *
 */
public class Girokonto extends Konto implements Ueberweisungsfaehig{

    /**
     * erzeugt ein leeres, nicht gesperrtes Standard-Girokonto
     * von Herrn MUSTERMANN
     */
    public Girokonto()
    {
    }

    /**
     * erzeugt ein Girokonto mit den angegebenen Werten
     * @param inhaber Kontoinhaber
     * @param nummer Kontonummer
     * @param dispo Dispo
     * @throws IllegalArgumentException wenn der inhaber null ist
     * 									oder der angegebene dispo negativ bzw. NaN ist
     */
    public Girokonto(Kunde inhaber, long nummer, double dispo)
    {
    }

    /**
     * liefert den Dispo
     * @return Dispo von this
     */
    public double getDispo() {
        System.out.println("nicht aufrufen!");
        return 0;
    }

    /**
     * setzt den Dispo neu
     * @param dispo muss größer sein als 0
     * @throw IllegalArgumentException wenn dispo negativ bzw. NaN ist
     */
    public void setDispo(double dispo) {
        System.out.println("nicht aufrufen!");
    }

    /**
     * vermindert den Kontostand um den angegebenen Betrag, falls das Konto nicht gesperrt ist.
     * Am Empfängerkonto wird keine Änderung vorgenommen, da davon ausgegangen wird, dass dieses sich
     * bei einer anderen Bank befindet.
     * @param betrag double
     * @param empfaenger String
     * @param nachKontonr int
     * @param nachBlz int
     * @param verwendungszweck String
     * @return boolean
     * @throws GesperrtException wenn das Konto gesperrt ist
     * @throws IllegalArgumentException wenn der Betrag negativ bzw. NaN ist oder
     * 									empfaenger oder verwendungszweck null ist
     */
    public boolean ueberweisungAbsenden(double betrag,
                                        String empfaenger, long nachKontonr,
                                        long nachBlz, String verwendungszweck)
            throws GesperrtException
    {
        System.out.println("nicht aufrufen!");
        return false;
    }

    /**
     * erhöht den Kontostand um den angegebenen Betrag
     * @param betrag double
     * @param vonName String
     * @param vonKontonr int
     * @param vonBlz int
     * @param verwendungszweck String
     *      * @throws IllegalArgumentException wenn der Betrag negativ bzw. NaN ist oder
     * 									vonName oder verwendungszweck null ist
     */
    public void ueberweisungEmpfangen(double betrag, String vonName, long vonKontonr, long vonBlz, String verwendungszweck)
    {
        System.out.println("nicht aufrufen!");
    }

    @Override
    public String toString()
    {
        System.out.println("nicht aufrufen!");
        return null;
    }

    @Override
    public boolean abheben(double betrag) throws GesperrtException{
        System.out.println("nicht aufrufen!");
        return false;
    }



}
