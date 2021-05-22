import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.time.LocalDate;

/**
 * Testen der Bankklasse mit Hilfe von Mocking
 */
class BankTest {

    Bank bank;
    Kunde kunde;
    Girokonto girokonto;
    Sparbuch sparbuch;
    long giroNummer, sparNummer;

    /**
     * vor jedem Test werden Bank und Kunde erstellt und Mockobjekte vorbereitet
     *
     * @throws GesperrtException
     */
    @BeforeEach
    public void setUp() throws GesperrtException {
        bank = new Bank(12345);
        kunde = new Kunde("Mumen", "Raidaa", "Z-City", LocalDate.parse("1993-10-10"));
        girokonto = Mockito.mock(Girokonto.class);
        sparbuch = Mockito.mock(Sparbuch.class);

        // Mocking eines Girokontos (alle Methoden mit Rückgabewert)
        // nicht gesperrt und abheben + überweisungAbsenden geben true zurück
        Mockito.when(girokonto.getDispo()).thenReturn(500.0);
        Mockito.when(girokonto.getKontostand()).thenReturn(1000.0);
        Mockito.when(girokonto.getInhaber()).thenReturn(kunde);
        Mockito.when(girokonto.getKontostandFormatiert()).thenReturn("Girokonto - Kontostand formatiert: 1000,00 Euro");
        Mockito.when(girokonto.getKontonummerFormatiert()).thenReturn("Girokonto - Kontonummer formatiert: " + giroNummer);
        Mockito.when(girokonto.getGesperrtText()).thenReturn("Girokonto - gesperrt!");
        Mockito.when(girokonto.toString()).thenReturn("Ein Girokonto - String");
        Mockito.when(girokonto.isGesperrt()).thenReturn(false);
        Mockito.when(girokonto.abheben(ArgumentMatchers.anyDouble())).thenReturn(true);
        Mockito.when(girokonto.ueberweisungAbsenden(ArgumentMatchers.anyDouble(), ArgumentMatchers.anyString(), ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong(), ArgumentMatchers.anyString())).thenReturn(true);

        // Mocking eines Sparbuchs (alle Methoden mit Rückgabewert)
        Mockito.when(sparbuch.getInhaber()).thenReturn(kunde);
        Mockito.when(sparbuch.getKontostand()).thenReturn(1200.0);
        Mockito.when(sparbuch.getKontostandFormatiert()).thenReturn("Sparbuch - Kontostand formatiert: 1200,00 Euro");
        Mockito.when(sparbuch.getKontonummerFormatiert()).thenReturn("Sparbuch - Kontonummer formatiert: " + sparNummer);
        Mockito.when(sparbuch.getGesperrtText()).thenReturn("Sparbuch - gesperrt!");
        Mockito.when(sparbuch.toString()).thenReturn("Ein Sparbuch - String");
        Mockito.when(sparbuch.isGesperrt()).thenReturn(false);
        Mockito.when(sparbuch.abheben(ArgumentMatchers.anyDouble())).thenReturn(true);
    }

    /**
     * Testen, ob Konstruktor die Bank richtig anlegt
     */
    @Test
    public void newCreatedBankHasNoAccounts() {
        Assertions.assertEquals("", bank.getAlleKonten());
        Assertions.assertTrue(bank.getAlleKontonummern().isEmpty());
        Assertions.assertEquals(12345, bank.getBankleitzahl());
    }

    /**
     * simuliert Erstellen und Nutzen von Girokonto mittels mockEinfuegen
     */
    @Test
    public void bankGirokontoZugreifbar() {
        giroNummer = bank.mockEinfuegen(girokonto);
        Mockito.when(girokonto.getKontonummer()).thenReturn(giroNummer);
        Assertions.assertEquals(1, bank.getAlleKontonummern().size());
        Assertions.assertEquals(1, bank.getAlleKontonummern().get(0));
        try {
            bank.geldEinzahlen(1, 100);
        } catch (KontonummerNichtGefundenException e) {
            e.printStackTrace();
        }
        Mockito.verify(girokonto).einzahlen(100);

    }


}