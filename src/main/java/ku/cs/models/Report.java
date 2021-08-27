package ku.cs.models;

public class Report {
    private String detail;
    private Product product;
    private Review review;
    public enum ReportType {HARASSMENT, ABUSE}
    private final ReportType type;
    private final User reporter;

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

    public String getDetail() {
        return detail;
    }

    public void setReportItem(Product product){
        this.product = product;
        this.review = null;
    }

    public void setReportItem(Review review) {
        this.review = review;
        this.product = null;
    }

    public Review getReview() {
        return review;
    }

    public Product getProduct() {
        return product;
    }
}
