package beleg7.baelle;

import java.util.*;

/**
 * Steuerungsklasse für eine Ball-Animation
 *
 * @author Doro
 */
public class Ballspiel {
    private BallFrame f;
    /**
     * Liste, welche alle erstellten Bälle speichert
     */
    private List<Ball> ballList;

    /**
     * erstellt die Steuerungsklasse für die angegebene Oberfläche
     *
     * @param f
     */
    public Ballspiel(BallFrame f) {
        this.f = f;
        this.ballList = new ArrayList<>();
    }

    /**
     * startet einen Ball auf der Oberfläche und lässt ihn hüpfen
     */
    public void ballStarten() {
        Random r = new Random();
        int dauer = r.nextInt(500) + 1000; //Zufallszahl zwischen 1000 und 1500
        Ball b = new Ball(f.getZeichenflaeche(), dauer);
        ballList.add(b);
    }

    /**
     * hält alle Bälle auf der Oberfläche an, so dass sie an ihrer aktuellen Position
     * stehen bleiben
     */
    public void baelleStoppen() {
        for (Ball b : ballList) {
            b.stop();
        }
    }

    /**
     * lässt alle angehaltenen Bälle wieder weiter hüpfen
     */
    public void baelleWeiter() {
        for (Ball b : ballList) {
            b.resume();
        }
    }

    /**
     * löscht alle Bälle von der Oberfläche und aus der Liste
     */
    public void alleLoeschen() {
        for (Ball b : ballList) {
            b.forceDelete();
        }
        ballList.clear();
    }
}




