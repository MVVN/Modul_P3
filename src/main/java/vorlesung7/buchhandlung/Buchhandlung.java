package vorlesung7.buchhandlung;

/**
 * Diese Klasse simuliert eine Buchandlung als Test f�r Thread-Programmierung.
 * Leider gibt es hier nur ein Buch zu kaufen, so dass nur die Anzahl der im Regal
 * stehenden Exemplare des Buches interessant ist.
 *
 */
public class Buchhandlung {
	/**
	 * Anzahl der B�cher im Regal
	 */
	private int anzahlBuecher =0;
	/**
	 * ob die Buchhandlung geschlossen ist oder nicht
	 */
	public boolean geschlossen = false;

	/**
	 * liefert die Anzahl der im Regal stehenden B�cher
	 * @return Anzahl der B�cher im Regal
	 */
	public int getAnzahlBuecher() {
		return anzahlBuecher;
	}

	/**
	 * Ändert die Anzahl der B�cher im Regal
	 * @param anzahlBuecher neue Anzahl B�cher
	 */
	public void setAnzahlBuecher(int anzahlBuecher) {
		this.anzahlBuecher = anzahlBuecher;
		this.notifyAll(); //allen Wartenden Bescheid sagen,
		//dass es im Objekt this eine �nderung gegeben hat
	}

	/**
 	* stellt nacheinander insgesamt viele B�cher ins Regal
 	* @param insgesamt Die Zahl der neuen B�cher
 	*/
	public void auffuellen(int insgesamt)
	{
		int i = 0;
		while (i < insgesamt && !Thread.interrupted())
		{
			int anzahl;
			synchronized(this) //Oft: extra statisches Objekt angelegt
			{
				anzahl = this.getAnzahlBuecher();
				try {
					Thread.sleep(4000); //4000 Millisekunden lang nichts tun
				} catch (InterruptedException e) {
					System.out.println("Kaffeepause unterbrochen");
					break;
				}
				anzahl = anzahl + 1;
				this.setAnzahlBuecher(anzahl);
				
			}
			System.out.println("im Regal: "+anzahl);
			i++;
		}
		System.out.println("T�r abschlie�en");
	}
	
	/**
	 * schliesst die Buchhandlung nach der angegebenen Wartezeit
	 * @param wartezeit Wartezeit in Millisekunden
	 */
	public void schliessen(int wartezeit)
	{
		try {
			Thread.sleep(wartezeit);
		} catch (InterruptedException e) {}
		this.geschlossen = true;
	}
	
	/**
	 * l�sst Buchh�ndler und K�ufer arbeiten
	 * @param args wird nicht benutzt
	 */
	public static void main(String[] args){
		Buchhandlung buchUndZeitschrift = new Buchhandlung();
		Kaeufer kaeufer = new Kaeufer(buchUndZeitschrift);
		Feierabend feierabend = new Feierabend(buchUndZeitschrift);
		Feierabend2 feierabend2 = new Feierabend2(Thread.currentThread());

		Thread ft = new Thread(feierabend);
		ft.start();
		Thread f2t = new Thread(feierabend2);
		f2t.start();
		
		
		Thread kt = new Thread(kaeufer); //ein neuer Ausf�hrungsstrang
		//in run() von kaeufer steht, was der Thread tun soll
		kaeufer.setInsgesamt(500);
		kt.start();
		buchUndZeitschrift.auffuellen(1000);
		


		
	}

}










