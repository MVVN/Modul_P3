package vorlesung7.buchhandlung;

/**
 * K�ufer in der Ein-Buch-Buchhandlung. Er holt mehrmals ein Buch aus dem Regal
 *
 */
public class Kaeufer implements Runnable
{
	/**
	 * die Buchhandlung
	 */
	Buchhandlung handlung;
	
	/**
	 * erstellt einen K�ufer, der in der angegebenen Buchhandlung "einkauft"
	 * @param handlung
	 */
	public Kaeufer(Buchhandlung handlung)
	{
		this.handlung = handlung;
	}
	
	/**
	 * Der K�ufer nimmt nacheinander viele B�cher aus dem Regal
	 */
	public void kaufen(int insgesamt)
	{
		int i = 0;
		while (i < insgesamt && !handlung.geschlossen)
		{
			int anzahl;
			synchronized(handlung)
			{
				
				anzahl = handlung.getAnzahlBuecher();
				//Aktives Warten: NIEMALS!!!
/*				while(anzahl <= 0)
				{
					
				}
*/
				while(anzahl <= 0)
				{
					try {
						handlung.wait();
					} catch (InterruptedException e) {
					} //warten, bis es eine �nderung
									//im Objekt handlung gegeben hat
									//dersynchronized-Lock wird aufgehoben
					anzahl = handlung.getAnzahlBuecher();
							//noch mal den jetzt aktuellen Wert holen
				}
				try {
					Thread.sleep(4);
				} catch (InterruptedException e) {
				}
				anzahl = anzahl - 1;
				handlung.setAnzahlBuecher(anzahl);
			}
			System.out.println("Kaeufer: "+anzahl);
			i++;
		}
		System.out.println("K�ufer verl�sst den Laden");
	}
	
	private int insgesamt;

	/**
	 * @param insgesamt the insgesamt to set
	 */
	public void setInsgesamt(int insgesamt) {
		this.insgesamt = insgesamt;
	}

	@Override
	public void run() {
		this.kaufen(insgesamt);
		
	}
}