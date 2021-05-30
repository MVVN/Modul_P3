package beleg7.baelle;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * ein hüpfender Ball
 *
 * @author Doro
 */
public class Ball implements Runnable {

    /**
     * erstellt einen Ball, der in das angegebene Panel gezeichnet wird
     *
     * @param b Die Zeichenfläche
     */
    public Ball(JPanel b) {
        box = b;
    }

    /**
     * erstellt einen Ball, der in das angegebene Panel gezeichnet wird
     * @param b Die Zeichenfläche
     * @param dauer Dauer, wie lange ball hüpfen soll
     */
    public Ball(JPanel b, int dauer) {
        box = b;
        this.geloescht = false;
        setDauer(dauer);
        Thread t = new Thread(this);
        t.start();
    }

    /**
     * zeichnet den Ball an seiner aktuellen Position
     */
    public void draw() {
        Graphics g = box.getGraphics();
        g.fillOval(x, y, XSIZE, YSIZE);
        g.dispose();
    }

    /**
     * löscht den Ball von der Oberfläche
     */
    public void loeschen() {
        this.geloescht = true;
        Graphics g = box.getGraphics();
        g.setXORMode(box.getBackground());
        g.fillOval(x, y, XSIZE, YSIZE);
    }

    /**
     * bewegt den Ball einen Schritt weiter
     */
    public void move() {
        if (!box.isVisible())
            return;
        Graphics g = box.getGraphics();
        g.setXORMode(box.getBackground());
        g.fillOval(x, y, XSIZE, YSIZE);
        x += dx;
        y += dy;
        Dimension d = box.getSize();
        if (x < 0) {
            x = 0;
            dx = -dx;
        }
        if (x + XSIZE >= d.width) {
            x = d.width - XSIZE;
            dx = -dx;
        }
        if (y < 0) {
            y = 0;
            dy = -dy;
        }
        if (y + YSIZE >= d.height) {
            y = d.height - YSIZE;
            dy = -dy;
        }
        g.fillOval(x, y, XSIZE, YSIZE);
        g.dispose();
    }

    /**
     * bewegt den Ball dauer viele Schritte weiter in der Oberfläche. Um eine angenehme Animation
     * zu erhalten, wird nach jedem Schritt eine Pause eingelegt.
     * Kontrolliert, ob Ball gelöscht werden soll oder stoppen soll
     */
    public void huepfen() {
        this.draw();
        for (int i = 1; i <= getDauer(); i++) {
            if (toDelete) {
                break; // skip forLoop
            }
            synchronized (this) {
                while (!amHuepfen) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            this.move();
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
            }
        }
        this.loeschen();
    }

    private JPanel box;
    private static final int XSIZE = 10;
    private static final int YSIZE = 10;
    private int x = 0;
    private int y = 0;
    private int dx = 2;
    private int dy = 2;

    /**
     * signalisiert, ob Ball gelöscht wurde
     */
    private boolean geloescht;

    /**
     * Dauer, die der Ball springen soll
     */
    private int dauer;

    /**
     * markiert, ob Ball von außen gelöscht werden soll
     */
    private boolean toDelete;

    /**
     * boolean, ob Ball derzeit hüpft
     */
    private boolean amHuepfen = false;

    /**
     * setzt Dauer, wie lange Ball hüpfen soll
     * @param dauer Dauer, wie lange der Ball hüpfen soll
     */
    private void setDauer(int dauer) {
        if (dauer < 0) {
            throw new IllegalArgumentException("Dauer muss positiv sein.");
        }
        this.dauer = dauer;
    }

    /**
     * erfragt die gesetzte Dauer
     *
     * @return Dauer, wie lange der Ball hüpfen soll
     */
    private int getDauer() {
        return this.dauer;
    }

    /**
     * Während Thread nicht interrupted wird, soll der Ball hüpfen ausführen
     */
    @Override
    public void run() {
        amHuepfen = true;
        toDelete = false;
        huepfen();
    }

    /**
     * setzt amHuepfen auf false -> Bälle stoppen
     */
    public void stop() {
        this.amHuepfen = false;
    }

    /**
     * setzt amHuepfen true und notified den Thread darüber
     */
    public synchronized void resume() {
        this.amHuepfen = true;
        this.notify();
    }

    /**
     * setzt toDelete auf true und erzwingt das Löschen des Balls
     */
    public void forceDelete() {
        resume();
        this.toDelete = true;
    }
}