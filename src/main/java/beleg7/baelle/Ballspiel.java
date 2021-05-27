package beleg7.baelle;

import java.util.Random;

/**
 * Steuerungsklasse für eine Ball-Animation
 * @author Doro
 *
 */
public class Ballspiel {
	private BallFrame f;
	
	/**
	 * erstellt die Steuerungsklasse für die angegebene Oberfläche
	 * @param f
	 */
	public Ballspiel(BallFrame f)
	{
		this.f = f;
	}
	
	/**
	 * startet einen Ball auf der Oberfläche und lässt ihn hüpfen
	 */
	public void ballStarten()
	{
		Random r = new Random();
		int dauer = r.nextInt(500) + 1000; //Zufallszahl zwischen 1000 und 1500
		Ball b = new Ball(f.getZeichenflaeche());
		b.huepfen(dauer);
	}
	
	/**
	 * hält alle Bälle auf der Oberfläche an, so dass sie an ihrer aktuellen Position
	 * stehen bleiben
	 */
	public void baelleStoppen()
	{
		
	}

	/**
	 * lässt alle angehaltenen Bälle wieder weiter hüpfen
	 */
	public void baelleWeiter() {
		
	}

	/**
	 * löscht alle Bälle von der Oberfläche
	 */
	public void alleLoeschen() {
		
	}
}




