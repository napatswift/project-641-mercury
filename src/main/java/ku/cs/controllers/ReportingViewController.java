package ku.cs.controllers;

import com.github.saacsos.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
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
    private HBox reportPromptLabel;

    @FXML
    private VBox radioBtnVBox;

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
        if (group.getSelectedToggle() == null
                || group.getSelectedToggle().getUserData() == null
                || reportDetailsTA.getText() == null
                || reportDetailsTA.getText().equals(""))
            return; // TODO show assistive text
        report.setType(group.getSelectedToggle().getUserData().toString());
        report.setDetail(reportDetailsTA.getText());
        report.setReportDateTime(LocalDateTime.now());
        dataSource.getReports().addReport(report);
        dataSource.getReports().setCurrReport(null);
        dataSource.saveReport();
    }

    private void populateReportType(){
        String [] reportTypes;
        group = new ToggleGroup();
        if (report.getProduct() != null)
            reportTypes = report.getProductReportType();
        else
            reportTypes = new String[]{};

        for (String type: reportTypes){
            RadioButton newBtn = new RadioButton(type);
            newBtn.getStyleClass().add("subtitle1");
            newBtn.setUserData(type);
            newBtn.setToggleGroup(group);
            radioBtnVBox.getChildren().add(newBtn);
        }
    }

    private void buildCard(){
        ComponentBuilder builder = new ComponentBuilder();
        if (report.getProduct() != null)
            reportItemHBox.getChildren().add(builder.productCard(report.getProduct()));
        else
            ;
    }

    @FXML
    public void initialize() throws IOException {
      dataSource = (DataSource) FXRouter.getData();
      report = dataSource.getReports().getCurrReport();
      populateReportType();
      buildCard();
    }

}
