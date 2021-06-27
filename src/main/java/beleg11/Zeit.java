package beleg11;

import java.time.LocalTime;

/**
* die aktuelle Uhrzeit
*/
public class Zeit
{
	private int stunde, minute, sekunde;     

	/**
	 * erstellt die Zeitermittlung
	 */
    public Zeit() {
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
		sekunde = jetzt.getSecond();
	}

}
