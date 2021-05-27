package vorlesung7.buchhandlung;

/**
 * schlie�t eine Buchhandlung ab
 * @author Doro
 *
 */
public class Feierabend implements Runnable{
	/**
	 * die Buchhandlung
	 */
	private Buchhandlung handlung;
	
	/**
	 * erstellt einen Feierabend f�r die angegebene Buchhandlung
	 * @param handlung zu schlie�ende Buchhandlung
	 */
	public Feierabend(Buchhandlung handlung)
	{
		this.handlung = handlung;
	}

	/**
	 * schlie�t die Buchhandlung nach angemessener Wartezeit
	 */
	public void run()
	{
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
		handlung.geschlossen = true;
	}
}
