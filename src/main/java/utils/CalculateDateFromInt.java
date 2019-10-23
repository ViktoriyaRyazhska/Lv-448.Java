package utils;

import java.time.Period;

public class CalculateDateFromInt {
    public static final int YEAR = 365;
    public static final int MONTH = 30;

    public static Integer[] calculateDaysFromInt(int amountDays) {

        int years = 0;
        int months = 0;
        int days = 0;
        Integer[] result = new Integer[3];
        if (amountDays / YEAR > 0) {
            years = amountDays / YEAR;
            days = amountDays % YEAR;
            if (days / MONTH > 0) {
                months = days / MONTH;
                days = days % MONTH;
            }
        } else if (amountDays / MONTH > 0) {
            months = amountDays / MONTH;
            days = months % MONTH;
        } else
            days = amountDays;
        result[0] = years;
        result[1] = months;
        result[2] = days;

        return result;

    }
}
