import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.awt.event.ActionEvent;

import static java.lang.Double.parseDouble;

/**
 * Eine Oberfläche für ein einzelnes Konto. Man kann einzahlen
 * und abheben und sperren und die Adresse des Kontoinhabers
 * ändern
 * @author Doro
 *
 */
public class KontoOberflaeche extends BorderPane {
    private Konto konto;

    private Text ueberschrift;
    private GridPane anzeige;
    private Text txtNummer;
    /**
     * Anzeige der Kontonummer
     */
    private Text nummer;
    private Text txtStand;
    /**
     * Anzeige des Kontostandes
     */
    private Text stand;
    private Text txtGesperrt;
    /**
     * Anzeige und Änderung des Gesperrt-Zustandes
     */
    private CheckBox gesperrt;
    private Text txtAdresse;
    /**
     * Anzeige und Änderung der Adresse des Kontoinhabers
     */
    private TextArea adresse;
    /**
     * Anzeige von Meldungen über Kontoaktionen
     */
    private Text meldung;
    private HBox aktionen;
    /**
     * Auswahl des Betrags für eine Kontoaktion
     */
    private TextField betrag;
    /**
     * löst eine Einzahlung aus
     */
    private Button einzahlen;
    /**
     * löst eine Abhebung aus
     */
    private Button abheben;

    /**
     * Zum Erstellen der Kontooberfläche
     * @param kontoController Controller des Kontos
     * @param kontoModel Model des Kontos
     */
    public KontoOberflaeche(KontoController kontoController, Konto kontoModel) {
        this.konto = kontoModel;

        ueberschrift = new Text("Ein Konto verändern");
        ueberschrift.setFont(new Font("Sans Serif", 25));
        BorderPane.setAlignment(ueberschrift, Pos.CENTER);
        this.setTop(ueberschrift);

        anzeige = new GridPane();
        anzeige.setPadding(new Insets(20));
        anzeige.setVgap(10);
        anzeige.setAlignment(Pos.CENTER);

        txtNummer = new Text("Kontonummer:");
        txtNummer.setFont(new Font("Sans Serif", 15));
        anzeige.add(txtNummer, 0, 0);
        nummer = new Text();
        nummer.setText(kontoModel.getKontonummerFormatiert());
        nummer.setFont(new Font("Sans Serif", 15));
        GridPane.setHalignment(nummer, HPos.RIGHT);
        anzeige.add(nummer, 1, 0);

        txtStand = new Text("Kontostand:");
        txtStand.setFont(new Font("Sans Serif", 15));
        anzeige.add(txtStand, 0, 1);
        stand = new Text();
        stand.textProperty().bind(kontoModel.kontostandProperty().asString());
        stand.setFont(new Font("Sans Serif", 15));
        GridPane.setHalignment(stand, HPos.RIGHT);
        anzeige.add(stand, 1, 1);

        txtGesperrt = new Text("Gesperrt: ");
        txtGesperrt.setFont(new Font("Sans Serif", 15));
        anzeige.add(txtGesperrt, 0, 2);
        gesperrt = new CheckBox();
        gesperrt.selectedProperty().bindBidirectional(kontoModel.gesperrtProperty());
        GridPane.setHalignment(gesperrt, HPos.RIGHT);
        anzeige.add(gesperrt, 1, 2);

        txtAdresse = new Text("Adresse: ");
        txtAdresse.setFont(new Font("Sans Serif", 15));
        anzeige.add(txtAdresse, 0, 3);
        adresse = new TextArea();
        adresse.setPrefColumnCount(25);
        adresse.setPrefRowCount(2);
        adresse.textProperty().bindBidirectional(kontoModel.getInhaber().adresseProperty());
        GridPane.setHalignment(adresse, HPos.RIGHT);
        anzeige.add(adresse, 1, 3);

        meldung = new Text("Willkommen lieber Benutzer");
        meldung.setFont(new Font("Sans Serif", 15));
        meldung.setFill(Color.RED);
        anzeige.add(meldung, 0, 4, 2, 1);

        this.setCenter(anzeige);

        aktionen = new HBox();
        aktionen.setSpacing(10);
        aktionen.setAlignment(Pos.CENTER);

        betrag = new TextField("100.00");
        aktionen.getChildren().add(betrag);
        einzahlen = new Button("Einzahlen");
//        einzahlen.setOnAction(e -> kontoController.einzahlen(parseDouble(String.valueOf(betrag))));
        einzahlen.setOnAction(e -> kontoController.einzahlen(parseDouble(betrag.getText())));
        aktionen.getChildren().add(einzahlen);
        abheben = new Button("Abheben");
//        abheben.setOnAction(e -> kontoController.abheben(parseDouble(String.valueOf(betrag))));
//        abheben.setOnAction(e -> kontoController.abheben(parseDouble(String.valueOf(betrag))));
        abheben.setOnAction(e -> kontoController.abheben(parseDouble(betrag.getText())));
        aktionen.getChildren().add(abheben);

        this.setBottom(aktionen);
    }

    /**
     * zeigt dem Nutzer eine Nachricht in einem neuen Fenster an
     * @param message anzuzeigende Nachricht
     */
    public void displayMessage(String message) {
//        System.out.println("Debug: In displayMessage");

        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setContentText(message);
        alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        alert.show();
    }

}
