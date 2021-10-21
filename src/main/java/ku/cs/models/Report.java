package ku.cs.models;

import ku.cs.models.io.CSVFile;

import java.time.LocalDateTime;
import java.util.UUID;

abstract public class Report<T> implements Comparable<Report>, CSVFile {
    protected String id;
    private final String type;
    private final LocalDateTime reportDateTime;
    private final T reportItem;
    private final String detail;

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

    public String getId()                    { return id; }
    public String getDetail()                { return detail; }
    public T getReportItem()                 { return reportItem; }
    public String getType()                  { return type; }
    public LocalDateTime getReportDateTime() { return reportDateTime; }
    abstract public User getSuspectedPerson();
}
