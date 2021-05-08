import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Bank {

    private final long bankleitzahl;
    private Map<Long, Konto> kontoMap;
    private long nextKontoNummer;

    public Bank(long bankleitzahl) {
        if (bankleitzahl < 0) {
            throw new IllegalArgumentException("Bankleitzahl kann nicht negativ sein.");
        }
        this.bankleitzahl = bankleitzahl;
        this.kontoMap = new TreeMap<Long, Konto>();
        this.nextKontoNummer = 1;
    }

    public long getBankleitzahl() {
        return bankleitzahl;
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
        String alleKonten = "";
        for (Konto k : kontoMap.values()) {
            alleKonten += k.getKontonummerFormatiert() + " " + k.getKontostandFormatiert() + System.lineSeparator();
        }
        return alleKonten;
    }

    public List<Long> getAlleKontonummern() {
        return new LinkedList<>(kontoMap.keySet());
    }

    public boolean geldAbheben(long von, double betrag) {
        if (kontoMap.get(von) == null) {
            System.err.println("Konto unter dieser Nummer nicht gefunden.");
            return false;
        }
        try {
            kontoMap.get(von).abheben(betrag);
        } catch (GesperrtException e) {
            System.err.println("Konto gesperrt.");
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
