package beleg11;

/**
 * Controller für Zeit und Uhr
 */
public class Controller_Uhr {
    private View_Uhr vUhr;
    private Model_Zeit mZeit;

    public Controller_Uhr(View_Uhr view, Model_Zeit model) {
        vUhr = view;
        mZeit = model;
        view.anmelden(this);
        model.anmelden(view);
    }

    /**
     * schalten Uhrview an
     */
    public void anschalten() {
        mZeit.anmelden(vUhr);
        vUhr.anAusSchalten(true);
    }

    /**
     * schalten Uhrview aus
     */
    public void abschalten() {
        mZeit.abmelden(vUhr);
        vUhr.anAusSchalten(false);
    }

    /**
     * liefert registriertes Model der Zeit
     * @return Zeitmodel
     */
    public Model_Zeit get_m_Zeit() {
        return mZeit;
    }
}
