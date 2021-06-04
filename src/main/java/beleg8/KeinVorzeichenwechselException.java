package beleg8;

public class KeinVorzeichenwechselException extends Exception{
    /**
     * Exception, wenn kein Vorzeichenwechsel stattgefunden hat
     */
    public KeinVorzeichenwechselException() {
        super("Es fand kein Vorzeichenwechsel statt!");
    }

}
