import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Bank {

    private final long BANKLEITZAHL;
    private TreeMap<Long, Konto> kontoMap;
    private long nextKontoNummer;

    public Bank(long bankleitzahl) {
        if (bankleitzahl < 0) {
            throw new IllegalArgumentException("Bankleitzahl kann nicht negativ sein.");
        }
        this.BANKLEITZAHL = bankleitzahl;
        this.kontoMap = new TreeMap<Long, Konto>();
        this.nextKontoNummer = 1;
    }

    public long getBANKLEITZAHL() {
        return BANKLEITZAHL;
    }

    public long girokontoErstellen(Kunde inhaber) {
        Girokonto gk = new Girokonto(inhaber, nextKontoNummer, 200);
        kontoMap.put(gk.getKontonummer(), gk);
        nextKontoNummer++;
        return gk.getKontonummer();
    }

    public long sparbuchErstellen(Kunde inhaber) {
        Sparbuch sb = new Sparbuch(inhaber, nextKontoNummer);
        kontoMap.put(sb.getKontonummer(), sb);
        nextKontoNummer++;
        return sb.getKontonummer();
    }

    public String getAlleKonten() {
        StringBuilder sb = new StringBuilder();
        for (Konto k : kontoMap.values()) {
            sb.append(k.getKontonummerFormatiert() + ", " + k.getKontostandFormatiert() + " ; " + System.lineSeparator());
        }
        return sb.toString();
    }

    public List<Long> getAlleKontonummern() {
        return new LinkedList<>(kontoMap.keySet());
    }

    public boolean geldAbheben(long von, double betrag) throws GesperrtException, IllegalArgumentException, KontonummerNotFoundException {
        if (!kontoMap.containsKey(von)) {
            throw new KontonummerNotFoundException(von);
        }
        kontoMap.get(von).abheben(betrag);
        return true;
    }

    public void geldEinzahlen(long auf, double betrag) throws IllegalArgumentException, KontonummerNotFoundException {
        if (!kontoMap.containsKey(auf)) {
            throw new KontonummerNotFoundException(auf);
        }
        kontoMap.get(auf).einzahlen(betrag);
    }

    public boolean kontoLoeschen(long nummer) throws KontonummerNotFoundException {
        if (!kontoMap.containsKey(nummer)) {
            throw new KontonummerNotFoundException(nummer);
        }
        kontoMap.remove(nummer);
        return true;
    }

    public double getKontostand(long nummer) throws KontonummerNotFoundException {
        if (!kontoMap.containsKey(nummer)) {
            throw new KontonummerNotFoundException(nummer);
        }
        return kontoMap.get(nummer).getKontostand();
    }

    public boolean geldUeberweisen(long vonKontonr, long nachKontonr, double betrag, String verwendungszweck) throws KontonummerNotFoundException {
        if (!kontoMap.containsKey(vonKontonr)) {
            System.err.println("Senderkonto nicht gefunden.");
            throw new KontonummerNotFoundException(vonKontonr);
        } else if (!kontoMap.containsKey(nachKontonr)) {
            System.err.println("Empfängerkonto nicht gefunden.");
            throw new KontonummerNotFoundException(nachKontonr);
        }
        Konto senderKonto = kontoMap.get(vonKontonr);
        Konto empfaengerKonto = kontoMap.get(nachKontonr);

        if (!Ueberweisungsfaehig.class.isAssignableFrom(senderKonto.getClass()) || !Ueberweisungsfaehig.class.isAssignableFrom(empfaengerKonto.getClass())) {
            System.err.println("Beide Konten müssen Überweisungsfähig sein.");
            return false;
        }
        Ueberweisungsfaehig sender = (Ueberweisungsfaehig) senderKonto;
        Ueberweisungsfaehig empfaenger = (Ueberweisungsfaehig) empfaengerKonto;

        try {
            sender.ueberweisungAbsenden(betrag,
                    empfaengerKonto.getKontonummerFormatiert(),
                    nachKontonr, getBANKLEITZAHL(), verwendungszweck);
        } catch (GesperrtException e) {
            System.err.println("Senderkonto ist gesperrt. Ueberweisung kann nicht durchgeführt werden.");
            e.printStackTrace();
            return false;
        } finally {
            empfaenger.ueberweisungEmpfangen(betrag,
                    senderKonto.getKontonummerFormatiert(),
                    vonKontonr, getBANKLEITZAHL(), verwendungszweck);
        }
        return true;
    }

}
