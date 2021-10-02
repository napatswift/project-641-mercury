package ku.cs.models;

import java.time.LocalDateTime;
import java.util.UUID;

abstract public class Report<T> implements Comparable<Report>{

    protected String id;
    private String type;
    private LocalDateTime reportDateTime;
    private T reportItem;
    private String detail;

    @Override
    public int compareTo(Report o) {
        return this.id.compareTo(o.id);
    }

    public Report(String id, String type, T reportItem, LocalDateTime reportDateTime, String detail) {
        this.id = id;
        this.type = type;
        this.reportItem = reportItem;
        this.reportDateTime = reportDateTime;
        this.detail = detail;
    }

    public Report(String type, T reportItem, String detail) {
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.reportItem = reportItem;
        this.reportDateTime = LocalDateTime.now();
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

    abstract public User getSuspectedPerson();

    public LocalDateTime getReportDateTime() {
        return reportDateTime;
    }

    public T getReportItem() {
        return reportItem;
    }

    public String getType() {
        return type;
    }

    abstract public String toCSV();
}
