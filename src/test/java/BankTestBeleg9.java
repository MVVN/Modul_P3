import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BankTestBeleg9 {
    Bank bank, copiedBank;
    Kunde kunde, kunde2;
    long giroKunde1, giroKunde2, sparKunde1, sparKunde2;

    @BeforeEach
    public void setUp() {
        bank = new Bank(12345);
        kunde = new Kunde("Mumen", "Raidaa", "Z-City", LocalDate.parse("1993-10-10"));
        kunde2 = new Kunde("Über", "Weisung", "Geldstr. 2", LocalDate.parse("1911-11-11"));

        giroKunde1 = bank.girokontoErstellen(kunde);
        sparKunde1 = bank.sparbuchErstellen(kunde);
        giroKunde2 = bank.girokontoErstellen(kunde2);
    }

    @Test
    public void copied_bank_is_identical() {
        copiedBank = bank.clone();
        Assertions.assertEquals(bank.getBankleitzahl(), copiedBank.getBankleitzahl());
        Assertions.assertEquals(bank.getAlleKontonummern(), copiedBank.getAlleKontonummern());
    }

    @Test
    public void copied_bank_is_independent() throws KontonummerNichtGefundenException {
        copiedBank = bank.clone();
        sparKunde2 = bank.sparbuchErstellen(kunde2);
        bank.geldEinzahlen(giroKunde1, 5);
        Assertions.assertEquals(5, bank.getKontostand(giroKunde1));
        Assertions.assertEquals(0, copiedBank.getKontostand(giroKunde1));
        Assertions.assertTrue(bank.getAlleKontonummern().size() > copiedBank.getAlleKontonummern().size());
    }

    @Test
    public void copied_bank_is_independent2() {
        copiedBank = bank.clone();
        List<Long> alleKonten = bank.getAlleKontonummern();
        alleKonten.forEach(konto -> {
            try {
                bank.geldEinzahlen(konto, 500);
            } catch (KontonummerNichtGefundenException e) {
                e.printStackTrace();
            }
        });
        alleKonten.forEach(konto -> {
            try {
                Assertions.assertTrue(bank.getKontostand(konto) > copiedBank.getKontostand(konto));
                Assertions.assertEquals(0, copiedBank.getKontostand(konto));
            } catch (KontonummerNichtGefundenException e) {
                e.printStackTrace();
            }
        });
    }
}