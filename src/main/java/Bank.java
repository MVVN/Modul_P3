import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * Bank ist eine Klasse, welche in einer Map verschiedene Konten erstellen kann und in einer Map hält
 * Sie ermöglicht Einzahlungen und Auszahlungen und Überweisungen
 */
public class Bank implements Cloneable{

    /**
     * Bankleitzahl der Bank
     */
    private final long bankleitzahl;
    /**
     * kontoMap hält alle Konten der Bank fest und weist sie ihrer eindeutigen Kontonummer zu
     */
    private TreeMap<Long, Konto> kontoMap;
    /**
     * bei Erstellung von Konten wird diesem die nextKontoNummer als Kontonummer zugewiesen
     */
    private long nextKontoNummer;
    private long MIN_KONTONUMMER = 1;
    private long MAX_KONTONUMMER = Long.MAX_VALUE;

    /**
     * Erstellt Bank mit bestimmter Bankleitzahl
     *
     * @param bankleitzahl Bankleitzahl der neu erstellten Bank
     */
    public Bank(long bankleitzahl) {
        if (bankleitzahl < 0) {
            throw new IllegalArgumentException("Bankleitzahl kann nicht negativ sein.");
        }
        this.bankleitzahl = bankleitzahl;
        this.kontoMap = new TreeMap<Long, Konto>();
        this.nextKontoNummer = MIN_KONTONUMMER;
    }

    /**
     * @return Bankleitzahl der Bank
     */
    public long getBankleitzahl() {
        return bankleitzahl;
    }

    /**
     * Erstellt ein Girokonto und fügt es zu kontoMap hinzu
     * nextKontoNummer wird um 1 erhöht, somit hat jedes weitere Konto eine eindeutige Nummer
     *
     * @param inhaber Kunde, für der das Girokonto angelegt wird
     * @return neue Kontonummer des angelegten Kontos
     */
    public long girokontoErstellen(Kunde inhaber) {
        Girokonto gk = new Girokonto(inhaber, nextKontoNummer, 200);
        kontoMap.put(gk.getKontonummer(), gk);
        nextKontoNummer++;
        return gk.getKontonummer();
    }

    /**
     * Erstellt ein Sparbuch und fügt es zu kontoMap hinzu
     * nextKontoNummer wird um 1 erhöht, somit hat jedes weitere Konto eine eindeutige Nummer
     *
     * @param inhaber Kunde, für der das Sparbuch angelegt wird
     * @return neue Kontonummer des angelegten Kontos
     */
    public long sparbuchErstellen(Kunde inhaber) {
        Sparbuch sb = new Sparbuch(inhaber, nextKontoNummer);
        kontoMap.put(sb.getKontonummer(), sb);
        nextKontoNummer++;
        return sb.getKontonummer();
    }

    /**
     * Erstellt einen String mit Kontonummer und deren Kontostand von allen in der Bank vorhanden Konten
     *
     * @return String aller Konten in der Bank
     */
    public String getAlleKonten() {
        StringBuilder sb = new StringBuilder();
        for (Konto k : kontoMap.values()) {
            sb.append(k.getKontonummerFormatiert() + ", " + k.getKontostandFormatiert() + " ; " + System.lineSeparator());
        }
        return sb.toString();
    }

    /**
     * Erstellt Liste mit den Kontonummern aller Konten der Bank
     *
     * @return Liste mit allen Kontonummern
     */
    public List<Long> getAlleKontonummern() {
        return new LinkedList<>(kontoMap.keySet());
    }

    /**
     * Abheben eines Betrags vom Konto mit gewählter Kontonummer
     *
     * @param von    Kontonummer des zu belastenen Kontos
     * @param betrag Betrag, der vom Konto abgehoben werden soll
     * @return true bei Erfolg, ansonsten false
     * @throws GesperrtException                 wenn Konto gesperrt ist
     * @throws IllegalArgumentException          wenn Betrag negativ ist
     * @throws KontonummerNichtGefundenException wenn die Kontonummer in der Bank nicht gefunden wurde
     */
    public boolean geldAbheben(long von, double betrag) throws GesperrtException, IllegalArgumentException, KontonummerNichtGefundenException {
        if (!kontoMap.containsKey(von)) {
            throw new KontonummerNichtGefundenException(von);
        }
        return kontoMap.get(von).abheben(betrag);
    }

    /**
     * Einzahlen eines Betrages auf ein Konto
     *
     * @param auf    Kontonummer des Kontos, auf die der Betrag eingezahlt werden soll
     * @param betrag der Betrag, der auf das Konto eingezahlt werden soll
     * @throws IllegalArgumentException          wenn Betrag negativ ist
     * @throws KontonummerNichtGefundenException wenn Kontonummer nicht in Bank gefunden wurde
     */
    public void geldEinzahlen(long auf, double betrag) throws IllegalArgumentException, KontonummerNichtGefundenException {
        if (!kontoMap.containsKey(auf)) {
            throw new KontonummerNichtGefundenException(auf);
        }
        kontoMap.get(auf).einzahlen(betrag);
    }

