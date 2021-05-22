///**
// * stellt ein allgemeines Konto dar
// */
//public abstract class Konto implements Comparable<Konto> {
//    /**
//     * der Kontoinhaber
//     */
//    private Kunde inhaber;
//
//    /**
//     * die Kontonummer
//     */
//    private final long nummer;
//
//    /**
//     * der aktuelle Kontostand
//     */
//    private double kontostand;
//
//    /**
//     * Waehrung des Geldes auf dem aktuellen Konto;
//     * initialer Wert ist Euro
//     */
//    private Waehrung waehrung = Waehrung.EUR;
//
//    /**
//     * setzt den aktuellen Kontostand
//     *
//     * @param kontostand neuer Kontostand
//     */
//    protected void setKontostand(double kontostand) {
//        this.kontostand = kontostand;
//    }
//
//    /**
//     * Wenn das Konto gesperrt ist (gesperrt = true), k�nnen keine Aktionen daran mehr vorgenommen werden,
//     * die zum Schaden des Kontoinhabers w�ren (abheben, Inhaberwechsel)
//     */
//    private boolean gesperrt;
//
//    /**
//     * Setzt die beiden Eigenschaften kontoinhaber und kontonummer auf die angegebenen Werte,
//     * der anf�ngliche Kontostand wird auf 0 gesetzt.
//     *
//     * @param inhaber     der Inhaber
//     * @param kontonummer die gew�nschte Kontonummer
//     * @throws IllegalArgumentException wenn der Inhaber null
//     */
//    public Konto(Kunde inhaber, long kontonummer) {
//        if (inhaber == null)
//            throw new IllegalArgumentException("Inhaber darf nicht null sein!");
//        this.inhaber = inhaber;
//        this.nummer = kontonummer;
//        this.kontostand = 0;
//        this.gesperrt = false;
//    }
//
//    /**
//     * setzt alle Eigenschaften des Kontos auf Standardwerte
//     */
//    public Konto() {
//        this(Kunde.MUSTERMANN, 1234567);
//    }
//
//    /**
//     * liefert den Kontoinhaber zur�ck
//     *
//     * @return der Inhaber
//     */
//    public final Kunde getInhaber() {
//        return this.inhaber;
//    }
//
//    /**
//     * setzt den Kontoinhaber
//     *
//     * @param kinh neuer Kontoinhaber
//     * @throws GesperrtException        wenn das Konto gesperrt ist
//     * @throws IllegalArgumentException wenn kinh null ist
//     */
//    public final void setInhaber(Kunde kinh) throws GesperrtException {
//        if (kinh == null)
//            throw new IllegalArgumentException("Der Inhaber darf nicht null sein!");
//        if (this.gesperrt)
//            throw new GesperrtException(this.nummer);
//        this.inhaber = kinh;
//
//    }
//
//    /**
//     * liefert den aktuellen Kontostand
//     *
//     * @return double
//     */
//    public final double getKontostand() {
//        return kontostand;
//    }
//
//    /**
//     * liefert die Kontonummer zur�ck
//     *
//     * @return long
//     */
//    public final long getKontonummer() {
//        return nummer;
//    }
//
//    /**
//     * liefert zur�ck, ob das Konto gesperrt ist oder nicht
//     *
//     * @return true, wenn das Konto gesperrt ist
//     */
//    public final boolean isGesperrt() {   //Getter, aber eben f�r booleschen Wert
//        return gesperrt;
//    }
//
//    /**
//     * Erh�ht den Kontostand um den eingezahlten Betrag.
//     *
//     * @param betrag double
//     * @throws IllegalArgumentException wenn der betrag negativ ist
//     */
//    public void einzahlen(double betrag) {
//        if (betrag < 0 || Double.isNaN(betrag)) {
//            throw new IllegalArgumentException("Falscher Betrag");
//        }
//        setKontostand(getKontostand() + betrag);
//    }
//
//
//    /**
//     * dient rein didaktischen Zwecken, geh�rt hier eigentlich nicht her
//     */
//    public void ausgeben() {
//        System.out.println(this.toString());
//    }
//
//    /**
//     * Mit dieser Methode wird der geforderte Betrag vom Konto abgehoben, wenn es nicht gesperrt ist.
//     *
//     * @param betrag double
//     * @return true, wenn die Abhebung geklappt hat,
//     * false, wenn sie abgelehnt wurde
//     * @throws GesperrtException        wenn das Konto gesperrt ist
//     * @throws IllegalArgumentException wenn der betrag negativ ist
//     */
//    public abstract boolean abheben(double betrag)
//            throws GesperrtException;
//
//    /**
//     * sperrt das Konto, Aktionen zum Schaden des Benutzers sind nicht mehr m�glich.
//     */
//    public final void sperren() {
//        this.gesperrt = true;
//    }
//
//    /**
//     * entsperrt das Konto, alle Kontoaktionen sind wieder m�glich.
//     */
//    public final void entsperren() {
//        this.gesperrt = false;
//    }
//
//
//    /**
//     * liefert eine String-Ausgabe, wenn das Konto gesperrt ist
//     *
//     * @return "GESPERRT", wenn das Konto gesperrt ist, ansonsten ""
//     */
//    public final String getGesperrtText() {
//        if (this.gesperrt) {
//            return "GESPERRT";
//        } else {
//            return "";
//        }
//    }
//
//    /**
//     * liefert die ordentlich formatierte Kontonummer
//     *
//     * @return auf 10 Stellen formatierte Kontonummer
//     */
//    public String getKontonummerFormatiert() {
//        return String.format("%10d", this.nummer);
//    }
//
//    /**
//     * liefert den ordentlich formatierten Kontostand
//     *
//     * @return formatierter Kontostand mit 2 Nachkommastellen und W�hrungssymbol
//     */
//    public String getKontostandFormatiert() {
//        return String.format("%10.2f " + waehrung.name(), this.getKontostand());
//    }
//
//    /**
//     * Vergleich von this mit other; Zwei Konten gelten als gleich,
//     * wen sie die gleiche Kontonummer haben
//     *
//     * @param other das Vergleichskonto
//     * @return true, wenn beide Konten die gleiche Nummer haben
//     */
//    @Override
//    public boolean equals(Object other) {
//        if (this == other)
//            return true;
//        if (other == null)
//            return false;
//        if (this.getClass() != other.getClass())
//            return false;
//        if (this.nummer == ((Konto) other).nummer)
//            return true;
//        else
//            return false;
//    }
//
//    @Override
//    public int hashCode() {
//        return 31 + (int) (this.nummer ^ (this.nummer >>> 32));
//    }
//
//    @Override
//    public int compareTo(Konto other) {
//        if (other.getKontonummer() > this.getKontonummer())
//            return -1;
//        if (other.getKontonummer() < this.getKontonummer())
//            return 1;
//        return 0;
//    }
//
//    /*
//        BELEG 2
//     */
//
//    /**
//     * abheben eines Betrages vom Konto in bestimmter Währung (Währungsbetrag wird in passende Währung umgerechnet und dann abgehoben)
//     *
//     * @param betrag der abgehoben werden soll
//     * @param w      Währung des Betrags
//     * @return true wenn Abheben erfolgreich, false wenn nicht
//     * @throws GesperrtException
//     */
//    public boolean abheben(double betrag, Waehrung w) throws GesperrtException {
//        betrag = w.waehrungInWaehrungUmrechnen
//                (betrag, getAktuelleWaehrung());
//        return abheben(betrag);
//    }
//
//    /**
//     * Einzahlen von Betrag angegeben in Währung w
//     *
//     * @param betrag Betrag als double
//     * @param w      Währung des Betrags
//     * @throws IllegalArgumentException wenn Betrag negativ
//     */
//    public void einzahlen(double betrag, Waehrung w) throws IllegalArgumentException {
//        betrag = w.waehrungInWaehrungUmrechnen(betrag, this.waehrung);
//        this.einzahlen(betrag);
//    }
//
//    /**
//     * getter für Währung
//     *
//     * @return aktuelle Währung
//     */
//    public Waehrung getAktuelleWaehrung() {
//        return waehrung;
//    }
//
//    /**
//     * Wechselt die Währung des Kontos
//     *
//     * @param neu neue Währung in die gewechselt wird
//     */
//    public void waehrungswechsel(Waehrung neu) {
//        kontostand = waehrung.waehrungInWaehrungUmrechnen(kontostand, neu);
//        this.waehrung = neu;
//    }
//
//    /**
//     * Gibt eine Zeichenkettendarstellung der Kontodaten zur�ck.
//     */
//    @Override
//    public String toString() {
//        String ausgabe;
//        ausgabe = "Kontonummer: " + this.getKontonummerFormatiert()
//                + System.getProperty("line.separator");
//        ausgabe += "Inhaber: " + this.inhaber;
//        ausgabe += "Aktueller Kontostand: " + this.kontostand + " " + this.waehrung;
//        ausgabe += this.getGesperrtText() + System.getProperty("line.separator");
//        return ausgabe;
//    }
//}


