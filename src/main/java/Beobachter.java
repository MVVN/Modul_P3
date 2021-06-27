import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Interface für Beobachter, welche ein Subjekt beobachten
 */
public class Beobachter implements PropertyChangeListener {

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Konto konto = (Konto) evt.getSource();
        Object oldValue = evt.getOldValue();
        Object newValue = evt.getNewValue();

        System.out.println("Änderungen im Konto " + konto.getKontonummer() + " : " +
                System.lineSeparator() +
                evt.getPropertyName() + ":" + System.lineSeparator() +
                oldValue + " --> " + newValue
        );
    }
}
