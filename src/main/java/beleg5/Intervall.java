package beleg5;

public class Intervall<T extends Comparable<? super T>> {

    private T untereGrenze, obereGrenze;

    public Intervall(T untereGrenze, T obereGrenze) {
        if (untereGrenze != null && obereGrenze != null) {
            this.untereGrenze = untereGrenze;
            this.obereGrenze = obereGrenze;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public T getUntereGrenze() {
        return untereGrenze;
    }

    public T getObereGrenze() {
        return obereGrenze;
    }

    public boolean isLeer() {
        return untereGrenze.compareTo(obereGrenze) > 0;
    }

    public boolean enthaelt(T wert) {
        return wert.compareTo(untereGrenze) >= 0 && wert.compareTo(obereGrenze) <= 0;
    }

    public <T1 extends T> Intervall<T> schnitt(Intervall<T1> anderes) throws Exception {
        if (anderes.isLeer() || this.isLeer()) {
            throw new Exception("Kann keine Schnittmenge mit leerer Menge bilden.");
        }
        T untenNeu, obenNeu;

        untenNeu = untereGrenze.compareTo(anderes.getUntereGrenze()) <= 0 ? untereGrenze : anderes.getUntereGrenze();
        obenNeu = obereGrenze.compareTo(anderes.getObereGrenze()) >= 0 ? obereGrenze : anderes.getObereGrenze();

        return new Intervall<T>(untenNeu, obenNeu);
    }

    @Override
    public String toString() {
        return "[ " + untereGrenze + ", " + obereGrenze + " ]";
    }
}
