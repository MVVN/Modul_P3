package beleg9;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;

public class NumberFormatter {
    public static void main(String[] args) {
        String userInput;
        int intZahl;
        double doubleZahl;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            FileWriter fileWriter = new FileWriter("formatierteZahlen.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);

            do {
                System.out.println("Bitte geben Sie eine ganze Zahl ein: ");
                userInput = bufferedReader.readLine();
            } while (!isInteger(userInput));
            intZahl = Integer.parseInt(userInput);

            do {
                System.out.println("Bitte geben Sie nun eine Kommazahl ein: ");
                userInput = bufferedReader.readLine();
            } while (!isDouble(userInput));
            doubleZahl = Double.parseDouble(userInput);

//          1.  Standardformatierung Decimal Integer
            printWriter.printf("%1$d %n", intZahl);
//          2.  mindestens 10 Stellen, wenn zu kurz mit 0 gefüllt
            printWriter.printf("%1$010d %n", intZahl);
//          3.  Vorzeichen anzeigen, tausender Trennzeichen
            printWriter.printf("%1$+,d %n", intZahl);
//          4. Standardformatierung Floating Point
            printWriter.printf("%1$f %n", doubleZahl);
//          5. Vorzeichen und nur 2 Nachkommastellen
            printWriter.printf("%1$+.2f %n", doubleZahl);
//          6. wissenschaftliche Darstellung (Exponent)
            printWriter.printf("%1$+.2E %n", doubleZahl);
//          7. 3 Nachkommastellen + USA Schreibweise mit . als Dezimaltrennzeichen (printf mit Locale parameter)
            printWriter.printf(Locale.US ,"%1$+.3f %n", doubleZahl);
//          8. ein Prozentzeichen
            printWriter.printf("%% %n");
//          9. aktuelles Datum - Wochentag ausgeschrieben, Tag ohne führende 0, Monatsname, Vierstellige Jahreszahl
            printWriter.printf(Locale.GERMAN,"%tA %<te %<tB %<tY %n", LocalDate.now());
//          10. aktuelles Datum - gleich bloß in italienisch
            printWriter.printf(Locale.ITALIAN,"%tA %<te %<tB %<tY %n", LocalDate.now());
//          11. aktuelle Uhrzeit im englischen Format - HH:MM am/pm
            printWriter.printf(Locale.UK,"%tI:%<tM%<tp  %n", LocalTime.now());

            bufferedReader.close();
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
