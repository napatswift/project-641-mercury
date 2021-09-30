package ku.cs.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProductReport extends Report<Product>{

    public ProductReport(String type, Product reportItem, LocalDateTime reportDateTime, String detail) {
        super(type, reportItem, reportDateTime, detail);
    }

    public ProductReport(String type, Product reportItem, String detail) {
        super(type, reportItem, detail);
    }

    @Override
    public User getSuspectedPerson() {
        return getReportItem().getStore().getOwner();
    }

    @Override
    public String toCSV() {
        return "" + getType() + ","
                + getSuspectedPerson().getUsername() + ","
                + (getReportDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)) + ","
                + null + ","
                + (getReportItem().getId()) + ","
                + "\"" + getDetail().replace("\"", "\"\"") + "\"";
    }
}
