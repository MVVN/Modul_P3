package vorlesung7.buchhandlung;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * K�ufer in der Ein-Buch-Buchhandlung. Er holt 100 mal ein Buch aus dem Regal
 *
 */
public class Kaeufer
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
	public void kaufen(int nr, Lock l, Condition regalLeer)
	{
		int i = 0;
		while (i < 500 && !handlung.geschlossen)
		{
			while(handlung.getAnzahlBuecher().get() == 0)
			{
				try{
					l.lock();
					try {
						regalLeer.await();
					} catch (InterruptedException e) {
						System.out.println("bla");
						return;
					}
				} finally {
					l.unlock();
				}
			}
			handlung.getAnzahlBuecher().decrementAndGet();
			Thread.yield();
			System.out.println("Kaeufer "+ nr + ": "+handlung.getAnzahlBuecher());
			i++;
		}
		System.out.println("K�ufer verl�sst den Laden");
	}
}