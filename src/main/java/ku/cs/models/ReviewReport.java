package ku.cs.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReviewReport extends Report<Review>{

    public ReviewReport(String type, Review reportItem, LocalDateTime reportDateTime, String detail) {
        super(type, reportItem, reportDateTime, detail);
    }

    public ReviewReport(String type, Review reportItem, String detail) {
        super(type, reportItem, detail);
    }

    @Override
    public User getSuspectedPerson() {
        return getReportItem().getAuthor();
    }

    @Override
    public String toCSV() {
        return getType() + ","
                + getSuspectedPerson().getUsername() + ","
                + getReportDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + ","
                + getReportItem().getId() + ","
                + null + ","
                + "\"" + getDetail().replace("\"", "\"\"") + "\"";
    }
}
