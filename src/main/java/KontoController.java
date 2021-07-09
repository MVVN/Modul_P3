import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controller von Konto; Brücke von View zu Model
 */
public class KontoController extends Application {

    Kunde kunde = new Kunde();

    private Girokonto giro = new Girokonto(kunde, 123456, 500);
    private KontoOberflaeche kontoOberflaeche;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Girokonto:");
        kontoOberflaeche = new KontoOberflaeche(this, giro);
        Scene scene = new Scene(kontoOberflaeche, 500, 500);
        stage.setScene(scene);
        stage.show();
    }

    protected void einzahlen(double betrag) {
        try {
            giro.einzahlen(betrag);
        } catch (IllegalArgumentException e) {
            kontoOberflaeche.displayMessage("Betrag ist negativ!");
            return;
        }
        kontoOberflaeche.displayMessage(betrag + " erfolgreich eingezahlt.");
    }

    protected void abheben(double betrag) {
        try {
            giro.abheben(betrag);
        } catch (GesperrtException | IllegalArgumentException e) {
            kontoOberflaeche.displayMessage("Abhebung nicht durchgeführt.");
            return;
        }
        kontoOberflaeche.displayMessage(betrag + " erfolgreich abgehoben.");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
