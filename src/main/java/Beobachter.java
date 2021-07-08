import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Beobachter, welcher ein Subjekt beobachtet
 */
public interface Beobachter extends PropertyChangeListener {

    /**
     * erhält Werte durch PropertyChangeSupport
     * @param evt event des PropertyChange
     */
    public void propertyChange(PropertyChangeEvent evt);
}
