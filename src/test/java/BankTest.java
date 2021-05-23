import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InOrder;
import org.mockito.Mockito;

import java.time.LocalDate;

/**
 * Testen der Bankklasse mit Hilfe von Mocking
 */
class BankTest {
    Bank bank;
    Kunde kunde, kunde2;
    Girokonto girokonto, girokonto2;
    Sparbuch sparbuch;
    long giroNummer, sparNummer, giroNummer2;

    /**
     * vor jedem Test werden Bank und Kunde erstellt und Mockobjekte vorbereitet
     *
     * @throws GesperrtException
     */
    @BeforeEach
    public void setUp() throws GesperrtException {
        bank = new Bank(12345);
        kunde = new Kunde("Mumen", "Raidaa", "Z-City", LocalDate.parse("1993-10-10"));
        kunde2 = new Kunde("Über", "Weisung", "Geldstr. 2", LocalDate.parse("1911-11-11"));
        girokonto = Mockito.mock(Girokonto.class);
        sparbuch = Mockito.mock(Sparbuch.class);
        girokonto2 = Mockito.mock(Girokonto.class);

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

        // Mocking eines Girokontos (alle Methoden mit Rückgabewert) = zum Überweisung testen
        // nicht gesperrt und abheben + überweisungAbsenden geben true zurück
        Mockito.when(girokonto2.getDispo()).thenReturn(100.0);
        Mockito.when(girokonto2.getKontostand()).thenReturn(100.0);
        Mockito.when(girokonto2.getInhaber()).thenReturn(kunde2);
        Mockito.when(girokonto2.getKontostandFormatiert()).thenReturn("Girokonto - Kontostand formatiert: 1000,00 Euro");
        Mockito.when(girokonto2.getKontonummerFormatiert()).thenReturn("Girokonto - Kontonummer formatiert: " + giroNummer2);
        Mockito.when(girokonto2.getGesperrtText()).thenReturn("Girokonto - gesperrt!");
        Mockito.when(girokonto2.toString()).thenReturn("Ein Girokonto - String");
        Mockito.when(girokonto2.isGesperrt()).thenReturn(false);
        Mockito.when(girokonto2.abheben(ArgumentMatchers.anyDouble())).thenReturn(true);
        Mockito.when(girokonto2.ueberweisungAbsenden(ArgumentMatchers.anyDouble(), ArgumentMatchers.anyString(), ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong(), ArgumentMatchers.anyString())).thenReturn(true);

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
    public void newCreatedBank_HasNoAccounts() {
        Assertions.assertEquals("", bank.getAlleKonten());
        Assertions.assertTrue(bank.getAlleKontonummern().isEmpty());
        Assertions.assertEquals(12345, bank.getBankleitzahl());
    }

    /**
     * Bank mit 2 Konten; 1. gelöscht und nicht mehr zugreifbar und nicht mehr in Liste
     */
    @Test
    public void bankLoeschtKonto_Erfolgreich() {
        giroNummer = bank.mockEinfuegen(girokonto);
        sparNummer = bank.mockEinfuegen(sparbuch);
        Mockito.when(girokonto.getKontonummer()).thenReturn(giroNummer);
        Mockito.when(sparbuch.getKontonummer()).thenReturn(sparNummer);

        Assertions.assertEquals(2, bank.getAlleKontonummern().size());
        Assertions.assertEquals(1, bank.getAlleKontonummern().get(0));
        Assertions.assertEquals(2, bank.getAlleKontonummern().get(1));

        bank.kontoLoeschen(1);
        Assertions.assertEquals(2, bank.getAlleKontonummern().get(0));

        Assertions.assertThrows(KontonummerNichtGefundenException.class, () -> bank.geldEinzahlen(1, 100));
    }

    @Test
    public void bankGetAlleKonten_Funktioniert() {
        giroNummer = bank.mockEinfuegen(girokonto);
        Mockito.when(girokonto.getKontonummer()).thenReturn(giroNummer);
        Assertions.assertEquals("Girokonto - Kontonummer formatiert: 0, Girokonto - Kontostand formatiert: 1000,00 Euro ; " + System.lineSeparator(),
                bank.getAlleKonten());
        sparNummer = bank.mockEinfuegen(sparbuch);
        Mockito.when(sparbuch.getKontonummer()).thenReturn(sparNummer);
        Assertions.assertEquals("Girokonto - Kontonummer formatiert: 0, Girokonto - Kontostand formatiert: 1000,00 Euro ; " + System.lineSeparator() +
                        "Sparbuch - Kontonummer formatiert: 0, Sparbuch - Kontostand formatiert: 1200,00 Euro ; " + System.lineSeparator(),
                bank.getAlleKonten());
    }

    /**
     * simuliert Erstellen und Nutzen von Girokonto mittels mockEinfuegen
     */
    @Test
    public void bankGirokonto_Zugreifbar() {
        giroNummer = bank.mockEinfuegen(girokonto);
        Mockito.when(girokonto.getKontonummer()).thenReturn(giroNummer);
        Assertions.assertEquals(1, bank.getAlleKontonummern().size());
        Assertions.assertEquals(1, bank.getAlleKontonummern().get(0));
        try {
            bank.geldEinzahlen(1, 100);
            bank.getKontostand(1);
            bank.geldAbheben(1, 100);
        } catch (KontonummerNichtGefundenException | GesperrtException e) {
            Assertions.fail("Fehler.");
        }
        Mockito.verify(girokonto).einzahlen(100);
        Mockito.verify(girokonto).getKontostand();
        try {
            Mockito.verify(girokonto).abheben(100);
        } catch (GesperrtException e) {
            Assertions.fail("Fehler.");
        }
    }

    /**
     * Testet Zugriff auf Sparbuch in Bank
     */
    @Test
    public void bankSparbuch_Zugreifbar() {
        sparNummer = bank.mockEinfuegen(sparbuch);
        Mockito.when(sparbuch.getKontonummer()).thenReturn(sparNummer);
        Assertions.assertEquals(1, bank.getAlleKontonummern().size());
        Assertions.assertEquals(1, bank.getAlleKontonummern().get(0));
        try {
            bank.geldEinzahlen(1, 100);
            bank.getKontostand(1);
            bank.geldAbheben(1, 100);
        } catch (KontonummerNichtGefundenException | GesperrtException e) {
            Assertions.fail("Fehler.");
        }
        Mockito.verify(sparbuch).einzahlen(100);
        Mockito.verify(sparbuch).getKontostand();
        try {
            Mockito.verify(sparbuch).abheben(100);
        } catch (GesperrtException e) {
            Assertions.fail("Fehler.");
        }
    }

    /**
     * testes Überweisung zwischen zwei Girokonten
     */
    @Test
    public void bankUeberweisung_GiroToGiro() {
        giroNummer = bank.mockEinfuegen(girokonto);
        giroNummer2 = bank.mockEinfuegen(girokonto2);
        Mockito.when(girokonto.getKontonummer()).thenReturn(giroNummer);
        Mockito.when(girokonto2.getKontonummer()).thenReturn(giroNummer2);
        Assertions.assertEquals(2, bank.getAlleKontonummern().size());
        Assertions.assertEquals(2, bank.getAlleKontonummern().get(1));

        InOrder order = Mockito.inOrder(girokonto, girokonto2);

        try {
            Assertions.assertTrue(bank.geldUeberweisen(
                    girokonto.getKontonummer(),
                    girokonto2.getKontonummer(),
                    99,
                    "Überweisungstest"));
        } catch (GesperrtException e) {
            Assertions.fail("Fehler.");
        }
        try {
            order.verify(girokonto).ueberweisungAbsenden(
                    99, girokonto2.getInhaber().getName(),
                    bank.getAlleKontonummern().get(1),
                    bank.getBankleitzahl(), "Überweisungstest"
            );
            order.verify(girokonto2).ueberweisungEmpfangen(
                    99, girokonto.getInhaber().getName(),
                    bank.getAlleKontonummern().get(0), bank.getBankleitzahl(),
                    "Überweisungstest"
            );
        } catch (GesperrtException e) {
            Assertions.fail("Fehler.");
        }
    }

    /**
     * Überweisung returned false, wenn Sender nicht genug Geld hat
     */
    @Test
    public void bankUeberweisung_SenderNichtGenugCash() {
        giroNummer = bank.mockEinfuegen(girokonto);
        giroNummer2 = bank.mockEinfuegen(girokonto2);
        Mockito.when(girokonto.getKontonummer()).thenReturn(giroNummer);
        try {
            Mockito.when(girokonto.ueberweisungAbsenden(ArgumentMatchers.anyDouble(), ArgumentMatchers.anyString(), ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong(), ArgumentMatchers.anyString())).thenReturn(false);
        } catch (GesperrtException e) {
            Assertions.fail("Fehler.");
        }
        Mockito.when(girokonto2.getKontonummer()).thenReturn(giroNummer2);
        Assertions.assertEquals(2, bank.getAlleKontonummern().size());
        Assertions.assertEquals(2, bank.getAlleKontonummern().get(1));

        try {
            Assertions.assertFalse(bank.geldUeberweisen(
                    girokonto.getKontonummer(),
                    girokonto2.getKontonummer(),
                    99,
                    "Überweisungstest"));
        } catch (GesperrtException e) {
            Assertions.fail("Fehler.");
        }
        Mockito.verify(girokonto).getKontonummer();

        try {
            Mockito.verify(girokonto).ueberweisungAbsenden(
                    99, girokonto2.getInhaber().getName(),
                    giroNummer2, bank.getBankleitzahl(), "Überweisungstest"
            );
        } catch (GesperrtException e) {
            Assertions.fail("Fehler.");
        }
        Mockito.verifyNoMoreInteractions(girokonto);
    }

    /**
     * Überweisung mit Sparbuch sollte false zurückgeben
     */
    @Test
    public void bankUeberweisung_GiroToSparbuch_ReturnFalse() {
        giroNummer = bank.mockEinfuegen(girokonto);
        sparNummer = bank.mockEinfuegen(sparbuch);
        Mockito.when(girokonto.getKontonummer()).thenReturn(giroNummer);
        Mockito.when(sparbuch.getKontonummer()).thenReturn(sparNummer);

        Assertions.assertEquals(2, bank.getAlleKontonummern().size());
        Assertions.assertEquals(1, bank.getAlleKontonummern().get(0));
        Assertions.assertEquals(2, bank.getAlleKontonummern().get(1));

        try {
            Assertions.assertFalse(bank.geldUeberweisen(girokonto.getKontonummer(),
                    sparbuch.getKontonummer(),
                    99, "Überweisungstest"));
        } catch (GesperrtException e) {
            Assertions.fail("Fehler.");
        }
        Mockito.verify(girokonto).getKontonummer();
        Mockito.verify(sparbuch).getKontonummer();
        Mockito.verifyNoMoreInteractions(girokonto, sparbuch);
    }

    /**
     * Überweisung zu nicht vorhandenem Konto returns false
     */
    @Test
    public void bankUeberweisung_GiroToEmpty() {
        giroNummer = bank.mockEinfuegen(girokonto);
        Mockito.when(girokonto.getKontonummer()).thenReturn(giroNummer);
        Assertions.assertEquals(1, bank.getAlleKontonummern().size());
        Assertions.assertEquals(1, bank.getAlleKontonummern().get(0));

        try {
            Assertions.assertFalse(bank.geldUeberweisen(giroNummer, giroNummer + 1, 99, "Überweisungtest"));
        } catch (GesperrtException e) {
            Assertions.fail("Fehler.");
        }
        Mockito.verifyNoInteractions(girokonto);
    }

    /**
     * Überweisung mit GesperrtException
     */
    @Test
    public void bankUeberweisung_KontoGesperrtException() {
        giroNummer = bank.mockEinfuegen(girokonto);
        giroNummer2 = bank.mockEinfuegen(girokonto2);
        Mockito.when(girokonto.getKontonummer()).thenReturn(giroNummer);
        Mockito.when(girokonto2.getKontonummer()).thenReturn(giroNummer2);
        try {
            Mockito.doThrow(new GesperrtException(girokonto.getKontonummer())).when(girokonto).ueberweisungAbsenden(ArgumentMatchers.anyDouble(), ArgumentMatchers.anyString(),
                    ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong(), ArgumentMatchers.anyString());
        } catch (GesperrtException e) {
            Assertions.fail("Fehler.");
        }
        try {
            bank.geldUeberweisen(giroNummer, giroNummer2, 99, "Gesperrt");
            Assertions.fail("Exception sollte geworfen werden!");
        } catch (GesperrtException e) {
        }
        Mockito.verify(girokonto2).getInhaber();
        Mockito.verifyNoMoreInteractions(girokonto2);
    }

    /**
     * Einzahlen ungültiger Betrag funktioniert nicht - wirft IllegalArgumentException
     */
    @Test
    public void bankEinzahlen_IllegalArgumentException() {
        giroNummer = bank.mockEinfuegen(girokonto);
        Mockito.when(girokonto.getKontonummer()).thenReturn(giroNummer);
        try {
            Mockito.doThrow(new IllegalArgumentException()).when(girokonto).einzahlen(ArgumentMatchers.anyDouble());
            bank.geldEinzahlen(giroNummer, 99);
            Assertions.fail("Exception sollte geworfen werden!");
        } catch (IllegalArgumentException | KontonummerNichtGefundenException e) {
        }
    }

    /**
     * Abheben ungültiger Betrag funktioniert nicht - wirft IllegalArgumentException
     */
    @Test
    public void bankAbheben_IllegalArgumentException() {
        giroNummer = bank.mockEinfuegen(girokonto);
        Mockito.when(girokonto.getKontonummer()).thenReturn(giroNummer);
        try {
            Mockito.doThrow(new IllegalArgumentException()).when(girokonto).abheben(ArgumentMatchers.anyDouble());
            bank.geldAbheben(giroNummer, 99);
            Assertions.fail("Exception sollte geworfen werden!");
        } catch (IllegalArgumentException | KontonummerNichtGefundenException | GesperrtException e) {
        }
    }

    /**
     * Abheben von gesperrtem Konto funktioniert nicht - wirft GesperrtException
     */
    @Test
    public void bankAbheben_GesperrtException() {
        giroNummer = bank.mockEinfuegen(girokonto);
        Mockito.when(girokonto.getKontonummer()).thenReturn(giroNummer);
        try {
            Mockito.doThrow(new GesperrtException(giroNummer)).when(girokonto).abheben(ArgumentMatchers.anyDouble());
            bank.geldAbheben(giroNummer, 99);
            Assertions.fail("Exception sollte geworfen werden!");
        } catch (IllegalArgumentException | KontonummerNichtGefundenException | GesperrtException e) {
        }
    }

}