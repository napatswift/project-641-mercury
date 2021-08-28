package ku.cs.models;

import java.time.LocalDateTime;

public class Report {

    private final ReportType type;
    private User suspectedPerson;
    private final User reporter;
    private LocalDateTime reportDateTime;
    private Review review;
    private Product product;
    private String detail;
    public enum ReportType {HARASSMENT, ABUSE}

    public Report(ReportType type, User suspectedPerson, User reporter, LocalDateTime reportDateTime, Review review, Product product, String detail) {
        this.type = type;
        this.suspectedPerson = suspectedPerson;
        this.reporter = reporter;
        this.reportDateTime = reportDateTime;
        this.review = review;
        this.product = product;
        this.detail = detail;
    }

    public Report(ReportType type, User reporter) {
        this.type = type;
        this.reporter = reporter;
    }

    public User getReporter() {
        return reporter;
    }

    public ReportType getType() {
        return type;
    }

    public void setDetail(String detail) {

        if (detail.isBlank()){
            return;
        }
        this.detail = detail;
    }

    public void setReportItem(Product product){
        //this.suspectedPerson = เจ้าของสินค้า
        this.product = product;
        this.review = null;
    }

    public void setReportItem(Review review) {
        this.suspectedPerson = review.getAuthor();
        this.review = review;
        this.product = null;
    }

    public String getDetail() {
        return detail;
    }

    public LocalDateTime getReportDateTime() {
        return reportDateTime;
    }

    public User getSuspectedPerson() {
        return suspectedPerson;
    }

    public Review getReview() {
        return review;
    }

    public Product getProduct() {
        return product;
    }
}
