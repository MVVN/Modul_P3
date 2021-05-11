package beleg5;

/**
 * mathematisches Intervall auf linear angeordneter Menge
 * @param <T> T muss Comparable implementieren, damit Objekte untereinander vergleichbar sind
 *           Comparable {@literal <? super T>} Objekte müssen Oberklasse von T sein
 */
public class Intervall<T extends Comparable<? super T>> {

    /**
     * untere und obere Grenze des Intervalls
     */
    private T untereGrenze, obereGrenze;

    /**
     * Konstruktor mit oberer und unterer Grenze
     * @param untereGrenze der niedrigste Wert im Intervall
     * @param obereGrenze der höchste Wert im Intervall
     * @throws IllegalArgumentException wenn einer der Parameter null ist
     */
    public Intervall(T untereGrenze, T obereGrenze) {
        if (untereGrenze != null && obereGrenze != null) {
            this.untereGrenze = untereGrenze;
            this.obereGrenze = obereGrenze;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @return untere Grenze des Intervalls
     */
    public T getUntereGrenze() {
        return untereGrenze;
    }

    /**
     * @return obere Grenze des Intervalls
     */
    public T getObereGrenze() {
        return obereGrenze;
    }

    /**
     * kontrolliert, ob Grenzen vertauscht sind (null nicht erlaubt, daher nur das kontrollierbar)
     * @return true, falls vertauscht; ansonsten false
     */
    public boolean isLeer() {
        return untereGrenze.compareTo(obereGrenze) > 0;
    }

    /**
     * kontrolliert, ob Wert zwischen unterer und oberer Grenze liegt
     * @param wert der Wert, nachdem im Intervall geschaut wird
     * @return true wenn enthalten; false wenn nicht
     */
    public boolean enthaelt(T wert) {
        return wert.compareTo(untereGrenze) >= 0 && wert.compareTo(obereGrenze) <= 0;
    }

    /**
     * Bildet Schnittmenge dieser Menge und einer anderen Menge
     * @param anderes das andere Intervall, mit dem die Schnittmenge gebildet werden soll
     * @param <T1> Typ der Inhalte im anderen Intervall; muss von T erben, damit Schnittmenge erstellt werden kann
     * @return Schnittmenge als Intervall
     * @throws Exception falls eine der beiden Intervalle leer ist
     */
    public <T1 extends T> Intervall<T> schnitt(Intervall<T1> anderes) throws Exception {
        if (anderes.isLeer() || this.isLeer()) {
            throw new Exception("Kann keine Schnittmenge mit leerer Menge bilden.");
        }
        T untenNeu, obenNeu;

        untenNeu = untereGrenze.compareTo(anderes.getUntereGrenze()) <= 0 ? anderes.getUntereGrenze() : untereGrenze;
        obenNeu = obereGrenze.compareTo(anderes.getObereGrenze()) >= 0 ? anderes.getObereGrenze() : obereGrenze;

        return new Intervall<T>(untenNeu, obenNeu);
    }

    /**
     * @return Intervall im Format "[ a, b ]" als String
     */
    @Override
    public String toString() {
        return "[ " + untereGrenze + ", " + obereGrenze + " ]";
    }
}
