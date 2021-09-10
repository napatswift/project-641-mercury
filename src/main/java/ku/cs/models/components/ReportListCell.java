package ku.cs.models.components;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import ku.cs.models.Report;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReportListCell extends ListCell<Report> {
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
            label.setText("Report - #ID");
            setStyle("-fx-border-width: 0 0 2 0; -fx-border-color: surface-overlay");
            setGraphic(content);
        } else {
            setGraphic(null);
            setStyle(null);
        }
    }
}