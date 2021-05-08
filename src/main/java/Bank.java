public class Bank {

    private final long bankleitzahl;

    public Bank(long bankleitzahl) {
        if (bankleitzahl < 0) {
            throw new IllegalArgumentException("Bankleitzahl kann nicht negativ sein.");
        }
        this.bankleitzahl = bankleitzahl;
    }

    public long getBankleitzahl() {
        return bankleitzahl;
    }

    public long girokontoErstellen(Kunde inhaber) {
        
    }
}
