package dev.ufo.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Date {

    private int year;
    private int month;
    private int day;


    public String getMonthName() {
        return switch (this.month) {
            case 1 -> "jan";
            case 2 -> "feb";
            case 3 -> "mar";
            case 4 -> "apr";
            case 5 -> "may";
            case 6 -> "jun";
            case 7 -> "jul";
            case 8 -> "aug";
            case 9 -> "sep";
            case 10 -> "oct";
            case 11 -> "nov";
            case 12 -> "dec";
            default -> "err";
        };
    }

    public static Date today() {

        Date date = new Date();

        LocalDate d = LocalDate.now();

        date.setDay(d.getDayOfMonth());
        date.setMonth(d.getMonthValue());
        date.setYear(d.getYear());

        return date;
    }

    public static Date of(String s) {

        int[] counts = new int[3];

        for (int i = 0; i != 3; i++) {

            String date = s.split("\\.")[i];

            try {
                counts[i] = Integer.parseInt(date);
            } catch (Exception e){
                return today();
            }

        }

        Date d = new Date();

        d.setDay(counts[0]);
        d.setMonth(counts[1]);
        d.setYear(counts[2]);

        return d;

    }

    @Override
    public String toString() {
        return this.day + "." + this.month + "." + this.year;
    }

    public String toFormalString() {
        return this.day + "-" + getMonthName() + "-" + this.year;
    }

}
