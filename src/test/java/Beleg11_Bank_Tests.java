import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.time.LocalDate;

/**
 * Testen der Bank fuer Beleg 11
 *  Ich habe es nicht mit Mocks hinbekommen und kontrolliere selbst die Ausgabe in der Konsole
 */
public class Beleg11_Bank_Tests {

    Kunde kunde = new Kunde();
    Girokonto girokonto;
//    Beobachter beobachter = Mockito.mock(Beobachter.class);

    @BeforeEach
    public void setUp() {
        girokonto = new Girokonto(kunde, 1, 500);
    }

    @Test
    public void beobachterKunde_propertyChange_Einzahlen() {
//        girokonto.anmelden(beobachter);
        girokonto.anmelden(kunde);
        girokonto.einzahlen(500);
    }

    @Test
    public void beobachterKunde_propertyChange_Auszahlen() throws GesperrtException {
        girokonto.anmelden(kunde);
        girokonto.einzahlen(500);
        girokonto.abheben(100);
    }

    @Test
    public void beobachterKunde_propertyChange_Dispo() {
        girokonto.anmelden(kunde);
        girokonto.setDispo(100);
    }

    @Test
    public void beobachterKunde_propertyChange_Sperren() {
        girokonto.anmelden(kunde);
        girokonto.sperren();
    }

    @Test
    public void beobachterKunde_propertyChange_KundeNeu() throws GesperrtException {
        girokonto.anmelden(kunde);
        Kunde k2 = new Kunde("Test", "Neu", "aaa", LocalDate.of(1999, 10, 10));
        girokonto.setInhaber(k2);
    }

}
