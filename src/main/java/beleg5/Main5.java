package beleg5;

import java.util.Calendar;
import java.util.Date;
import java.sql.Time;
import java.util.GregorianCalendar;

public class Main5 {

    public static void main(String[] args) {

        Intervall<String> stringIntervall = new Intervall<>("Apfel", "Mango");

        System.out.println(stringIntervall);
        System.out.println("Enthält Bananen? - " + stringIntervall.enthaelt("Bananen"));
        System.out.println("Ist leer? - " + stringIntervall.isLeer());

        Intervall<String> stringIntervall2 = new Intervall<>("Kaki", "Pomelo");
        System.out.println(stringIntervall2);
        try {
            System.out.println("Schnittmenge: " + stringIntervall.schnitt(stringIntervall2));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intervall<Integer> integerIntervall = new Intervall<>(5, 0);
        System.out.println("\n"+integerIntervall);
        System.out.println("Empty? - " + integerIntervall.isLeer());

        System.out.println();

        Calendar cal1 = new GregorianCalendar(1991, 1, 1);
        Intervall<Date> dateIntervall = new Intervall<Date>(cal1.getTime(), new Date());
        Calendar cal2 = new GregorianCalendar(1970, 1, 1);
        Calendar cal3 = new GregorianCalendar(2005, 1, 1);
        Intervall<Date> dateIntervall2 = new Intervall<Date>(cal2.getTime(), cal3.getTime());

        Intervall<Time> timeIntervall = new Intervall<Time>(new Time(cal2.getTimeInMillis()), new Time(cal3.getTimeInMillis()));

        try {
            System.out.println("Schnitt Date-Date: " + dateIntervall.schnitt(dateIntervall2));
            System.out.println("Schnitt Date-Time: " + dateIntervall.schnitt(timeIntervall));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
