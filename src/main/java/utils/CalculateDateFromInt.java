package utils;

public class CalculateDateFromInt {
    public static final int YEAR = 365;
    public static final int MONTH = 30;

    public static String calculateDaysFromInt(int amountDays) {

        int years = 0;
        int months = 0;
        int days = 0;

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

        return "Amount of years: " + years + "Amount of months: " + months + "Amount of days: " + days;

    }
}