/////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////
// FOR MOCKING



/**
 * stellt ein allgemeines Konto dar
 */
public abstract class Konto implements Comparable<Konto>
{
    /**
     * setzt den aktuellen Kontostand
     * @param kontostand neuer Kontostand
     */
    protected void setKontostand(double kontostand) {
        System.out.println("nicht aufrufen!");
    }

    /**
     * Setzt die beiden Eigenschaften kontoinhaber und kontonummer auf die angegebenen Werte,
     * der anfängliche Kontostand wird auf 0 gesetzt.
     *
     * @param inhaber Kunde
     * @param kontonummer long
     * @throws IllegalArgumentException wenn der Inhaber null
     */
    public Konto(Kunde inhaber, long kontonummer) {
    }

    /**
     * setzt alle Eigenschaften des Kontos auf Standardwerte
     */
    public Konto() {
    }

    /**
     * liefert den Kontoinhaber zurück
     * @return   Kunde
     */
    public Kunde getInhaber() {
        System.out.println("nicht aufrufen!");
        return null;
    }

    /**
     * setzt den Kontoinhaber
     * @param kinh   neuer Kontoinhaber
     * @throws GesperrtException wenn das Konto gesperrt ist
     * @throws IllegalArgumentException wenn kinh null ist
     */
    public void setInhaber(Kunde kinh) throws GesperrtException{
        System.out.println("nicht aufrufen!");

    }