    /**
     * Entfernt, sofern vorhanen, ein Konto mit gegebener Nummer aus der Bank
     *
     * @param nummer Kontonummer des zu löschenden Kontos
     * @return true bei Erfolg; false, wenn Kontonummer nicht gefunden
     */
    public boolean kontoLoeschen(long nummer) {
        if (!kontoMap.containsKey(nummer)) {
            return false;
        }
        kontoMap.remove(nummer);
        return true;
    }

    /**
     * Erhalte Information über den Kontostand eines Kontos
     *
     * @param nummer Kontonummer des Kontos
     * @return Kontostand des angegebenen Kontos
     * @throws KontonummerNichtGefundenException wenn Konto nicht gefunden
     */
    public double getKontostand(long nummer) throws KontonummerNichtGefundenException {
        if (!kontoMap.containsKey(nummer)) {
            throw new KontonummerNichtGefundenException(nummer);
        }
        return kontoMap.get(nummer).getKontostand();
    }

    /**
     * überweist Geld von einem Konto auf ein anderes
     *
     * @param vonKontonr       das Konto, von welchem die Überweisung ausgeht
     * @param nachKontonr      das Konto, welches die Überweisung erhält
     * @param betrag           der zu überweisende Betrag
     * @param verwendungszweck Verwendungszweck
     * @return true bei Erfolg, ansonsten false
     * @throws GesperrtException wenn zu belastenes Konto gesperrt ist
     */
    public boolean geldUeberweisen(long vonKontonr, long nachKontonr, double betrag, String verwendungszweck) throws GesperrtException {
        if (!kontoMap.containsKey(vonKontonr) || !kontoMap.containsKey(nachKontonr)) {
            return false;
        }
        Konto senderKonto = kontoMap.get(vonKontonr);
        Konto empfaengerKonto = kontoMap.get(nachKontonr);

        if (!Ueberweisungsfaehig.class.isAssignableFrom(senderKonto.getClass()) || !Ueberweisungsfaehig.class.isAssignableFrom(empfaengerKonto.getClass())) {
            return false;
        }
        Ueberweisungsfaehig sender = (Ueberweisungsfaehig) senderKonto;
        Ueberweisungsfaehig empfaenger = (Ueberweisungsfaehig) empfaengerKonto;

        if (!sender.ueberweisungAbsenden(betrag,
                empfaengerKonto.getInhaber().getName(),
                nachKontonr, getBankleitzahl(), verwendungszweck)) {
            return false;
        }
        empfaenger.ueberweisungEmpfangen(betrag,
                senderKonto.getInhaber().getName(),
                vonKontonr, getBankleitzahl(), verwendungszweck);
        return true;
    }

    /**
     * sperrt alle Konten mit negativen Kontostand
     */
    public void pleitegeierSperren() {
        kontoMap.values().stream()
                .filter(konto -> konto.getKontostand() < 0)
                .forEach(Konto::sperren);
    }

    /**
     * sammelt alle Kunden, die mindestens bestimmten Kontostand haben
     *
     * @param minimum der Kontostand, den die Kunden mindestens haben muessen
     * @return Liste aller Kunden mit Kontostand > minimum
     */
    public List<Kunde> getKundenMitVollemKonto(double minimum) {
        return kontoMap.values().stream()
                .filter(konto -> konto.getKontostand() >= minimum)
                .map(Konto::getInhaber)
                .collect(Collectors.toList());
    }

    /**
     * Ermittelt Namen + Geburtstag aller Kunden der Bank
     * doppelte Namen werden aussortiert und die Liste wird nach Geburtsdaten sortiert
     *
     * @return String von Kunden + Geburtsdaten gefiltert + sortiert
     */
    public String getKundengeburtstage() {
        return kontoMap.values().stream()
                .map(Konto::getInhaber)
                .distinct()
                .sorted(Comparator.comparing(Kunde::getGeburtstag))
                .map(kunde -> new StringBuffer(kunde.getName())
                        .append(" ")
                        .append(kunde.getGeburtstag()))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    /**
     * gibt die unbesetzten Kontunmmern als Liste zurück
     * kontrolliert dabei zwischen Minimum und Maximum der Kontonummern
     * @return Liste der unbesetzten Kontonummern in der Bank
     */
    public List<Long> getKontonummernLuecken() {
        return LongStream.range(MIN_KONTONUMMER, MAX_KONTONUMMER)
                .filter(x -> !kontoMap.containsKey(x))
                .boxed() // verwandelt long zu Long-Objekten, damit in Liste speicherbar
                .collect(Collectors.toList());
    }

//    /**
//     * Sucht alle Kunden, von denen alle Kontenbetraege ueber einem Minimum sind
//     * @param minimum das Minimum, welches ueberschritten werden muss
//     * @return Liste von reichen Kunden
//     */
//    public List<Kunde> getAlleReichenKunden(double minimum) {
//
//    }

    /**
     * gibt eine eigenständige Kopie der Bank zurück
     * Zuustand der zu klonenden Bank wird bis zum Zeitpunkt des Klonens übernommen
     * @return Kopie der Bank
     */
/*    public Bank clone() {

    }*/

    public long mockEinfuegen(Konto k) {
        kontoMap.put(nextKontoNummer, k);
        nextKontoNummer++;
        return nextKontoNummer - 1;
    }
}
