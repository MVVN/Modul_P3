package beleg8;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatheTest {

    @Test
    public void funktion1() throws KeinVorzeichenwechselException {
        Assertions.assertEquals(2.2265625, Mathe.nullstelleSchaetzen(0, 20,
                x -> Math.pow(x, 2) - 5));
        Assertions.assertEquals(-2.2265625, Mathe.nullstelleSchaetzen(-20, 0,
                x -> Math.pow(x, 2) - 5));
    }

    @Test
    public void funktion2() throws KeinVorzeichenwechselException {
        Assertions.assertEquals(0.6640625, Mathe.nullstelleSchaetzen(0, 20,
                x -> Math.pow(Math.E, 3 * x) - 7));
    }

    @Test
    public void funktion3() throws KeinVorzeichenwechselException {
        Assertions.assertEquals(5d, Mathe.nullstelleSchaetzen(0, 20,
                x -> (5 - x)/(Math.pow(x, 3) + 2)));
    }

    @Test
    public void funktion4_keineNS() throws KeinVorzeichenwechselException {
        Assertions.assertThrows(KeinVorzeichenwechselException.class, () -> {
            Mathe.nullstelleSchaetzen(-1000, 1000,
                    x -> Math.pow(x, 2) + 1);
        });
    }
}