    /**
     * liefert den aktuellen Kontostand
     * @return   double
     */
    public double getKontostand() {
        System.out.println("nicht aufrufen!");
        return 0;
    }

    /**
     * liefert die Kontonummer zurück
     * @return   long
     */
    public long getKontonummer() {
        System.out.println("nicht aufrufen!");
        return 0;
    }

    /**
     * liefert zurück, ob das Konto gesperrt ist oder nicht
     * @return
     */
    public boolean isGesperrt() {
        System.out.println("nicht aufrufen!");
        return false;
    }

    /**
     * Erhöht den Kontostand um den eingezahlten Betrag.
     *
     * @param betrag double
     * @throws IllegalArgumentException wenn der betrag negativ ist
     */
    public void einzahlen(double betrag) {
        System.out.println("nicht aufrufen!");
    }

    /**
     * Gibt eine Zeichenkettendarstellung der Kontodaten zurück.
     */
    @Override
    public String toString() {
        System.out.println("nicht aufrufen!");
        return null;
    }

    /**
     * Mit dieser Methode wird der geforderte Betrag vom Konto abgehoben, wenn es nicht gesperrt ist.
     *
     * @param betrag double
     * @throws GesperrtException wenn das Konto gesperrt ist
     * @throws IllegalArgumentException wenn der betrag negativ ist
     * @return true, wenn die Abhebung geklappt hat,
     * 		   false, wenn sie abgelehnt wurde
     */
    public abstract boolean abheben(double betrag) throws GesperrtException;

    /**
     * sperrt das Konto, Aktionen zum Schaden des Benutzers sind nicht mehr möglich.
     */
    public void sperren() {
        System.out.println("nicht aufrufen!");
    }

    /**
     * entsperrt das Konto, alle Kontoaktionen sind wieder möglich.
     */
    public void entsperren() {
        System.out.println("nicht aufrufen!");
    }


    /**
     * liefert eine String-Ausgabe, wenn das Konto gesperrt ist
     * @return "GESPERRT", wenn das Konto gesperrt ist, ansonsten ""
     */
    public String getGesperrtText()
    {
        System.out.println("nicht aufrufen!");
        return null;
    }

    /**
     * liefert die ordentlich formatierte Kontonummer
     * @return auf 10 Stellen formatierte Kontonummer
     */
    public String getKontonummerFormatiert()
    {
        System.out.println("nicht aufrufen!");
        return null;
    }

    /**
     * liefert den ordentlich formatierten Kontostand
     * @return formatierter Kontostand mit 2 Nachkommastellen und Währungssymbol €
     */
    public String getKontostandFormatiert()
    {
        System.out.println("nicht aufrufen!");
        return null;
    }

    /**
     * Vergleich von this mit other; Zwei Konten gelten als gleich,
     * wen sie die gleiche Kontonummer haben
     * @param other
     * @return true, wenn beide Konten die gleiche Nummer haben
     */
    @Override
    public boolean equals(Object other)
    {
        System.out.println("nicht aufrufen!");
        return false;
    }

    @Override
    public int hashCode()
    {
        System.out.println("nicht aufrufen!");
        return 0;
    }

    @Override
    public int compareTo(Konto other)
    {
        System.out.println("nicht aufrufen!");
        return 0;
    }

    // Diese Methode sollte hier eigentlich nicht sein!
    public void aufKonsoleAusgeben()
    {
        System.out.println("nicht aufrufen!");
    }
}