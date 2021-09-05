package ku.cs.models;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Report {

    private String type;
    private User suspectedPerson;
    private final User reporter;
    private LocalDateTime reportDateTime;
    private Review review;
    private Product product;
    private String detail;

    private String [] productReportType = {
            "Copyright",
            "Offensive or sexually explicit",
            "Privacy concern",
            "Legal issue",
            "Others"
    };

    public Report(String type, User suspectedPerson, User reporter, LocalDateTime reportDateTime, Review review, Product product, String detail) {
        this.type = type;
        this.suspectedPerson = suspectedPerson;
        this.reporter = reporter;
        this.reportDateTime = reportDateTime;
        this.review = review;
        this.product = product;
        this.detail = detail;
    }

    public Report(User reporter, Product product) {
        this.reporter = reporter;
        this.product = product;
    }

    public Report(User reporter, Review review) {
        this.reporter = reporter;
        this.review = review;
    }

    public String[] getProductReportType() {
        return productReportType;
    }

    public boolean checkReport(Report report){
        if(report == this)
            return true;
        return false;
    }

    //setter
    public void setType(String type) {
        this.type = type;
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

    public void setReportDateTime(LocalDateTime reportDateTime) {
        this.reportDateTime = reportDateTime;
    }

    //getter
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

    public User getReporter() {
        return reporter;
    }

    public String getType() {
        return type;
    }

    public String toCSV() {
        return "" + type + ","
                + (suspectedPerson == null ? null :suspectedPerson.getUsername()) + ","
                + reporter.getUsername() + ","
                + (reportDateTime == null ? null : reportDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)) + ","
                + (review == null ? null : review.getId()) + "," //ยังไม่ทำ reviewID
                + (product == null ? null : product.getId()) + "," //ยังไม่ทำ productID
                + "\"" + detail.replace("\"", "\"\"") + "\"";
    }

    public static class ReportListCell extends ListCell<Report> {
        private final VBox content;
        private final Label topLabel;
        private final Label label;

        public ReportListCell() {
            topLabel = new Label();
            label = new Label();
            topLabel.getStyleClass().add("body2");
            topLabel.setStyle("-fx-text-fill: on-surface-med-color;");
            label.getStyleClass().add("body1");
            label.setStyle("-fx-text-fill: on-surface-color");
            content = new VBox(topLabel, label);
            content.setSpacing(3);
        }

        private String getTimeString(LocalDateTime time){
            String pattern = "HH:mm - d MMM";
            DateTimeFormatter simpleDateFormat = DateTimeFormatter.ofPattern(pattern);
            return simpleDateFormat.format(time);
        }

        @Override
        protected void updateItem(Report item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null && !empty) {
                topLabel.setText(getTimeString(item.getReportDateTime()));
                label.setText("Report - #ID Report Type");
                setStyle("-fx-border-width: 0 0 2 0; -fx-border-color: surface-overlay");
                setGraphic(content);
            } else {
                setGraphic(null);
            }
        }
    }
}
