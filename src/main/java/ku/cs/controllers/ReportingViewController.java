package ku.cs.controllers;

import com.github.saacsos.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ku.cs.models.ComponentBuilder;
import ku.cs.models.Report;
import ku.cs.service.DataSource;

import java.io.IOException;
import java.time.LocalDateTime;

public class ReportingViewController {

    @FXML
    private HBox reportItemHBox;

    @FXML
    private Label reportPromptLabel;

    @FXML
    private VBox radioBtnVBox, assistiveTextVBox;

    @FXML
    private TextArea reportDetailsTA;

    private DataSource dataSource;
    private Report report;
    private ToggleGroup group;

    @FXML
    void handleBackBtn(ActionEvent event) {
        dataSource.getReports().setCurrReport(null);
        try {
            FXRouter.goTo("marketplace", dataSource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleSubmitReportBtn(ActionEvent event) {
        assistiveTextVBox.getChildren().clear();
        if (group.getSelectedToggle() == null || group.getSelectedToggle().getUserData() == null) {
            assistiveTextVBox
                    .getChildren().add(new Label("Please select what is wrong with this."));
            return;
        }

        if (reportDetailsTA.getText() == null || reportDetailsTA.getText().equals("")) {
            assistiveTextVBox
                    .getChildren().add(new Label("Please tell us more details about this report."));
            return;
        }

        report.setType(group.getSelectedToggle().getUserData().toString());
        report.setDetail(reportDetailsTA.getText());
        report.setReportDateTime(LocalDateTime.now());
        dataSource.getReports().addReport(report);
        dataSource.getReports().setCurrReport(null);
        dataSource.saveReport();

        try {
            FXRouter.goTo("marketplace", dataSource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populateReportType(){
        String [] reportTypes;
        group = new ToggleGroup();
        if (report.getProduct() != null)
            reportTypes = report.getProductReportType();
        else
            reportTypes = report.getReviewReportType();

        for (String type: reportTypes){
            RadioButton newBtn = new RadioButton(type);
            newBtn.getStyleClass().add("subtitle1");
            newBtn.setUserData(type);
            newBtn.setToggleGroup(group);
            radioBtnVBox.getChildren().add(newBtn);
        }
        group.selectedToggleProperty()
                .addListener((observableValue, toggle, t1) -> assistiveTextVBox.getChildren().clear());
    }

    private void buildCard(){
        ComponentBuilder builder = new ComponentBuilder();
        reportItemHBox.getStyleClass().add("review-card");
        if (report.getProduct() != null)
            reportItemHBox.getChildren().add(builder.smallProductCard(report.getProduct()));
        else
            reportItemHBox.getChildren().add(builder.smallReviewCard(report.getReview()));
    }

    private void clearText(KeyEvent e){
        assistiveTextVBox.getChildren().clear();
    }

    private void clearText(MouseEvent e){
        assistiveTextVBox.getChildren().clear();
    }

    @FXML
    public void initialize() throws IOException {
      dataSource = (DataSource) FXRouter.getData();
      report = dataSource.getReports().getCurrReport();
      if (report.getReview() != null)
          reportPromptLabel.setText("What's wrong with this review?");
      populateReportType();
      buildCard();

      reportDetailsTA.setOnMouseClicked(this::clearText);
      reportDetailsTA.setOnKeyPressed(this::clearText);
    }

}
