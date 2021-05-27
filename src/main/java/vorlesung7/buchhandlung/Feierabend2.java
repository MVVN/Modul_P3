package vorlesung7.buchhandlung;

/**
 * beendet einen Thread
 * @author Doro
 *
 */
public class Feierabend2 implements Runnable{

	private Thread t;
	
	/**
	 * erstellt einen Feierabend f�r den angegebenen Thread
	 * @param t anzuhaltender Thread
	 */
	public Feierabend2(Thread t)
	{
		this.t = t;
	}
	/**
	 * beendet des Thread nach angemessener Wartezeit
	 */
	public void run()
	{
		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
		}
		t.interrupt();
	}
}
