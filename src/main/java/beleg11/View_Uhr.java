package beleg11;

import java.beans.PropertyChangeListener;

/**
 * allgemeine Uhr (View)
 */
public interface View_Uhr extends PropertyChangeListener {

    /**
     * Uhr an- / ausschalten
     * @param b an = true ; aus = false
     */
    public void anAusSchalten(boolean b);

    /**
     * Anmelden des Controllers beim View
     * @param controller
     */
    public void anmelden(Controller_Uhr controller);
}
