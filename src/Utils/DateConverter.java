package Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

public class DateConverter {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    public static Date stringToDate(String dateString) throws ParseException {
        return formatter.parse(dateString);
    }

    public static String dateToString(Date date) {
        return formatter.format(date);
    }

    public static int getYearsBetween(Date startDate, Date endDate) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);

        int years = endCal.get(Calendar.YEAR) - startCal.get(Calendar.YEAR);

        if (endCal.get(Calendar.DAY_OF_YEAR) < startCal.get(Calendar.DAY_OF_YEAR)) {
            years--;
        }

        return years;
    }
}