package ku.cs.models.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateTime {
    public static String toReadableDateTime(LocalDateTime time) {
        LocalDateTime now = LocalDateTime.now();
        long minute = time.until(now, ChronoUnit.MINUTES);

        if (minute < 1)
            return "Just Now";
        else if (minute < 15)
            return minute + " minutes ago";

        long day = time.until(now, ChronoUnit.DAYS);
        boolean isToday = time.getYear() == now.getYear()
                && time.getDayOfYear() == now.getDayOfYear();
        String pattern;
        if (isToday)
            pattern = "HH:mm";
        else if (day < 7) {
            pattern = "EEEE HH:mm";
        } else if (time.getYear() != now.getYear()) {
            pattern = "HH:mm - d MMM Y";
        } else {
            pattern = "HH:mm - d MMM";
        }
        DateTimeFormatter simpleDateFormat = DateTimeFormatter.ofPattern(pattern);

        return (isToday ? "Today at " : "") + time.format(simpleDateFormat);
    }
}
