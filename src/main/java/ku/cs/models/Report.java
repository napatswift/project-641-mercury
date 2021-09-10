package ku.cs.models;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

abstract public class Report<T> {

    private String type;
    private LocalDateTime reportDateTime;
    private T reportItem;
    private String detail;

    public Report(String type, T reportItem, LocalDateTime reportDateTime, String detail) {
        this.type = type;
        this.reportItem = reportItem;
        this.reportDateTime = reportDateTime;
        this.detail = detail;
    }

    public Report(String type, T reportItem, String detail) {
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
