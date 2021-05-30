package vorlesung7.buchhandlung;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
	private AtomicInteger anzahlBuecher = new AtomicInteger(0);
	/**
	 * ob die Buchhandlung geschlossen ist oder nicht
	 */
	public boolean geschlossen = false;

	/**
	 * liefert die Anzahl der im Regal stehenden B�cher
	 * @return Anzahl der B�cher im Regal
	 */
	public AtomicInteger getAnzahlBuecher() {
		return anzahlBuecher;
	}

	/**
	 * �ndert die Anzahl der B�cher im Regal
	 * @param anzahlBuecher neue Anzahl B�cher
	 */
//	public void setAnzahlBuecher(int anzahlBuecher) {
//		this.anzahlBuecher = anzahlBuecher;
//	}

	/**
	 * stellt nacheinander insgesamt viele B�cher ins Regal
	 * @param insgesamt Die Zahl der neuen B�cher
	 */
	public void auffuellen(int insgesamt)
	{
		int i = 0;
		while (i < insgesamt && !geschlossen)
		{
			this.getAnzahlBuecher().incrementAndGet();
			Thread.yield();
			System.out.println("im Regal: "+this.getAnzahlBuecher());
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
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public static void main(String[] args){
		Buchhandlung buchUndZeitschrift = new Buchhandlung();
		Kaeufer kaeufer = new Kaeufer(buchUndZeitschrift);
		Feierabend feierabend = new Feierabend(buchUndZeitschrift);
		Feierabend2 feierabend2 = new Feierabend2(Thread.currentThread());
		Lock l = new ReentrantLock();
		Condition nichtLeer = l.newCondition();

		ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
		Runnable verkaeufer = () -> {buchUndZeitschrift.getAnzahlBuecher().incrementAndGet();
			l.lock();
			nichtLeer.signal();
			l.unlock();
			System.out.println("im Regal: " + buchUndZeitschrift.getAnzahlBuecher());
		};
		// buchUndZeitschrift.auff�llen(1500);
		Runnable kaeuferR = () -> kaeufer.kaufen(1,l, nichtLeer);
		Callable<Integer> feier = () -> {buchUndZeitschrift.geschlossen = true;

			//Thread.currentThread().getThreadGroup().interrupt();
			service.shutdownNow();
			return buchUndZeitschrift.getAnzahlBuecher().get();
		};
		Future<Integer> restbestand = service.schedule(feier, 5, TimeUnit.MILLISECONDS);
		service.scheduleAtFixedRate(verkaeufer, 0, 1, TimeUnit.MILLISECONDS);
		service.execute(() -> kaeufer.kaufen(1,l, nichtLeer));
		service.execute(() -> kaeufer.kaufen(2,l, nichtLeer));
		service.execute(() -> kaeufer.kaufen(3,l, nichtLeer));

		try {
			System.out.println("Am Ende sind " + restbestand.get() + " B�cher da");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}










