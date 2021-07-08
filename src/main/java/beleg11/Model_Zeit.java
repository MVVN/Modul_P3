package beleg11;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalTime;

/**
* die aktuelle Uhrzeit (Model)
*/
public class Model_Zeit
{
	private int stunde, minute, sekunde;
	private PropertyChangeSupport support = new PropertyChangeSupport(this);
	/**
	 * zum Mitgeben an den PropertyChangeSupport
	 */
	private int letzteSekunde;

	/**
	 * erstellt die Zeitermittlung
	 */
    public Model_Zeit() {
		//Thread starten, um die Uhrzeit laufen zu lassen:
		new Thread() {
			/**
			 * löst jede Sekunde die Aktualisierung der Uhrzeit aus
			 */
			@Override
			public void run() {
				try {
					while (true) {
						laufen();
						Thread.sleep(1000);
					}
				}
				catch (InterruptedException e) {}
			}
		}.start();
    }

    /**
     * liefert die aktuelle Stunde
     * @return
     */
    public int getStunde() { return stunde; }

    /**
     * liefert die aktuelle Minute
     * @return
     */
    public int getMinute() { return minute; }

    /**
     * liefert die aktuelle Sekunde
     * @return
     */
    public int getSekunde() { return sekunde; }  
	
	private void laufen()
	{
		LocalTime jetzt = LocalTime.now();
		stunde = jetzt.getHour();
		minute = jetzt.getMinute();
		letzteSekunde = sekunde;
		sekunde = jetzt.getSecond();

		support.firePropertyChange("Sekunde", letzteSekunde, sekunde);
	}

	public void anmelden(PropertyChangeListener listener) {
		if (listener != null) {
			support.addPropertyChangeListener(listener);
		}
	}

	public void abmelden(PropertyChangeListener listener) {
		support.removePropertyChangeListener(listener);
	}

}
