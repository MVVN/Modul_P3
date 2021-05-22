//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDate;
//
///**
// * Tests für Klasse Girokonto
// */
//class GirokontoTest {
//
//    LocalDate date = LocalDate.of(2001, 1, 1);
//    Kunde kunde = new Kunde("Testi", "Testorowski", "Testallee", date);
//    Girokonto girokonto = new Girokonto(kunde, 1234567, 500);
//
//    double dispoCheck = 0;
//
//    @BeforeEach
//    public void setUp() {
//        girokonto.setDispo(500);
//        girokonto.setKontostand(0);
//        dispoCheck = 0;
//    }
//
//    /**
//     * EUR zu BGN möglich
//     */
//    @Test
//    void wechsel_waehrung_EUR_zu_BGN() {
//        dispoCheck = girokonto.getDispo() * Waehrung.BGN.getEuroKurs();
//        girokonto.waehrungswechsel(Waehrung.BGN);
//        Assertions.assertEquals(Waehrung.BGN, girokonto.getAktuelleWaehrung());
//        Assertions.assertEquals(dispoCheck, girokonto.getDispo());
//    }
//
//    /**
//     * EUR zu BGN möglich und auch wieder zurück zu EUR
//     */
//    @Test
//    void wechsel_waehrung_EUR_zu_BGN_zu_EUR() {
//        girokonto.waehrungswechsel(Waehrung.BGN);
//        girokonto.waehrungswechsel(Waehrung.EUR);
//        Assertions.assertEquals(Waehrung.EUR, girokonto.getAktuelleWaehrung());
//        Assertions.assertEquals(500, girokonto.getDispo());
//    }
//
//    /**
//     * EUR zu BGN möglich und danach zu LTL
//     */
//    @Test
//    void wechsel_waehrung_EUR_zu_BGN_zu_LTL() {
//        dispoCheck = girokonto.getDispo() * Waehrung.LTL.getEuroKurs();
//        girokonto.waehrungswechsel(Waehrung.BGN);
//        girokonto.waehrungswechsel(Waehrung.LTL);
//        Assertions.assertEquals(Waehrung.LTL, girokonto.getAktuelleWaehrung());
//        Assertions.assertEquals(dispoCheck, girokonto.getDispo());
//    }
//
//    /**
//     * EUR zu LTL möglich
//     */
//    @Test
//    void wechsel_waehrung_EUR_zu_LTL() {
//        dispoCheck = girokonto.getDispo() * Waehrung.LTL.getEuroKurs();
//        girokonto.waehrungswechsel(Waehrung.LTL);
//        Assertions.assertEquals(Waehrung.LTL, girokonto.getAktuelleWaehrung());
//        Assertions.assertEquals(dispoCheck, girokonto.getDispo());
//    }
//
//    /**
//     * EUR zu KM möglich
//     */
//    @Test
//    void wechsel_waehrung_EUR_zu_KM() {
//        dispoCheck = girokonto.getDispo() * Waehrung.KM.getEuroKurs();
//        girokonto.waehrungswechsel(Waehrung.KM);
//        Assertions.assertEquals(Waehrung.KM, girokonto.getAktuelleWaehrung());
//        Assertions.assertEquals(dispoCheck, girokonto.getDispo());
//    }
//
//    /**
//     * EUR zu EUR möglich, alles bleibt gleich
//     */
//    @Test
//    void wechsel_waehrung_EUR_zu_EUR_bleibt_EUR() {
//        girokonto.waehrungswechsel(Waehrung.EUR);
//        Assertions.assertEquals(Waehrung.EUR, girokonto.getAktuelleWaehrung());
//        Assertions.assertEquals(500, girokonto.getDispo());
//    }
//
//    /**
//     * Kontostand formatiert gibt richtige Währung aus(EUR)
//     */
//    @Test
//    void kontostand_formatiert_EUR() {
//        Assertions.assertEquals("      0,00 EUR", girokonto.getKontostandFormatiert());
//    }
//
//    /**
//     * Kontostand formatiert gibt richtige Währung aus (BGN)
//     */
//    @Test
//    void kontostand_formatiert_BGN() {
//        girokonto.waehrungswechsel(Waehrung.BGN);
//        Assertions.assertEquals("      0,00 BGN", girokonto.getKontostandFormatiert());
//    }
//
//    /**
//     * abheben selbe Währung
//     */
//    @Test
//    void abheben_selbe_waehrung_funktioniert() throws GesperrtException {
//        girokonto.abheben(500, Waehrung.EUR);
//        Assertions.assertEquals(-500, girokonto.getKontostand());
//    }
//
//    /**
//     * abheben fremder Währung
//     */
//    @Test
//    void abheben_andere_waehrung_funktioniert() throws GesperrtException {
//        girokonto.abheben(50, Waehrung.BGN);
//        double abhebeBetragEUR = 50 / Waehrung.BGN.getEuroKurs();
//        Assertions.assertEquals(-abhebeBetragEUR, girokonto.getKontostand());
//    }
//
//    /**
//     * abheben überschreitet dispo returned false
//     */
//    @Test
//    void abheben_ueber_dispo_is_false() throws GesperrtException {
//        Assertions.assertFalse(girokonto.abheben(501, Waehrung.EUR));
//    }
//
//    /**
//     * abheben negativen Betrags nicht möglich
//     */
//    @Test
//    void abheben_negativ_error() {
//        Assertions.assertThrows(IllegalArgumentException.class,
//                () -> {girokonto.abheben(-1, Waehrung.EUR);});
//    }
//
//    /**
//     * enzahlen selber währung
//     */
//    @Test
//    void einzahlen_selbe_waehrung_funktioniert() {
//        girokonto.einzahlen(500, Waehrung.EUR);
//        Assertions.assertEquals(500, girokonto.getKontostand());
//    }
//
//    /**
//     * enzahlen fremder währung
//     */
//    @Test
//    void einzahlen_fremde_waehrung_funktioniert() {
//        girokonto.einzahlen(500, Waehrung.BGN);
//        double einzahlBetragEUR = 500 / Waehrung.BGN.getEuroKurs();
//        Assertions.assertEquals(einzahlBetragEUR, girokonto.getKontostand());
//    }
//
//    /**
//     * enzahlen negativer betrag funktioniert nicht
//     */
//    @Test
//    void einzahlen_negativ_error() {
//        Assertions.assertThrows(IllegalArgumentException.class,
//                () -> {girokonto.einzahlen(-1, Waehrung.EUR);});
//    }
//
//}